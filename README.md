git status# PQLetta

PQLetta is a lightweight Java ORM for Prometheus.  
It allows defining PromQL templates and binding them to parameters via configuration files.  
Queries are executed by identifier through a built-in Prometheus HTTP API client,  
enabling structured metric access without hardcoding PromQL.

[![License](https://img.shields.io/badge/license-Apache--2.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

---

## Maven

Available on Maven Central:
[io.github.byzatic:pqletta](https://search.maven.org/artifact/io.github.byzatic/pqletta)

```xml
<dependency>
  <groupId>io.github.byzatic</groupId>
  <artifactId>pqletta</artifactId>
  <version>0.0.1</version>
</dependency>
```

---

## Key Features

- Declarative configuration of Prometheus queries via JSON (`QueryConfiguration`, `ServerDescription`)
- Template engine for parametrized PromQL queries
- Pluggable interfaces for client, DAO, and template formatting
- Support for range queries, alerts, rules, series, targets, and more
- Built-in result models and error hierarchies
- Unit-tested and designed for integration into SCADA/monitoring systems

---

## Architecture Overview

### Response Model

```text
+-----------------------------+
|       ResponseSuccess       |
+-------------+---------------+
              ^ implements
              |
              |
              |
  +-----------+----------+--------------------+------------------+-------------------+-------------------+
  |                      |                    |                  |                   |                   |
  |                      |                    |                  |                   |                   |
  v                      v                    v                  v                   v                   v
+----------------+ +----------------+ +----------------+ +----------------+ +----------------+ +----------------+
| MetricMetadata | | PrometheusAlert| |PrometheusResult| | PrometheusRule | |PrometheusSeries| |PrometheusTarget|
+----------------+ +----------------+ +----------------+ +----------------+ +----------------+ +----------------+
                           |
                           | uses
                           v
                        +--------+       uses      +------------+
                        |  Data  +---------------->+  Result    |
                        +--------+                 +------------+
                                                         |
                                                         | uses
                                                         v
                                                   +------------+
                                                   |   Value    |
                                                   +------------+
                                                         ...
```

These are composed using:

```
Data -> Result -> Value
```

### Error Model

```text
                            +----------------------------+
                            |    ClientTransportError    |
                            +-----------+----------------+
                                        ^
                                        |
                               extends  |
                                        |
          +-----------------------------+----------------------------+
          |                                                          |
+----------------------------+                       +-----------------------------+
|  ClientTransportError      |                       |  PrometheusApiError         |
+----------------------------+                       +-----------------------------+
          ^ implements                                             ^ implements
          |                                                        |
          |                                                        |
          |                                                        |
+----------------------------+                       +-----------------------------+
| GenericClientTransportError|                       | GenericPrometheusApiError   |
+----------------------------+                       +-----------------------------+
```

Both error types implement `ResponseFailure`.

---

## Example Usage

### 1. Prometheus Query Config File

```json
{
  "server_description": {
    "url": "http://localhost:9090/",
    "ssl_verify": "False"
  },
  "query_description": [
    {
      "type": "cpu",
      "identifier": "CPU load idle",
      "upper_limit": "90",
      "lower_limit": "80",
      "step": "7",
      "time_range": "9",
      "labels": [
        {
          "key": "mode",
          "value": "idle",
          "sign": "="
        }
      ]
    }
  ]
}
```

See: `example.PrometheusQueryConfigurations.json`

---

### 2. PromQL Template

```promql
# type:cpu
(((((count(count(node_cpu_seconds_total${TAGLINEPLACEHOLDER}) by (cpu)) - 
    avg(sum by (mode)(rate(node_cpu_seconds_total${TAGLINEPLACEHOLDER}[30m])))) * 100)
    / count(count(node_cpu_seconds_total${TAGLINEPLACEHOLDER}) by (cpu))) < bool ${LLIMITPLACEHOLDER}))
```

See: `example.PrometheusQueryTemplates.txt`

---

## Getting Started

1. Load a `PrometheusQueryConfiguration` using `PromqlConfigurationDAO`
2. Bind query parameters to a PromQL template from the repository
3. Execute with `PrometheusAPIClient`
4. Handle results via `ResponseSuccess` or errors via `ResponseFailure`

---

## Main Interfaces

| Interface                         | Description                                  |
|----------------------------------|----------------------------------------------|
| `PQletaInterface`                | Main entry point to the engine               |
| `PrometheusAPIClientInterface`   | HTTP abstraction over Prometheus endpoints   |
| `PromqlConfigurationDAOInterface` | Loads JSON config files                      |
| `PromqlTemplateRepositoryInterface` | Loads and parses PromQL templates           |

---

## Project Structure

```text
.
├── client
│   ├── dto (request/response models)
│   ├── impl (query executor and handler)
│   └── exceptions
├── p_q_leta
│   ├── configuration_dao
│   ├── template_repository
│   └── impl (core engine)
├── annotations
├── example.PrometheusQueryConfigurations.json
├── example.PrometheusQueryTemplates.txt
```

---

## License

This project is licensed under the Apache-2.0 License.  
See: [https://www.apache.org/licenses/LICENSE-2.0](https://www.apache.org/licenses/LICENSE-2.0)

---

## Author

Svyatoslav Vlasov  
GitHub: [@byzatic](https://github.com/byzatic)  
Email: s.vlasov.home@icloud.com

---

## Repository

https://github.com/byzatic/PQLetta
