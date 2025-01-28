FROM openjdk:17-oracle

COPY bot.jar bot.jar

ENTRYPOINT ["java", "-jar", "bot.jar"]