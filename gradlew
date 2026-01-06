#!/usr/bin/env sh

APP_HOME="$(cd "$(dirname "$0")" && pwd)"

CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar:$APP_HOME/gradle/wrapper/gradle-wrapper-shared.jar"

JAVA_CMD="java"
[ -n "$JAVA_HOME" ] && JAVA_CMD="$JAVA_HOME/bin/java"

exec "$JAVA_CMD" -classpath "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"
