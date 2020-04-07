# C2MON : CERN Control and Monitoring Platform
## Standalone application for retrieving data from C2MON via REST

[![build status](https://gitlab.cern.ch/c2mon/c2mon-web-restapi/badges/master/pipeline.svg)](https://gitlab.cern.ch/c2mon/c2mon-web-restapi/commits/master)

The CERN Control and Monitoring Platform (C2MON) is a heterogeneous data acquisition and monitoring framework. It contains many useful features
such as historical metric persistence and browsing, command execution and alerting. It can be suitable for building many different types
of monitoring and control system.

## Documentation
See the current [reference docs][].

## Issue Tracking
All C2MON issues are tracked and maintained within the [CERN JIRA][] system. Alternatively, you can send an email to c2mon-support@cern.ch.

## Building from Source
C2MON uses a [Maven][]-based build system. In the instructions
below, `mvn` is invoked from the root of the source tree.

### Prerequisites

[Git][] and [JDK 11.0.6 or later][JDK11 build]

Be sure that your `JAVA_HOME` environment variable points to the `JDK 11` folder
extracted from the JDK download.

### Check out sources
`git clone git@github.com:c2mon/c2mon-web-restapi.git`

### Compile and test; build all jars, distribution zips, and docs
`mvn package`

### Running the project
Make sure that you start first the C2MON server before executing the command. By default the project expects C2MON running on the same machine.

`mvn spring-boot:run`

## Contributing
[Pull requests][] are welcome; see the [contributor guidelines][] for details.

## License
C2MON is released under the [GNU LGPLv3 License][].

[reference docs]: http://c2mon.web.cern.ch/c2mon/docs/
[CERN JIRA]: https://its.cern.ch/jira/projects/CM
[Maven]: http://maven.apache.org
[Git]: http://help.github.com/set-up-git-redirect
[JDK11 build]: https://www.oracle.com/java/technologies/javase-jdk11-downloads.html
[Pull requests]: http://help.github.com/send-pull-requests
[contributor guidelines]: /CONTRIBUTING.md
[GNU LGPLv3 License]: /LICENSE
