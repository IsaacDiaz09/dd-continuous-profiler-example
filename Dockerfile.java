FROM sapmachine:21-jdk-ubuntu-22.04

ARG DD_GIT_REPOSITORY_URL
ARG DD_GIT_COMMIT_SHA
ENV DD_GIT_REPOSITORY_URL=https://github.com/IsaacDiaz09/dd-continuous-profiler-example
ENV DD_GIT_COMMIT_SHA=d60d5cd1a919a91a3eb474065d66509606c4bcf5

COPY . /home
WORKDIR /home/java
RUN apt update && apt install -y curl
RUN curl -L -o dd-java-agent.jar 'https://dtdg.co/latest-java-tracer'
RUN ./gradlew --no-daemon installDist
CMD JAVA_OPTS="-Ddd.agent.host=$DD_AGENT_HOST -Ddd.profiling.enabled=true -javaagent:dd-java-agent.jar" ./build/install/movies-api-java/bin/movies-api-java
