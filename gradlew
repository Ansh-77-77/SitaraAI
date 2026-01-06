#!/usr/bin/env sh

# Resolve links
PRG="$0"
while [ -h "$PRG" ]; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`"/$link"
  fi
done

SAVED_PWD=`pwd`
cd `dirname "$PRG"`/ || exit 1
APP_HOME=`pwd -P`
cd "$SAVED_PWD" || exit 1

CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

JAVA_CMD="java"
if [ -n "$JAVA_HOME" ]; then
  JAVA_CMD="$JAVA_HOME/bin/java"
fi

exec "$JAVA_CMD" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
