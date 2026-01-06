#!/usr/bin/env sh

DIR="$(cd "$(dirname "$0")" && pwd)"

GRADLE_HOME="$DIR/gradle/wrapper"

JAVA_CMD=java
if [ -n "$JAVA_HOME" ] ; then
  JAVA_CMD="$JAVA_HOME/bin/java"
fi

exec "$JAVA_CMD" -jar "$GRADLE_HOME/gradle-wrapper.jar" "$@"
