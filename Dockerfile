FROM openjdk:8-jdk-alpine
ENV LANG=C.UTF-8
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
RUN apk update && apk add git  curl maven
COPY settings.xml /root/.m2/
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
VOLUME /tmp
ARG JAR_FILE=target/code-diff-*.jar
COPY ${JAR_FILE} app.jar
COPY run.sh .
RUN chmod 777 run.sh
ENV JAVA_OPTS="-server -Xms512m -Xmx1g -Dsping.profiles.active=docker"
RUN ls -l
ENTRYPOINT ["sh","/run.sh"]