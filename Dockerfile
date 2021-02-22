FROM openjdk:11.0.8

# Install jq
RUN apt-get update && \
    apt-get install -y wget && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /bin
RUN wget "http://stedolan.github.io/jq/download/linux64/jq" && chmod 755 jq

WORKDIR /

COPY deploy-properties.json application/
COPY newrelic.yml application/
COPY target/dependency/newrelic/newrelic.jar application/newrelic/newrelic.jar
COPY target/api-appgate-calculator-*.jar application/
COPY wait-for.sh application/
COPY startup.sh startup.sh

CMD ["/bin/bash", "-c", "bash ./wait-for.sh $WAIT_FOR_DB -s -- ./startup.sh"]

CMD ["./startup.sh"]

# Application port
EXPOSE 8080

# JMX or Debugging port
EXPOSE 9010
