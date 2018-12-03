# 需求
后端管理平台，如何分配管理人员的数据权限，精确到行级别权限，可以用的做法是代码中实现，若是要改变的则需要改变的代码，如何动态改变用户的数据权限，urm系统提供一种解决实现的方案

# 实现
urm通过mybatis拦截执行的sql语句，动态改变sql执行语句来实现，
用户在执行某个操作，配置相应的数据查询范围来，修改相应的语句来达到确定权限的目的。

      例如查询订单操作执行sql语句 select * form order_info 
      当前用户对该操作的权限是 order_type=1
      则相应的修改查询语句为 select * from order_info where order_type = 1

相应的修改和删除语句，加上相应的where order_type = 1条件来限制

# jar介绍

    urm-parent
    urm-common  工具jar
    urm-right   权限开发工具jar
    urm-web-res 静态js，css 库
    urm-auth    单点登陆鉴权系统
    urm-manager 权限管理系统
 
# 示例第三方管理系统实现
    urm-demo  demo例子系统

# 部署的例子

1，权限管理系统 urm-manager   http://urm.panly.me:8500/
      
      默认用户 admin/123456
  
2，单点登陆鉴权系统
  
    urm-auth http://urm.panly.me:8500/
  
3, demo系统        http://urm.panly.me:8701/
  
    demo系统的用户  是在权限管理系统的账户功能中创建的
    在权限管理系统默认注册账号，lipan/123456



 

  
  
  
  


 
 

  
