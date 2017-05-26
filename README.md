
# Nudge Elastic Stack connector

## Overview

The Nudge-ElasticStack Connector is a daemon that let you integrate your applications performance measures analyzed by [Nudge APM](https://www.nudge-apm.com/) into your Elastic Stack.

## Live demo
To view a live demo follow this link :
[bit.ly/nudge-demo-elastic](http://bit.ly/2f1NbUz)</a>

## Requirements
1. A [Nudge APM](https://www.nudge-apm.com/) api token
2. Elastic 2.3
3. Kibana 4.5.0
4. Java >= 1.7 (Open JDK and Oracle JVM have been tested)

## Getting started
First download and unpack our archive.

```
wget https://github.com/NudgeApm/nudge-elasticstack-connector/releases/download/v1.3.1/nudge-elasticstack-plugin-1.3.1.zip
unzip nudge-elasticstack-connector-1.3.1.zip
```

Then edit the properties file and set your own properties.
These are the mandatory properties you have to specify :


|Property|Value|
|-|-|
|nudge.api.token|[Nudge APM](https://www.nudge-apm.com/) API authentication token platform|
|nudge.apps.ids|Your application token in [Nudge API](https://monitor.nudge-apm.com/api-doc/)|
|elastic.index|The name of the Elastic index you want the plugin to write to|
|output.elastic.hosts|Elastic hosts (default http://localhost:9200)|

Finally start the service :

```
java -jar nudge-elasticstack-connector.jar -startDaemon
```

The plugin is now fetching live data from [nudge-apm.com](https://www.nudge-apm.com/) and writing them to your Elastic.
After running the connector, you can easily set up an initial Kibana dashboard using the shell script provided in the archive : `kibana_dashboard_init.sh`.

```
cd script
./kibana_dashboard_init.sh import
```

For more information about this script, read the [related documentation page](https://github.com/NudgeApm/nudge-elasticstack-connector/blob/master/script/kibana_dashboards_init/README.md).

## Documentation

Visit [www.nudge-apm.com](https://www.nudge-apm.com/integration) for the full documentation.
