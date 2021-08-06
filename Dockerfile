FROM adoptopenjdk:16-jre-hotspot
WORKDIR /opt/app
COPY target/*.jar languageteacher.jar
CMD ["java", "-jar", "languageteacher.jar"]
