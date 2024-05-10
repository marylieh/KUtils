FROM gradle
WORKDIR /usr/src/app
COPY . .
EXPOSE 8080
CMD [ "java", "-jar", "/usr/src/app/Main-JAR/KUtils-1.0-SNAPSHOT-all.jar" ]
