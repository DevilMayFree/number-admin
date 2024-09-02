## openjdk-maven
项目基础镜像包
构建基于openjdk和maven的镜像包

修改maven仓库为阿里云仓库地址
添加``dumb-init`` 修复镜像启动后项目进程pid=1问题

### 说明
1、基础镜像是bellsoft/liberica-openjdk-alpine:21.0.4

2、maven版本3.9.6

3、dumb-init版本v1.2.5


dumb-init使用源码构建,基于v1.2.5版本

gitee地址: https://gitee.com/deviltofree/dumb-init


### 构建

构建镜像:
```bash
docker build --no-cache -f Dockerfile -t maven-openjdk-21:3.9.6 .
```

添加标签:
```bash
docker tag maven-openjdk-21:3.9.6 fxdom/maven-openjdk-21:3.9.6
```

登录并且推送镜像:
```bash
# 登录dockerhub
docker login

# 推送镜像
docker push fxdom/maven-openjdk-21:3.9.6

# 退出dockerhub
docker logout
```

