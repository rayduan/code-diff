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
               "classFile": "com/dr/code/diff/config/GitConfig",
               "methodInfos": [
                 {
                   "methodName": "cloneRepository",
                   "parameters": "String gitUrl,String codePath,String commitId"
                 },
                 {
                   "methodName": "diffMethods",
                   "parameters": "DiffMethodParams diffMethodParams"
                 },
                 {
                   "methodName": "getClassMethods",
                   "parameters": "String oldClassFile,String mewClassFile,DiffEntry diffEntry"
                 }
               ],
               "type": "MODIFY"
             },
             {
               "classFile": "com/dr/code/diff/controller/CodeDiffController",
               "methodInfos": [
                 {
                   "methodName": "getList",
                   "parameters": "@ApiParam(required = true, name = \"gitUrl\", value = \"git远程仓库地址\") @RequestParam(value = \"gitUrl\") String gitUrl,@ApiParam(required = true, name = \"baseVersion\", value = \"git原始分支或tag\") @RequestParam(value = \"baseVersion\") String baseVersion,@ApiParam(required = true, name = \"nowVersion\", value = \"git现分支或tag\") @RequestParam(value = \"nowVersion\") String nowVersion"
                 }
               ],
               "type": "MODIFY"
             },
             {
               "classFile": "com/dr/code/diff/service/impl/CodeDiffServiceImpl",
               "methodInfos": [
                 {
                   "methodName": "getDiffCode",
                   "parameters": "DiffMethodParams diffMethodParams"
                 }
               ],
               "type": "MODIFY"
             },
             {
               "classFile": "com/dr/common/utils/string/ScmStringUtil",
               "methodInfos": [],
               "type": "ADD"
             }
           ],
           "uniqueData": "[{\"classFile\":\"com/dr/code/diff/config/GitConfig\",\"methodInfos\":[{\"methodName\":\"cloneRepository\",\"parameters\":\"String gitUrl,String codePath,String commitId\"},{\"methodName\":\"diffMethods\",\"parameters\":\"DiffMethodParams diffMethodParams\"},{\"methodName\":\"getClassMethods\",\"parameters\":\"String oldClassFile,String mewClassFile,DiffEntry diffEntry\"}],\"type\":\"MODIFY\"},{\"classFile\":\"com/dr/code/diff/controller/CodeDiffController\",\"methodInfos\":[{\"methodName\":\"getList\",\"parameters\":\"@ApiParam(required = true, name = \\\"gitUrl\\\", value = \\\"git远程仓库地址\\\") @RequestParam(value = \\\"gitUrl\\\") String gitUrl,@ApiParam(required = true, name = \\\"baseVersion\\\", value = \\\"git原始分支或tag\\\") @RequestParam(value = \\\"baseVersion\\\") String baseVersion,@ApiParam(required = true, name = \\\"nowVersion\\\", value = \\\"git现分支或tag\\\") @RequestParam(value = \\\"nowVersion\\\") String nowVersion\"}],\"type\":\"MODIFY\"},{\"classFile\":\"com/dr/code/diff/service/impl/CodeDiffServiceImpl\",\"methodInfos\":[{\"methodName\":\"getDiffCode\",\"parameters\":\"DiffMethodParams diffMethodParams\"}],\"type\":\"MODIFY\"},{\"classFile\":\"com/dr/common/utils/string/ScmStringUtil\",\"methodInfos\":[],\"type\":\"ADD\"}]"
         }
