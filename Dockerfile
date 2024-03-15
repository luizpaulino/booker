# Use a imagem base do Amazon Corretto 17 com Alpine Linux e JDK
FROM amazoncorretto:17-alpine-jdk

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o arquivo JAR da aplicação para o contêiner
COPY build/libs/booker-0.0.1.jar /app/booker.jar

# Copie os recursos da aplicação para o contêiner
COPY src/main/resources/ /app/

# Exponha a porta em que a aplicação será executada
EXPOSE 8080

# Comando para executar a aplicação
CMD ["sh", "-c", "java -jar -Dspring.profiles.active=dev booker.jar"]
