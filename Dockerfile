# syntax=docker/dockerfile:1.6

############################
# 1) BUILD (Maven + JDK 21)
############################
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /workspace

# Cache des dépendances Maven
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests dependency:go-offline

# Sources
COPY src ./src

# Build du jar (skip tests pour l'image)
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests package

############################
# 2) RUNTIME (JRE 21 léger)
############################
FROM eclipse-temurin:21-jre-alpine

# Bonnes pratiques JVM en conteneur
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0 -Dfile.encoding=UTF-8"

# Port d'écoute (Spring écoute 8080 par défaut)
ENV PORT=8080
EXPOSE 8080

WORKDIR /app
COPY --from=build /workspace/target/simulator-0.0.1-SNAPSHOT.jar /app/app.jar

# (Facultatif) Healthcheck si Actuator est ajouté au projet
# HEALTHCHECK --interval=30s --timeout=3s --start-period=20s \
#   CMD wget -qO- http://localhost:${PORT}/actuator/health | grep -q '"status":"UP"' || exit 1

# Utilisateur non-root
RUN addgroup -S app && adduser -S app -G app
USER app

# Démarrage — on laisse la possibilité de surcharger le port via $PORT
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT} -jar /app/app.jar"]
