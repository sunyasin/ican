# Back-end
##### Build module
    ./gradlew :back-end:clean :back-end:build
##### Start debug
    ./gradlew :back-end:bootRun --debug-jvm
    
##### Start and shutdown
For start and shutdown back-end use deploy.sh and shutdown.sh
to configure startup and shutdown scripts change CATALINA_HOME

##### Remote debug
Add next string to catalina.sh

    JAVA_OPTS="$JAVA_OPTS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
