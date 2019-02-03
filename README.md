# johnniang-api

## 目的

仅仅为有意思的想法提供 API，供 **[johnniang-portal](https://github.com/JohnNiang/johnniang-portal)** 项目请求并渲染。

## Docker 运行命令（推荐）

**pwd** 目录下应当包含 **johnniang-api-latest.jar** 文件，否则无法正确运行。

```bash
docker run -it -d --name=johnniang-api \
    -v `pwd`:/app \
    -v `pwd`:/root \
    -p 10086:8080 \
    openjdk:8-jdk-alpine \
    java -Xmx512m -Xms128m -jar \
    -Dserver.port=8080 \
    -Dspring.profiles.active=prod \
    -Djava.security.edg=file:/dev/./urandom \
    /app/johnniang-api-latest.jar
```