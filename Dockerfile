FROM openjdk:8-jdk-alpine
ENV LANG=C.UTF-8
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
RUN apk update && apk add git  curl maven && \
        mkdir -p /root/.m2 && \
        echo "<settings xmlns=\"http://maven.apache.org/SETTINGS/1.0.0\"\n\
                 xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n\
                 xsi:schemaLocation=\"http://maven.apache.org/SETTINGS/1.0.0\n\
                                     http://maven.apache.org/xsd/settings-1.0.0.xsd\">\n\
            <mirrors>\n\
                <mirror>\n\
                    <id>aliyunmaven</id>\n\
                    <mirrorOf>*</mirrorOf>\n\
                    <name>阿里云公共仓库</name>\n\
                    <url>https://maven.aliyun.com/repository/public</url>\n\
                </mirror>\n\
            </mirrors>\n\
        </settings>" \
        > /root/.m2/settings.xml
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