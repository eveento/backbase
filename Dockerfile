FROM openjdk:17-alpine
WORKDIR /backbase
COPY target/*.jar app.jar
RUN addgroup appusers && adduser -S appuser -G appusers
USER appuser:appusers
EXPOSE 8080/tcp
ENTRYPOINT ["java","-jar","/backbase/app.jar"]