package com.nineya.authentication.realm;

import com.nineya.authentication.isp.JwtToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.Collection;

/**
 * 复写默认的realm控制器，实现根据realm名称，不同token访问不同realm的功能
 * @author 殇雪话诀别
 * 2021/2/18
 */
public class NineyaModularRealmAuthenticator extends ModularRealmAuthenticator {

    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
        throws AuthenticationException {
        // 判断 Realm 是否为空
        assertRealmsConfigured();
        Collection<Realm> realms = getRealms();
        JwtToken jwtToken = (JwtToken) authenticationToken;
        String realmName = jwtToken.getRealmName();
        for (Realm realm : realms) {
            if (realm.getName().equals(realmName)) {
                return doSingleRealmAuthentication(realm, authenticationToken);
            }
        }
        return null;
    }
}
