# elk-prometheus-grafana-stack

ELK + Prometheus + Grafana communication POC

This is POC to understand better how this applications communicate with each other:

- Grafana
- Prometheus
- Spring Boot
  - Actuator
  - Sleuth
  - Logback
- Elasticsearch
- Logstash
- Kibana
- Zipkin
- Graylog

First of all, increase linux virtual allowed space

```sh
$ sudo sysctl -w vm.max_map_count=262144
```

# Endpoints

| Service       | Address                                   |
| ------------- | ----------------------------------------- |
| Demo          | [localhost:8080](http://localhost:8080)   |
| Elasticsearch | [localhost:9200](http://localhost:9200)   |
| Grafana       | [localhost:3000](http://localhost:3000)   |
| Graylog       | [localhost:9000](http://localhost:9000)   |
| Kibana        | [localhost:5601](http://localhost:5601)   |
| Logstash      | [localhost:9600](http://localhost:9600)   |
| MongoDB       | [localhost:27017](http://localhost:27017) |
| Prometheus    | [localhost:9090](http://localhost:9090)   |
| Zipkin        | [localhost:9411](http://localhost:9411)   |

# Steps

- Open file prometheus.yml, on the last line change the spring-actuator target IP to your local IP (this will be fixed soon :) )
- Start the Spring Boot demo app with `cd demo && ./gradlew bootRun`
- Start docker-compose with `docker-compose -d up`)
  - Be patient, wait at least 5 minutes so all the services have plenty of time to comunicate with each other and setup the initial configuration. Grab a cup of coffee or tea or water or something more powerful :)
  - `docker ps` should list 8 services up
- Check if prometheus [target](http://localhost:9090/targets) 'spring-actuator' is **up**
- Open [Graylog](http://localhost:9000) with user admin, password admin
  - Go to System -> Inputs
  - Select input "GELF UDP" and Launch, fill it with:
    - Check the ckeckbox Global
    - Title: UDP
    - Bind address: 0.0.0.0
    - Port: 12201
    - Save
  - Again select input "GELF UDP" and Launch, fill it with:
    - DONT check the ckeckbox Global
    - Title: ELK Stack UDP
    - Bind address: 0.0.0.0
    - Port: 12202
    - Save
- Call the Spring Boot demo app at least once to generate a log `curl http://localhost:8080/info`
- Create the index **micro-\*** in [Kibana](http://localhost:5601/app/kibana#/management/kibana/indices) with timefield @timestamp
- Open [Grafana dashboard](http://localhost:3000) with user admin, password secret
- Go to Configuration->Datasources, "Add data source", choose ElasticSearch and input:
  - URL: http://elasticsearch:9200
  - Index name: micro-\*
  - Time field name: @timestamp
  - Click "Save & Test"
- Again go to Configuration->Datasources, "Add data source", choose Prometheus and input:
  - URL: http://prometheus:9090
  - Click "Save & Test"
- [Import](http://localhost:3000/dashboard/import) the Grafana dashboard file **grafana-dash.json**
- Keep hitting `http://localhost:8080/info`, `http://localhost:8080/warn` and `http://localhost:8080/error` to check logs in Kibana/Graylog and numbers in Grafana. Also checkout Zipkin for the tracing.

Done!

## License

MIT

**Free Software, Hell Yeah!**
