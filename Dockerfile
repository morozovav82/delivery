FROM frolvlad/alpine-java:jdk8.202.08-slim
COPY target/delivery-2.0.jar /
CMD ["java", "-jar", "delivery-2.0.jar"]
