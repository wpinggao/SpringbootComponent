# 权限验证组件:基于角色的权限访问控制（Role-Based Access Control）
特性
1. 对角色简单控制
2. 对权限控制

如何使用
1. 添加配置，控制是否开启权限验证 true:开启(默认), false: 关闭

wping:

  authorizer:
  
    enabled: true


2. 实现接口UserAuthorizationService

public interface UserAuthorizationService {

    /**
     * 获取用户ID
     * @param args
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


3. 对角色或者权限配置

角色或者权限多个是，可以指定运算逻辑: 与 or 或

@RequiresRoles(value = {"admin"}, logical = Logical.AND)

@RequiresPermissions(value = {"user:select"})
