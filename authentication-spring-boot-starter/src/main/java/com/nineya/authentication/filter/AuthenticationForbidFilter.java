package com.nineya.authentication.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nineya.tool.restful.NetworkStatus;
import com.nineya.tool.restful.ResponseResult;
import org.apache.shiro.web.servlet.OncePerRequestFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 禁止访问的过滤器
 *
 * @author 殇雪话诀别
 */
public class AuthenticationForbidFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    public AuthenticationForbidFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        httpResponse.setContentType("application/json; charset=utf-8");
        response.getWriter().write(
                objectMapper.writeValueAsString(
                        ResponseResult.failure(NetworkStatus.UNAUTHORIZED, "禁止访问！").toMap()));
    }
}
