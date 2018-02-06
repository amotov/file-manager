ENV=$1
DIRECTORY=`dirname $0`
if [ "$ENV" == "" ]; then
    ENV="dev";
fi

java -Xms256m \
     -Xmx1024m \
     -server \
     -Xloggc:../logs/gc.log -verbose:gc \
     -XX:+PrintGCDetails \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=../logs \
     -Dcom.sun.management.jmxremote \
     -Dcom.sun.management.jmxremote.port=9888 \
     -Dcom.sun.management.jmxremote.ssl=FALSE \
     -Dcom.sun.management.jmxremote.authenticate=FALSE \
     -Dlogging.config=../config/logback-spring.xml \
     -Dspring.config.location=../config/application.properties \
     -jar $DIRECTORY/../lib/file-manager-0.1.jar