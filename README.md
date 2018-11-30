# 需求
一个公司内部有多个后端管理系统，每个系统都有需要功能菜单，用户和角色。
需要一个单点登陆系统
需要对每个用户，或者角色的进行数据权限管理，特别是精确到数据库行级别，或者范围权限

# urm
1，提供统一的功能菜单，用户和惧色
2，提供统一单点登陆
3，提供行级别数据权限控制支持

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

# 第三方系统



 

  
  
  
  


 
 

  
