FROM openjdk:23
VOLUME /tmp
EXPOSE 8080
COPY target/testproject-0.0.1.jar testproject.jar
ENTRYPOINT ["java","-jar","/testproject.jar"]
