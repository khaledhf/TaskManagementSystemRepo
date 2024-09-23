FROM tomcat:9.0

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY target/TaskManagementSystem.war /usr/local/tomcat/webapps/TaskManagementSystem.war

EXPOSE 8080
