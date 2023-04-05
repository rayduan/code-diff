FROM openjdk:8-jdk-alpine
ENV LANG=C.UTF-8
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
RUN apk update && apk add git  curl maven
RUN mkdir -p ~/.m2 && \
    echo '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" \
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" \
    xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 \
    https://maven.apache.org/xsd/settings-1.0.0.xsd">' > ~/.m2/settings.xml && \
    echo '  <mirrors>' >> ~/.m2/settings.xml && \
    echo '    <mirror>' >> ~/.m2/settings.xml && \
    echo '      <id>alimaven</id>' >> ~/.m2/settings.xml && \
    echo '      <mirrorOf>*</mirrorOf>' >> ~/.m2/settings.xml && \
    echo '      <name>阿里云公共仓库</name>' >> ~/.m2/settings.xml && \
    echo '      <url>https://maven.aliyun.com/repository/public</url>' >> ~/.m2/settings.xml && \
    echo '    </mirror>' >> ~/.m2/settings.xml && \
    echo '  </mirrors>' >> ~/.m2/settings.xml && \
    echo '</settings>' >> ~/.m2/settings.xml
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp
ARG JAR_FILE=target/code-diff-*.jar
COPY ${JAR_FILE} app.jar
COPY run.sh .
RUN chmod 777 run.sh
ENV JAVA_OPTS="-server -Xms512m -Xmx1g"
RUN ls -l
ENTRYPOINT ["sh","/run.sh"]