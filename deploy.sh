#!/usr/bin/env bash
CATALINA_HOME=/home/haspel/Soft/apache-tomcat-8.5.23/
./gradlew back-end:clean back-end:build
rm $CATALINA_HOME/webapps/backend.war
cp back-end/build/libs/backend.war $CATALINA_HOME/webapps/backend.war
$CATALINA_HOME/bin/startup.sh
tail -f $CATALINA_HOME/logs/catalina.out
