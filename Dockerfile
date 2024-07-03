FROM bellsoft/liberica-openjdk-alpine:21
WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/shopping-platform.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "shopping-platform.jar"]
