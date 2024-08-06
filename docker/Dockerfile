# Use the official MySQL image as the base image
FROM mysql:latest

# Set the MySQL root password
ENV MYSQL_ROOT_PASSWORD=root

# Set the MySQL username and password
ENV MYSQL_USER=springstudent
ENV MYSQL_PASSWORD=springstudent

# Copy the initialization script into the container
COPY script.sql /docker-entrypoint-initdb.d/
