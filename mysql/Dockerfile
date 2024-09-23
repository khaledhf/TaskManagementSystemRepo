FROM mysql:latest

ENV MYSQL_ROOT_PASSWORD=P@ssw0rd
ENV MYSQL_DATABASE=taskmanagement
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=P@ssw0rd

ADD init.sql /docker-entrypoint-initdb.d/

EXPOSE 3307