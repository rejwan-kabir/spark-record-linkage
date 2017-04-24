# TL;DR

Please install `JDK-8` and `Spark-2.1.0`. Make sure `java` and `spark-submit` are in `$PATH`
~~~bash
./run.sh <input-directory> <output-directory>
~~~

For instance, running
~~~bash
./run.sh input output
~~~

will delete any existing `output` directory, take `input` as input and write output json inside `output` directory.

Please make sure you have enough permission for specified directories.
