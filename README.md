# code-diff
基於git或svn的差异代码获取


### 简介
+ 本项目主要是用于基于jacoco的增量代码统计，增量代码的统计核心问题是如何获得增量代码，网络上关于增量代码的获取相关资料比较少，而且代码注释也没有，阅读起来相对困难，我这边参考了几个项目后根据实际需求，进行了整理，整个项目工程，只有application模块为核心代码，其他都是辅助，请配合jacoco二开一起使用https://gitee.com/Dray/jacoco.git
具体实现方案请参考[博客](https://blog.csdn.net/tushuping/article/details/112613528)



### 使用方法
#### 1、修改application.yml
    ##基于git
	git:
      userName: admin
      password: 123456
      local:
        base:
          dir: D:\git-test
    ##基于svn
    svn:
      userName: admin
      password: 123456
      local:
        base:
          dir: D:\svn-test
#### 2、运行项目，访问http://127.0.0.1:8085/doc.html

![git差异代码获取](https://images.gitee.com/uploads/images/2021/0408/122939_6cf6505d_1007820.png "屏幕截图.png")
![svn差异代码获取](https://images.gitee.com/uploads/images/2021/0408/123039_5cb136f9_1007820.png "屏幕截图.png")
	 2.1 输入git地址，填写差异分支的旧版本，新版本，执行，就可以获取差异信息

	 2.2 
		{
			"code": 10000,
			"msg": "业务处理成功",
			"data": [
				{
					"classFile": "com/dr/application/InstallCert",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/application/app/controller/Calculable",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/application/app/controller/JenkinsPluginController",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/application/app/controller/LoginController",
					"methodInfos": [
			{
				"methodName": "captcha",
				"parameters": "HttpServletRequest&HttpServletResponse"
			},
			{
				"methodName": "login",
				"parameters": "LoginUserParam&HttpServletRequest"
			},
			{
				"methodName": "testInt",
				"parameters": "int&char"
			},
			{
				"methodName": "testInt",
				"parameters": "String&int"
			},
			{
				"methodName": "testInt",
				"parameters": "short&int"
			},
			{
				"methodName": "testInt",
				"parameters": "int[]"
			},
			{
				"methodName": "testInt",
				"parameters": "T[]"
			},
			{
				"methodName": "testInt",
				"parameters": "Calculable&int&int"
			},
			{
				"methodName": "testInt",
				"parameters": "Map<String,Object>&List<String>&Set<Integer>"
			},
			{
				"methodName": "display",
				"parameters": ""
			},
			{
				"methodName": "a",
				"parameters": "InnerClass"
			}
					],
					"type": "MODIFY"
				},
				{
					"classFile": "com/dr/application/app/controller/RoleController",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/application/app/controller/TestController",
					"methodInfos": [
			{
				"methodName": "test",
				"parameters": ""
			},
			{
				"methodName": "getPom",
				"parameters": "HttpServletResponse"
			},
			{
				"methodName": "getDeList",
				"parameters": ""
			}
					],
					"type": "MODIFY"
				},
				{
					"classFile": "com/dr/application/app/controller/view/RoleViewController",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/application/app/param/AddRoleParam",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/application/app/vo/DependencyVO",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/application/app/vo/JenkinsPluginsVO",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/application/app/vo/RoleVO",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/application/config/ExceptionAdvice",
					"methodInfos": [
			{
				"methodName": "handleException",
				"parameters": "Exception"
			},
			{
				"methodName": "handleMissingServletRequestParameterException",
				"parameters": "MissingServletRequestParameterException"
			}
					],
					"type": "MODIFY"
				},
				{
					"classFile": "com/dr/application/config/GitConfig",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/application/config/JenkinsConfig",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/application/ddd/StaticTest",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/application/ddd/Test",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/application/util/GitAdapter",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/common/errorcode/BizCode",
					"methodInfos": [
			{
				"methodName": "getCode",
				"parameters": ""
			}
					],
					"type": "MODIFY"
				},
				{
					"classFile": "com/dr/common/response/ApiResponse",
					"methodInfos": [
			{
				"methodName": "success",
				"parameters": ""
			}
					],
					"type": "MODIFY"
				},
				{
					"classFile": "com/dr/jenkins/JenkinsApplication",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/jenkins/config/JenkinsConfigure",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/jenkins/controller/JenkinsController",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/jenkins/controller/TestApi",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/jenkins/dto/JobAddDto",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/jenkins/service/JenkinsService",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/jenkins/service/impl/JenkinsServiceImpl",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/jenkins/util/GenerateUniqueIdUtil",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/jenkins/vo/DeviceVo",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/jenkins/vo/GoodsVO",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/jenkins/vo/JobAddVo",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/repository/user/dto/query/RoleQueryDto",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/repository/user/dto/result/RoleResultDto",
					"methodInfos": null,
					"type": "ADD"
				},
				{
					"classFile": "com/dr/user/service/impl/PermissionServiceImpl",
					"methodInfos": [
			{
				"methodName": "getPermissionByRoles",
				"parameters": "List<Long>"
			},
			{
				"methodName": "buildMenuTree",
				"parameters": "List<MenuDTO>"
			},
			{
				"methodName": "getSubMenus",
				"parameters": "Long&Map<Long,List<MenuDTO>>"
			}
					],
					"type": "MODIFY"
				},
				{
					"classFile": "com/dr/user/service/impl/RoleServiceImpl",
					"methodInfos": [
			{
				"methodName": "getByUserId",
				"parameters": "Long"
			},
			{
				"methodName": "getListByPage",
				"parameters": "RoleQueryDto"
			}
					],
					"type": "MODIFY"
				}
			],
			"uniqueData": "[{\"classFile\":\"com/dr/application/InstallCert\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/application/app/controller/Calculable\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/application/app/controller/JenkinsPluginController\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/application/app/controller/LoginController\",\"methodInfos\":[{\"methodName\":\"captcha\",\"parameters\":\"HttpServletRequest&HttpServletResponse\"},{\"methodName\":\"login\",\"parameters\":\"LoginUserParam&HttpServletRequest\"},{\"methodName\":\"testInt\",\"parameters\":\"int&char\"},{\"methodName\":\"testInt\",\"parameters\":\"String&int\"},{\"methodName\":\"testInt\",\"parameters\":\"short&int\"},{\"methodName\":\"testInt\",\"parameters\":\"int[]\"},{\"methodName\":\"testInt\",\"parameters\":\"T[]\"},{\"methodName\":\"testInt\",\"parameters\":\"Calculable&int&int\"},{\"methodName\":\"testInt\",\"parameters\":\"Map<String,Object>&List<String>&Set<Integer>\"},{\"methodName\":\"display\",\"parameters\":\"\"},{\"methodName\":\"a\",\"parameters\":\"InnerClass\"}],\"type\":\"MODIFY\"},{\"classFile\":\"com/dr/application/app/controller/RoleController\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/application/app/controller/TestController\",\"methodInfos\":[{\"methodName\":\"test\",\"parameters\":\"\"},{\"methodName\":\"getPom\",\"parameters\":\"HttpServletResponse\"},{\"methodName\":\"getDeList\",\"parameters\":\"\"}],\"type\":\"MODIFY\"},{\"classFile\":\"com/dr/application/app/controller/view/RoleViewController\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/application/app/param/AddRoleParam\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/application/app/vo/DependencyVO\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/application/app/vo/JenkinsPluginsVO\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/application/app/vo/RoleVO\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/application/config/ExceptionAdvice\",\"methodInfos\":[{\"methodName\":\"handleException\",\"parameters\":\"Exception\"},{\"methodName\":\"handleMissingServletRequestParameterException\",\"parameters\":\"MissingServletRequestParameterException\"}],\"type\":\"MODIFY\"},{\"classFile\":\"com/dr/application/config/GitConfig\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/application/config/JenkinsConfig\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/application/ddd/StaticTest\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/application/ddd/Test\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/application/util/GitAdapter\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/common/errorcode/BizCode\",\"methodInfos\":[{\"methodName\":\"getCode\",\"parameters\":\"\"}],\"type\":\"MODIFY\"},{\"classFile\":\"com/dr/common/response/ApiResponse\",\"methodInfos\":[{\"methodName\":\"success\",\"parameters\":\"\"}],\"type\":\"MODIFY\"},{\"classFile\":\"com/dr/jenkins/JenkinsApplication\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/jenkins/config/JenkinsConfigure\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/jenkins/controller/JenkinsController\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/jenkins/controller/TestApi\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/jenkins/dto/JobAddDto\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/jenkins/service/JenkinsService\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/jenkins/service/impl/JenkinsServiceImpl\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/jenkins/util/GenerateUniqueIdUtil\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/jenkins/vo/DeviceVo\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/jenkins/vo/GoodsVO\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/jenkins/vo/JobAddVo\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/repository/user/dto/query/RoleQueryDto\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/repository/user/dto/result/RoleResultDto\",\"methodInfos\":[],\"type\":\"ADD\"},{\"classFile\":\"com/dr/user/service/impl/PermissionServiceImpl\",\"methodInfos\":[{\"methodName\":\"getPermissionByRoles\",\"parameters\":\"List<Long>\"},{\"methodName\":\"buildMenuTree\",\"parameters\":\"List<MenuDTO>\"},{\"methodName\":\"getSubMenus\",\"parameters\":\"Long&Map<Long,List<MenuDTO>>\"}],\"type\":\"MODIFY\"},{\"classFile\":\"com/dr/user/service/impl/RoleServiceImpl\",\"methodInfos\":[{\"methodName\":\"getByUserId\",\"parameters\":\"Long\"},{\"methodName\":\"getListByPage\",\"parameters\":\"RoleQueryDto\"}],\"type\":\"MODIFY\"}]"
		}


在linux系统部署时请注意修改代码的基础路径和日志路径，如：
```angular2html
java -jar  -Dlog.path=/app/data2/devops/code-diff/logs  -Dgit.local.base.dir=/app/data2/devops/code-diff/   application-1.0.0-SNAPSHOT.jar
```

#近期github不稳定，请访问https://gitee.com/Dray/code-diff.git


如有疑问，请加群主入群

![输入图片说明](https://images.gitee.com/uploads/images/2021/0414/163539_9ff67f82_1007820.png "屏幕截图.png")