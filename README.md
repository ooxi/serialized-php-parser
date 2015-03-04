# serialized-php-parser

Simple [MIT licensed](LICENSE) Java library to access data serialized by PHP.



# Usage

*serialized-php-parser* is published in the central Maven repository and can be
included using the following dependency information:

````xml
<dependency>
	<groupId>com.github.ooxi</groupId>
	<artifactId>serialized-php-parser</artifactId>
	<version>0.5.0</version>
</dependency>
````

Deserializing data is as simple as

````java
String data = "O:8:\"TypeName\":1:{s:3:\"foo\";s:3:\"bar\";}";
SerializedPhpParser phparser = new SerializedPhpParser(data);
PhpObject result = (PhpObject)phparser.parse();

System.out.println(result.attributes.get("foo"));
````


## Origin 

This is a fork from [Google Code Serialized Php Parser](http://code.google.com/p/serialized-php-parser/), based on the work of [Michele Catalano](https://github.com/ironpinguin).
