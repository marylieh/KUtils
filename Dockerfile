FROM gradle
WORKDIR /usr/src/app
COPY . .
EXPOSE 8080
CMD [ "./gradlew", "build" ]
CMD [ "java", "-jar", "/usr/src/app/build/libs/KUtils-1.0-SNAPSHOT-all.jar" ]
