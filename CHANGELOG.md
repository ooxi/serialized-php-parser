# serialized-php-parser 0.5.0

 * Publication in Maven central repository
 * Moved files to default Maven directory layout
 * Lowered JDK dependency to Java 1.5 since no features from Java 1.6 are used
 * Changed Maven groupId from `org.lorecraft.phparser` to `com.github.ooxi` to
   avoid conflict in the future
 * `JSONTransformer` marked deprecated and will be removed in v1.0.0
 * Updated test case to JUnit 4
 * Changed throws declaration from generic `Exception` to the most specific
 * Updated Maven dependencies
 * Changed star includes to explicit includes
 * Renamed license file `license.txt` to default file name `LICENSE`
 * Removed unnecessary entries from `.gitignore`
 * Updated and added license information to every file
 * Format source code to comply with Sun Coding Standards
 * Added explicit version `2.2.1` for build plugin `org.apache.maven.plugins`/`maven-source-plugin`


# serialized-php-parser 0.4.5

 * Add exceptions integer codes to interpret better the exceptions reasons.


# serialized-php-parser 0.4.4

 * Fix Bug Strings with 0 byte chars can't be interpreted.
 * Add deserializing of References 'R'.


# serialized-php-parser 0.4.3

 * Fix Bug with unexpected end of serialized String on the end of a String (missing "; chars).


# serialized-php-parser 0.4.2

 * Fix Bug with unexpected end of serialized String on the end of a String
