# x86
# FROM fxdom/maven-openjdk-21:3.9.6
FROM registry.cn-hangzhou.aliyuncs.com/fxdom/maven-openjdk-21:3.9.6

LABEL org.opencontainers.image.authors="fx"

ARG application_name='number-admin'
ENV JAVA_OPTS=""

RUN mkdir /app
COPY ./target/$application_name.jar /app/java-application.jar
WORKDIR /app
RUN chown -R javauser:javauser /app
USER javauser

EXPOSE 8080

ENTRYPOINT ["/usr/local/bin/dumb-init", "--"]

CMD ["bash", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app/java-application.jar" ]

