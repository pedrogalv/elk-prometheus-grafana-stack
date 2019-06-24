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

First of all, increase linux virtual allowed space

```sh
$ sudo sysctl -w vm.max_map_count=262144
```
# Endpoints
| Service | Address |
| ------ | ------ |
| Demo | [localhost:8080](http://localhost:8080) |
| Prometheus | [localhost:9090](http://localhost:9090)|
| Grafana | [localhost:3000](http://localhost:3000) |
| Kibana | [localhost:5601](http://localhost:5601) |
| Elasticsearch | [localhost:9200](http://localhost:9200) |
| Logstash | [localhost:9600](http://localhost:9600) |

# Steps
* Start the spring boot demo app
* Start docker-compose
    * `docker ps` should list 5 services up
* Check if prometheus [target](http://localhost:9090/targets) 'spring-actuator' is __up__
* Create the [following]((https://imgur.com/a/EmR2g6H)) data sources in [Grafana](http://localhost:3000/datasources)
* [Import](http://localhost:3000/dashboard/import) the Grafana dashboard file **grafana-dash.json**
* Create the index **micro-\*** in [Kibana](http://localhost:5601/app/kibana#/management/kibana/indices)
* Hit `localhost:8080/info` and `localhost:8080/warn` to check logs in kibana and numbers in grafana
* 
Done!

License
-----------------
MIT


**Free Software, Hell Yeah!**
