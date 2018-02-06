if [ "$FM_HOME" == "" ]; then
    FM_HOME=`dirname $0`;
    FM_HOME="$FM_HOME/../";
fi

if [ "$FM_HEAP_SIZE" == "" ]; then
    FM_HEAP_SIZE="1024m";
fi

echo ""
echo "Configuration:"
echo "- FM_HOME: $FM_HOME";
echo "- FM_HEAP_SIZE: $FM_HEAP_SIZE";

java -Xms$FM_HEAP_SIZE \
     -Xmx$FM_HEAP_SIZE \
     -server \
     -Xloggc:$FM_HOME/logs/gc.log -verbose:gc \
     -XX:+PrintGCDetails \
     -XX:+HeapDumpOnOutOfMemoryError \
     -XX:HeapDumpPath=$DIRECTORY/../logs \
     -Dcom.sun.management.jmxremote \
     -Dcom.sun.management.jmxremote.port=9888 \
     -Dcom.sun.management.jmxremote.ssl=FALSE \
     -Dcom.sun.management.jmxremote.authenticate=FALSE \
     -Dlogging.config=$FM_HOME/config/logback-spring.xml \
     -Dlogging.file=$FM_HOME/logs/server.log \
     -Dspring.config.location=$FM_HOME/config/application.properties \
     -jar $FM_HOME/lib/file-manager-0.1.jar