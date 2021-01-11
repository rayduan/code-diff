# code-diff
基於git的差异代码获取


### 简介
+ 本项目主要是用于基于jacoco的增量代码统计，增量代码的统计核心问题是如何获得增量代码，网络上关于增量代码的获取相关资料比较少，而且代码注释也没有，阅读起来相对困难，我这边参考了几个项目后根据实际需求，进行了整理，整个项目工程，只有application的部分代码为核心代码，其他都是辅助


### 使用方法
#### 1、修改application.yml
	git:
      userName: rayduan      #git账号
      password: FDsfret334   #git密码
#### 2、运行项目，访问http://127.0.0.1:8085/doc.html
	 2.1 输入git地址，填写差异分支的旧版本，新版本，执行，就可以获取差异信息
	 2.2 {
           "code": 10000,
           "msg": "业务处理成功",
           "data": [
             {
               "classFile": "collector/src/main/java/com/geely/collector/CollectorApplication.java",
               "methodInfos": [
                 {
                   "md5": "13E2BFB69F7D987A6DB4272400C94E9B",
                   "methodName": "main",
                   "parameters": "[String[] args]"
                 }
               ],
               "type": "MODIFY"
             },
             {
               "classFile": "collector/src/main/java/com/geely/collector/bean/CodeQuality.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "collector/src/main/java/com/geely/collector/dao/basic/CodeQualityMapper.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "collector/src/main/java/com/geely/collector/dao/extension/CodeQualityExtensionMapper.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "collector/src/main/java/com/geely/collector/mvc/APIResponse.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "collector/src/main/java/com/geely/collector/mvc/BusinessException.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "collector/src/main/java/com/geely/collector/task/TestTask.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/gitlab/config/GitlabAPIConfig.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/gitlab/dto/CommitDetail.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/gitlab/dto/GitLabStats.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/gitlab/service/GitlabService.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/gitlab/service/impl/GitlabServiceImpl.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/Test.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/client/BaseHttpClient.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/client/MeasureClient.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/client/ProjectClient.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/client/authentication/PreemptiveAuth.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/config/SonarConnectionConf.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/dto/BaseModel.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/dto/MeasuresBean.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/dto/MeasuresResultDto.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/service/MeasureService.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/service/impl/MeasureServiceImpl.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/util/HttpResponseValidator.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/util/HttpResponseWrapper.java",
               "methodInfos": null,
               "type": "ADD"
             },
             {
               "classFile": "third-sdk/src/main/java/com/geely/sonar/util/SonarContant.java",
               "methodInfos": null,
               "type": "ADD"
             }
           ]
         }
