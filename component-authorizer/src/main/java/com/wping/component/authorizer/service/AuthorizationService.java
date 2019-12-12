package com.wping.component.authorizer.service;

import com.wping.component.authorizer.enums.Logical;
import com.wping.component.authorizer.exception.AuthorizationException;
import com.wping.component.authorizer.properties.AuthorizerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * describe:
 *
 * @author Wping.gao
 * @date 2019/5/14 17:21
 */
public class AuthorizationService implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationService.class);

    private UserAuthorizationService userAuthorizationService;

    private AuthorizerProperties authorizerProperties;

    public AuthorizationService(AuthorizerProperties authorizerProperties) {
        this.authorizerProperties = authorizerProperties;
    }

    public void checkRequiresRoles(Object[] args, String[] roles, Logical logical) throws AuthorizationException {

        if (!authorizerProperties.isEnabled()) {
            logger.warn("授权开关关闭, 不进行角色验证!!!");
            return;
        }

        if (roles == null || roles.length == 0) {
            throw new AuthorizationException("角色集不能为空");
        }

        if (logical == null) {
            throw new AuthorizationException("逻辑运算logical为空");
        }

        if (roles.length == 1) {
            checkRole(args, roles[0]);
            return;
        }

        if (Logical.AND.equals(logical)) {
            checkRoles(args, Arrays.asList(roles));
            return;
        }

        if (Logical.OR.equals(logical)) {
            boolean hasAtLeastOneRole = false;
            for (String role : roles) {
                if (hasRole(args, role)) {
                    hasAtLeastOneRole = true;
                }
            }
            if (!hasAtLeastOneRole) {
                checkRole(args, roles[0]);
            }
        }

    }

    public void checkRequiresPermissions(Object[] args, String[] permissions, Logical logical) {

        if (!authorizerProperties.isEnabled()) {
            logger.warn("授权开关关闭, 不进行权限验证!!!");
            return;
        }

        if (permissions == null || permissions.length == 0) {
            throw new AuthorizationException("权限集不能为空");
        }

        if (logical == null) {
            throw new AuthorizationException("逻辑运算logical为空");
        }

        if (permissions.length == 1) {
            checkPermission(args, permissions[0]);
            return;
        }

        if (Logical.AND.equals(logical)) {
            checkPermissions(args, Arrays.asList(permissions));
            return;
        }

        if (Logical.OR.equals(logical)) {
            boolean hasAtLeastOnePermissions = false;
            for (String permission : permissions) {
                if (hasPermission(args, permission)) {
                    hasAtLeastOnePermissions = true;
                }
            }
            if (!hasAtLeastOnePermissions) {
                checkPermission(args, permissions[0]);
            }
        }

    }

    public void checkRole(Object[] args, String roleIdentifier) throws AuthorizationException {

        String userId = userAuthorizationService.getUserId(args);
        List<String> roles = userAuthorizationService.queryRoles(userId);
        checkRole(userId, roleIdentifier, roles);

    }

    private void checkRole(String userId, String roleIdentifier, List<String> roles) throws AuthorizationException {
        if (CollectionUtils.isEmpty(roles) || !roles.contains(roleIdentifier)) {
            String msg = "用户" + userId + "没有角色" + roleIdentifier;
            logger.warn(msg);
            throw new AuthorizationException(msg);
        }
    }

    public void checkRoles(Object[] args, Collection<String> roleIdentifiers) throws AuthorizationException {

        String userId = userAuthorizationService.getUserId(args);
        List<String> roles = userAuthorizationService.queryRoles(userId);
        for (String roleIdentifier : roleIdentifiers) {
            checkRole(userId, roleIdentifier, roles);
        }

    }

    public boolean hasRole(Object[] args, String roleIdentifier) {

        String userId = userAuthorizationService.getUserId(args);
        List<String> roles = userAuthorizationService.queryRoles(userId);
        if (!CollectionUtils.isEmpty(roles) && roles.contains(roleIdentifier)) {
            return true;
        }

        return false;
    }

    public void checkPermission(Object[] args, String permission) throws AuthorizationException {

        String userId = userAuthorizationService.getUserId(args);
        List<String> permissions = userAuthorizationService.queryPermissions(userId);
        checkPermission(userId, permission, permissions);

    }

    private void checkPermission(String userId, String permission, List<String> permissions) throws AuthorizationException {
        if (CollectionUtils.isEmpty(permissions) || !permissions.contains(permission)) {
            String msg = "用户" + userId + "没有权限" + permission;
            logger.warn(msg);
            throw new AuthorizationException(msg);
        }
    }

    public void checkPermissions(Object[] args, List<String> requiresPermissions) throws AuthorizationException {

        String userId = userAuthorizationService.getUserId(args);
        List<String> permissions = userAuthorizationService.queryPermissions(userId);
        for (String permission : requiresPermissions) {
            checkPermission(userId, permission, permissions);
        }

    }

    public boolean hasPermission(Object[] args, String permission) {

        String userId = userAuthorizationService.getUserId(args);
        List<String> permissions = userAuthorizationService.queryPermissions(userId);
        if (!CollectionUtils.isEmpty(permissions) && permissions.contains(permission)) {
            return true;
        }

        return false;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        this.userAuthorizationService = applicationReadyEvent.getApplicationContext().getBean(UserAuthorizationService.class);
    }

}
