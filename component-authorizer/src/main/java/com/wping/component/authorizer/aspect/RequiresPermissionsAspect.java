package com.wping.component.authorizer.aspect;

import com.wping.component.authorizer.annotation.RequiresPermissions;
import com.wping.component.authorizer.enums.Logical;
import com.wping.component.authorizer.properties.AuthorizerProperties;
import com.wping.component.authorizer.service.AuthorizationService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * describe:角色验证
 *
 * @author Wping.gao
 * @date 2018/12/03
 */
@Aspect
public class RequiresPermissionsAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequiresPermissionsAspect.class);

    private AuthorizerProperties authorizerProperties;
    private AuthorizationService authorizationService;

    public RequiresPermissionsAspect(AuthorizerProperties authorizerProperties, AuthorizationService authorizationService) {
        this.authorizerProperties = authorizerProperties;
        this.authorizationService = authorizationService;
    }

    @Before("@annotation(requiresPermissions)")
    public void before(JoinPoint point, RequiresPermissions requiresPermissions) {

        if (!authorizerProperties.isEnabled()) {
            logger.warn("授权开关关闭, 不进行权限验证!!!");
            return;
        }

        Object[] args = point.getArgs();

        String[] permissions = requiresPermissions.value();

        if (permissions.length == 1) {
            authorizationService.checkPermission(args, permissions[0]);
            return;
        }

        if (Logical.AND.equals(requiresPermissions.logical())) {
            authorizationService.checkPermissions(args, Arrays.asList(permissions));
            return;
        }

        if (Logical.OR.equals(requiresPermissions.logical())) {
            boolean hasAtLeastOnePermissions = false;
            for (String permission : permissions) {
                if (authorizationService.hasPermission(args, permission)) {
                    hasAtLeastOnePermissions = true;
                }
            }
            if (!hasAtLeastOnePermissions) {
                authorizationService.checkPermission(args, permissions[0]);
            }
        }

    }


}
