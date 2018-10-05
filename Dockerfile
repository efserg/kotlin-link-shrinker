FROM java:8-jre
MAINTAINER EfSerg <kaftkm@gmail.com>
ADD ./build/libs/link-shrinker-0.0.1-SNAPSHOT.jar /app/link-shrinker.jar
CMD ["java", "-jar", "/app/link-shrinker.jar"]
EXPOSE 8080