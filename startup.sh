#!/bin/bash
APP_PATH="application"
JAR=$(echo ${APP_PATH}/*jar)
MAINCLASS=$(jq --raw-output '.mainClass' $APP_PATH/deploy-properties.json)

JVMARGS=$(jq --raw-output '.jvmArguments' $APP_PATH/deploy-properties.json)

# NewRelic
NEW_RELIC_ENABLED=$(jq --raw-output '.newRelicEnabled' $APP_PATH/deploy-properties.json)
if [[ $NEW_RELIC_ENABLED = "true"  ]]; then
  JVMARGS+="  -javaagent:${APP_PATH}/newrelic/newrelic.jar \
    -Dnewrelic.config.file=${APP_PATH}/newrelic.yml \
    -Dnewrelic.environment=production"
fi

# JMX and Debug
SPECIAL_PORT="9010"
DEBUG_ENABLED=$(jq --raw-output '.debugEnabled' $APP_PATH/deploy-properties.json)
JXM_ENABLED=$(jq --raw-output '.jmxEnabled' $APP_PATH/deploy-properties.json)

if [[ $DEBUG_ENABLED = "true"  ]]; then

  JVMARGS+="  -agentlib:jdwp=transport=dt_socket,address=*:${SPECIAL_PORT},server=y,suspend=n"

elif [[ $JXM_ENABLED = "true" ]]; then

  JVMARGS+="  -Dcom.sun.management.jmxremote=true \
              -Dcom.sun.management.jmxremote.port=${SPECIAL_PORT} \
              -Dcom.sun.management.jmxremote.rmi.port=${SPECIAL_PORT} \
              -Dcom.sun.management.jmxremote.local.only=false \
              -Dcom.sun.management.jmxremote.authenticate=false \
              -Dcom.sun.management.jmxremote.ssl=false \
              -Djava.rmi.server.hostname=${HOST_IP}"
fi

# Starting application
trap 'kill -TERM $PID' TERM INT
java $JVMARGS -jar "${JAR}" -server -Dspring.profiles.active=${ENV} -Dserver.port=8080 ${MAINCLASS} &
PID=$!
wait $PID
trap - TERM INT
wait $PID
EXIT_STATUS=$?
