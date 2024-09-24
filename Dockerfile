FROM openjdk:21
EXPOSE 2032
COPY target/TaskManagementSystem.jar TaskManagementSystem.jar
CMD ["java","-jar","TaskManagementSystem.jar"]

