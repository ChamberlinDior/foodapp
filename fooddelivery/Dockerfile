# Étape 1 : Construire l'application
FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /opt/app

# Configurer l'encodage UTF-8
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8

# Copier les fichiers Maven
COPY mvnw ./mvnw
COPY .mvn .mvn
COPY pom.xml ./pom.xml

# Télécharger les dépendances Maven
RUN ./mvnw dependency:go-offline

# Copier le code source
COPY src ./src

# Packager l'application sans exécuter les tests
RUN ./mvnw clean package -DskipTests

# Étape 2 : Exécuter l'application dans une image minimaliste
FROM eclipse-temurin:21-jre-alpine
WORKDIR /opt/app

# Copier le fichier JAR généré
COPY --from=build /opt/app/target/*.jar app.jar

# Exposer le port de l'application
EXPOSE 8080

# Lancer l'application
ENTRYPOINT ["java", "-jar", "app.jar"]
