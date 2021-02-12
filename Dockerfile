FROM frolvlad/alpine-java:jdk8.202.08-slim
COPY target/delivery-1.1.jar /
CMD ["java", "-jar", "delivery-1.1.jar"]
