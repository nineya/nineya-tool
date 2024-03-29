package com.nineya.authentication.realm;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * 复写默认的realm控制器，实现根据realm名称，不同token访问不同realm的功能
 *
 * @author 殇雪话诀别
 * 2021/2/18
 */
public class NineyaModularRealmAuthorizer extends ModularRealmAuthorizer {
    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) {
                continue;
            }
            // 仅比较 realmName 对应得上的 realm
            if (realmNames.contains(realm.getName())) {
                return ((Authorizer) realm).isPermitted(principals, permission);
            }
        }
        return false;
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, Permission permission) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) {
                continue;
            }
            // 仅比较 realmName 对应得上的 realm
            if (realmNames.contains(realm.getName())) {
                return ((Authorizer) realm).isPermitted(principals, permission);
            }
        }
        return false;
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        assertRealmsConfigured();
        Set<String> realmNames = principals.getRealmNames();
        for (Realm realm : getRealms()) {
            if (!(realm instanceof Authorizer)) {
                continue;
            }
            // 仅比较 realmName 对应得上的 realm
            if (realmNames.contains(realm.getName())) {
                return ((Authorizer) realm).hasRole(principals, roleIdentifier);
            }
        }
        return false;
    }

}
