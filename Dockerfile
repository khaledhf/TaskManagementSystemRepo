FROM openjdk:21
EXPOSE 9222
COPY target/TaskManagementSystem.jar TaskManagementSystem.jar
CMD ["java","-jar","TaskManagementSystem.jar"]
