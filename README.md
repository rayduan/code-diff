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
    git支持ssh（目前支持分支）配置
    git:
      ssh:
        priKey: C:\Users\mylocl/.ssh/id_rsa.
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

在linux系统部署时请注意修改代码的基础路径和日志路径，如：
```angular2html
java -jar  -Dlog.path=/app/data2/devops/code-diff/logs  -Dgit.local.base.dir=/app/data2/devops/code-diff/   application-1.0.0-SNAPSHOT.jar
```

#近期github不稳定，请访问https://gitee.com/Dray/code-diff.git


如有疑问，请加群主入群

![输入图片说明](https://images.gitee.com/uploads/images/2021/0414/163539_9ff67f82_1007820.png "屏幕截图.png")


##问题点：
如果ssh方式出现
invalid privatekey: [B@6553d80f 则是ssh的版本太高，通过指定旧版本的方式重新生成即可
```
ssh-keygen -m PEM -t rsa
```
---
[add]
### 新增：
* 1.支持差异对比mapper xml获取变更的mapper类中的方法
* 2.支持获取项目的静态调用链
* 3.支持通过差异代码和静态调用链推导变更http接口和dubbo接口
### 修复：
* 1.方法中的注释 // 应该不做代码差异
* 2.缺失构造方法的diff
* 3.根据包名对调用链进行降噪
#### 注意，因为需要变异代码，所以要配置maven，另外本项目运行和编译的代码均采用jdk1.8
```agsl
maven:
  home: /usr/local/app/apache-maven-3.8.3
```