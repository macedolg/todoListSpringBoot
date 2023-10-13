FROM ubuntu:latest as build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install

EXPOSE 8081

COPY --from=build /target/todolist-1.0.0.jar todo.jar

ENTRYPOINT [ "java", "-jar", "todo.jar" ]