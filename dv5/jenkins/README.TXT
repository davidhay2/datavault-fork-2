When we build in Jenkins with Docker we have to configure Java Version.

See Dockerfile.jenkins

The FROM line is what determines whether we are building with Java8 or Java17

either

FROM eclipse-temurin:8

or

FROM eclipse-temurin:eclipse-temurin:17.0.6_10-jdk

Remember : Class Files that compile and test okay for Java 8 will still work with Java 17.
 No newline at end of file
