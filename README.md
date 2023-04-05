# code-diff
基于git或svn的差异代码获取


### 简介
+ 本项目主要是用于基于jacoco的增量代码统计，增量代码的统计核心问题是如何获得增量代码，网络上关于增量代码的获取相关资料比较少，而且代码注释也没有，阅读起来相对困难，我这边参考了几个项目后根据实际需求，进行了整理，整个项目工程，只有application模块为核心代码，其他都是辅助，请配合jacoco二开一起使用https://gitee.com/Dray/jacoco.git
具体实现方案请参考[博客](https://blog.csdn.net/tushuping/article/details/112613528)

### 功能介绍
* 支持基于git的差异方法获取
 > 1. 验证支持账号密码和秘钥
 > 2. git支持基于分支对比，基于commitId对比，以及基于tag对比
* 支持基于svn的差异方法获取
 > 1. svn支持基于同分支不同reversion，不同分支同一reversion，不同分支不同reversion
* 支持基于git的方法的静态调用链
* 支持基于git的变更代码影响接口
* 支持基于svn的变更代码影响接口
* 影响接口支持http，dubbo以及自定义起始类，自定义起始方法

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
---
【add】
### 修复：
* 1.修复循环引用问题
* 2.优化lambda表达式调用链问题

【modify】 2023-04-05
### 变更：
1. 为了降低使用门槛合并代码结构为单模块
2. 增加了dockerfile，在工程目录下只需执行以下命令就可以构建镜像
    ```
   mvn clean package -Dmaven.tesr.skip=true
   docker build -t code-diff .
   ```
   当然我已经构建了一个通用[镜像](https://hub.docker.com/layers/rayduan/code-diff/v1/images/sha256-eefb21263cef421866ff68b193b4311a877e29e20a5acb2ef5745de1aefd396f?context=repo)
   获取镜像后只需运行

   ```docker run -d -p 8085:8085  --restart=always -e JAVA_OPTS="-Xms512m -Xmx1g  -Dspring.profiles.active=docker"   --name code-diff rayduan/code-diff:v1 ```
  镜像中内置了git和maven，maven settings使用了阿里云镜像，如果需要私服可以手动修改镜像内的文件，或者重新自己构建镜像，另外私钥地址可以运行时指定卷映射出去(-v root/.ssh/id_rsa.:~/.ssh/id_rsa.)，然后通过jvm参数指定，具体涉及到docker相关姿势请手动上网，一般情况下运行上述命令即可，无需做任何改动
  运行时参数可以通过修改JAVA_OPTS指定，如git的账号密码：
  ```
     JAVA_OPTS="-Dgit.userName=zs -Dgit.password=123456" 
  ```