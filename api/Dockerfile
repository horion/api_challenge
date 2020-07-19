FROM openjdk:11-jre-slim

COPY target/api-1.jar /usr/local/dextra/api/

ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /usr/local/dextra/api/

ENTRYPOINT [ "java", "-jar", "api-1.jar" ]
#ENTRYPOINT [ "java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" , "-jar", "api-1.jar" ]


