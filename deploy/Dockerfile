# x86
FROM bellsoft/liberica-openjdk-debian:21.0.4-cds
# FROM openjdk:21-ea-33-jdk-oracle
#FROM fxdom/maven-openjdk-21:3.9.6
#FROM registry.cn-hangzhou.aliyuncs.com/fxdom/maven-openjdk-21:3.9.6

LABEL org.opencontainers.image.authors="fx"

ARG application_name='number-admin'
ENV JAVA_OPTS=""


ENV TZ=Asia/Shanghai

RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

#RUN microdnf install vim

#RUN apk add --no-cache msttcorefonts-installer fontconfig
#RUN update-ms-fonts

RUN mkdir /app
COPY ./jar/$application_name.jar /app/java-application.jar
WORKDIR /app
#RUN chown -R javauser:javauser /app
#USER javauser

EXPOSE 8080

# ENTRYPOINT ["/usr/local/bin/dumb-init", "--"]

CMD ["bash", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/java-application.jar" ]

