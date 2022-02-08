FROM gradle
WORKDIR /usr/src/app
COPY . .
EXPOSE 8080
CMD [ "gradle", "run" ]
