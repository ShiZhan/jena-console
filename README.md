Jena Console
============

Introduction
------------

A portable console for [Jena](http://jena.apache.org/). 
It can be deployed as a single jar, without external dependencies.

How to use
----------

1.  Preparations

    * deploy [sbt](https://github.com/harrah/xsbt/wiki), which is required by
      most scala and java projects.
    * open sbt console in project root directory, run `update` to update
      project dependencies.
    * run sbt `assembly`. This task is based on [sbt assembly](https://github.com/sbt/sbt-assembly).

2.  Run main program

    * use `java -jar path/to/JenaConsole-assembly-1.0.jar` to enter console
    * use `java -jar path/to/JenaConsole-assembly-1.0.jar -h` to get command line help
    * or use the scripts in project root: jconsole (Linux|UNIX), jconsole.cmd (Windows),
      make sure the dependencies are in place.

3.  Model combining and importing

    * `JenaConsole -c <models>`, to generate a combined model
    * `JenaConsole -i <models>`, to importing them into the same repository.

    TDB database location is defined in 'JC_DATA' and defaults to
    '<current working directory>/.data'.

4.  SPARQL execution

    * use `JenaConsole -q <SPARQL query>` for query.
    * use `JenaConsole -u <SPARQL update>` for update.
    * or enter command shell, and use query or update command to input SPARQL scripts.

[download](http://goo.gl/ZGDskR)

Command line interface
----------------------

0. default entry: enter console
1. '-h' show help
2. '-v' show version
3. '-i' import specified model(s) into local triple storage
4. '-q' execute SPARQL query
5. '-u' execute SPARQL update
6. '-c' combine multiple models
7. '-R' do reasoning on given model with rule(s)

Alternatives
------------

For those who need REST interface, use [Fuseki](http://jena.apache.org/documentation/serving_data/index.html),
if Jena has been deployed already, then its individual command such as query,
update, infer, rdfcat, tdbinfo, tdbquery maybe directly usable. 

Other Triple Stores: [Sesame](http://www.openrdf.org/) and [Alibaba](http://www.openrdf.org/alibaba.jsp),
more can be found in [W3C LargeTripleStores](http://www.w3.org/wiki/LargeTripleStores)

Current graph databases: [Neo4j](http://www.neo4j.org/), [OrientDB](http://www.orientechnologies.com/orientdb/),
[Titan](http://thinkaurelius.github.io/titan/).

Author
======

[ShiZhan](http://shizhan.github.com/) (c) 2014 [Apache License Version 2.0](http://www.apache.org/licenses/)
