FROM java:8

COPY demo.jar demo.jar

ENTRYPOINT ["java","/home/zdwwTest/data/skywalking/skywalking-agent.jar -Dskywalking.agent.service_name=springboot -Dskywalking.collector.backend_service=10.233.29.49:11800",
"-jar" , "/demo.jar"]