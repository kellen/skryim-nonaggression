NonAggression
-------------

For people who think Skyrim is too scary; a SkyProc patcher which makes all NPCs cowardly and unaggressive.

Having a "no monsters" version would potentially be better, but this was easier.

This patch works, but is barely tested.

## Installation

Download the [latest zip file](https://github.com/kellen/skryim-nonaggression/releases) and install with Nexus Mod Manager.

## For programmers

### Prerequisites 

Clone [my fork](http://github.com/kellen/skyproc-library/) of [DienesToo's skyproc](https://bitbucket.org/DienesToo/skyproc-library) and install the dependencies for skyproc into your local maven and then build and install skyproc into your local maven.

### Build the distributable zip file

```
mvn clean package
```

The `target/NonAggression.zip` file can be installed with Nexus Mod Manager (NMM) and run directly or via SkyProc Unified Manager (SUM).

### Debugging

The SkyProc Unified Manager (SUM) starts a new process in which NonAggression.jar is run, so if you want to debug SUM + NonAggression, you'll need to edit the skyproc source and add debugging options to the command line produced in `SUMprogram#runJarPatcher()`, for example `-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=5005`. 
