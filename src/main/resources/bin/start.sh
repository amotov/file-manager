ENV=$1
DIRECTORY=`dirname $0`
if [ "$ENV" == "" ]; then
    ENV="dev";
fi

java -Xms256m \
     -Xmx1024m \
     -server \
     -Xloggc:$DIRECTORY/../logs/gc.log -verbose:gc \
     -XX:+PrintGCDetails \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=$DIRECTORY/../logs \
     -Dcom.sun.management.jmxremote \
     -Dcom.sun.management.jmxremote.port=9888 \
     -Dcom.sun.management.jmxremote.ssl=FALSE \
     -Dcom.sun.management.jmxremote.authenticate=FALSE \
     -Dlogging.config=$DIRECTORY/../config/logback-spring.xml \
     -Dlogging.file=$DIRECTORY/../logs/server.log \
     -Dspring.config.location=$DIRECTORY/../config/application.properties \
     -jar $DIRECTORY/../lib/file-manager-0.1.jar