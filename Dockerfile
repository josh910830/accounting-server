FROM openjdk:11-jre

ENV TZ=Asia/Seoul
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY target/accounting-server-*.jar accounting-server.jar

ENTRYPOINT ["java", "-jar", "accounting-server.jar"]
CMD ["--spring.profiles.active=prod"]
