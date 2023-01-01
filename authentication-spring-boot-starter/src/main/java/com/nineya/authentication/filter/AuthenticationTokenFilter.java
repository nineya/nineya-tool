package com.nineya.authentication.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nineya.authentication.isp.LoginType;
import com.nineya.authentication.isp.JwtToken;
import com.nineya.tool.restful.NetworkStatus;
import com.nineya.tool.restful.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * token 解析过滤器
 *
 * @author 殇雪话诀别
 * 2021/2/17
 */
@Slf4j
public class AuthenticationTokenFilter extends BasicHttpAuthenticationFilter {

    private final ObjectMapper objectMapper;
    /**
     * 用户登录类型
     */
    private final List<LoginType> loginTypes;

    private static final String TOKEN_PARAM = "AuthenticationTokenFilterToken";
    private static final String REALM_NAME_PARAM = "AuthenticationTokenFilterRealmName";

    public AuthenticationTokenFilter(ObjectMapper objectMapper, List<LoginType> loginTypes) {
        this.objectMapper = objectMapper;
        this.loginTypes = loginTypes;
    }

    /**
     * 判断用户是否想要登入。
     * 检测header里面是否包含Authorization等token相关的字段即可
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        for (LoginType loginType : loginTypes) {
            String token = req.getHeader(loginType.getHeaderName());
            if (token != null) {
                request.setAttribute(TOKEN_PARAM, token);
                request.setAttribute(REALM_NAME_PARAM, loginType.getRealmName());
                return true;
            }
            if (loginType.isAllowCookieLogin()) {
                Cookie[] cookies= req.getCookies();
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(loginType.getHeaderName())) {
                        request.setAttribute(TOKEN_PARAM, cookie.getValue());
                        request.setAttribute(REALM_NAME_PARAM, loginType.getRealmName());
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 该方法返回值表示是否跳过认证，这里如果返回 true，则不会再走一遍认证流程
     * 如果返回 false，则会执行 isAccessAllowed 方法，再执行isLoginAttempt方法，如果为false继续执行executeLogin
     * 方法，如果没有执行executeLogin或者执行结果也是false，则将执行sendChallenge方法，表示认证失败。
     * 返回 true 时，则必须在postHandle配置退出登录，这个方法将在执行完业务逻辑后执行，否则将导致下次没有携带token时直接使用上次的登录
     * 结果，从而非法访问接口。
     * 默认返回 false 时，isLoginAttempt这些方法将重复调用，所以不建议。如果要返回 false，建议复写 sendChallenge 方法，因为其响应内容为空。
     * <p>
     * 也可以将 isAccessAllowed 作为纯判断是否需要认证，或者不复写该方法，本文不提供实现，如果有问题欢迎留言
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 全部返回 false表示要进行验证，所有流程交给父类来执行把
        return false;
    }

    /**
     * 用户登录
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = (String) request.getAttribute(TOKEN_PARAM);
        String realmName = (String) request.getAttribute(REALM_NAME_PARAM);
        if (token == null || realmName == null) {
            return false;
        }
        JwtToken jwtToken = new JwtToken(realmName, httpServletRequest, token);
        getSubject(request, response).login(jwtToken);
        return true;
    }

    /**
     * 该方法将在过滤器执行完成后执行
     * 当isAccessAllowed默认为true时必须实现该方法
     * 在执行完请求后执行退出登录逻辑，否则下次请求时没有携带将可以直接访问接口，无须重新登录
     * @param request
     * @param response
     * @throws Exception
     */
    @Override
    protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 当 isAccessAllowed 可能返回false时需要复写该接口，否则默认将返回空白界面
     *
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setContentType("application/json; charset=utf-8");
        try {
            response.getWriter().write(
                    objectMapper.writeValueAsString(ResponseResult.failure(NetworkStatus.UNAUTHORIZED, "登录状态已失效！").toMap()));
        } catch (IOException e) {
            throw new UnauthenticatedException("登录状态已失效！");
        }
        return false;
    }

    /**
     * 登录异常处理类，该方法将捕获所有在本过滤器中遇到的异常，默认将会进行登录重试，重试失败后执行 afterCompletion 方法进行善后处理，
     * 后将异常封装为 ServletException。
     * 建议复写本方法 jwt 登录失败，重试也是无果的。
     * 其过滤的主体内容发生错误也会在这里执行，这点有些不好。
     *
     * @param request
     * @param response
     * @param existing 异常信息，如果为 null表示没有异常，不需要进行处理
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void cleanup(ServletRequest request, ServletResponse response, Exception existing)
            throws ServletException, IOException {
        if (existing == null) {
            return;
        }
        if (existing instanceof ShiroException) {
            log.error("登录状态已失效！", existing);
            sendChallenge(request, response);
            return;
        }
        throw new ServletException(existing);
    }
}
