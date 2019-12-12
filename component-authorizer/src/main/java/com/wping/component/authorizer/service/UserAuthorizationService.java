package com.wping.component.authorizer.service;

import java.util.List;

/**
 * describe: 用户权限服务类
 *
 * @author Wping.gao
 * @date 2019/5/14 17:20
 */
public interface UserAuthorizationService {

    /**
     * 获取用户ID
     * @param args 被代理方法请求参数
     * @return
     */
    String getUserId(Object[] args);

    /**
     * 通过用户ID获取用户角色
     * @param userId
     * @return
     */
    List<String> queryRoles(String userId);

    /**
     * 通过用户ID获取用户权限
     * @param userId
     * @return
     */
    List<String> queryPermissions(String userId);

}
