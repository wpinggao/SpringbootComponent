package com.wping.component.authorizer.aspect;

import com.wping.component.authorizer.annotation.RequiresRoles;
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
public class RequiresRolesAspect {

    private static final Logger logger = LoggerFactory.getLogger(RequiresRolesAspect.class);

    private AuthorizerProperties authorizerProperties;
    private AuthorizationService authorizationService;

    public RequiresRolesAspect(AuthorizerProperties authorizerProperties, AuthorizationService authorizationService) {
        this.authorizerProperties = authorizerProperties;
        this.authorizationService = authorizationService;
    }

    @Before("@annotation(requiresRoles)")
    public void before(JoinPoint point, RequiresRoles requiresRoles) {

        if (!authorizerProperties.isEnabled()) {
            logger.warn("授权开关关闭, 不进行角色验证!!!");
            return;
        }

        Object[] args = point.getArgs();

        String[] roles = requiresRoles.value();

        if (roles.length == 1) {
            authorizationService.checkRole(args, roles[0]);
            return;
        }

        if (Logical.AND.equals(requiresRoles.logical())) {
            authorizationService.checkRoles(args, Arrays.asList(roles));
            return;
        }

        if (Logical.OR.equals(requiresRoles.logical())) {
            boolean hasAtLeastOneRole = false;
            for (String role : roles) {
                if (authorizationService.hasRole(args, role)) {
                    hasAtLeastOneRole = true;
                }
            }
            if (!hasAtLeastOneRole) {
                authorizationService.checkRole(args, roles[0]);
            }
        }

    }


}
