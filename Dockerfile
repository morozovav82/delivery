FROM frolvlad/alpine-java:jdk8.202.08-slim
COPY target/delivery-1.0.jar /
CMD ["java", "-jar", "delivery-1.0.jar"]
