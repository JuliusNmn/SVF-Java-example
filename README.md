# SVF-Java-example
Using SVF in Java Projects

## Project aims

Being written in C/C++, SVF is not easily accessible to developers who wish to utilize the native library in other languages (such as Java). This project aims to make it simple for Java developers to start using SVF in their projects as if it were a native JAR. That is, Java developers should be able to use the latest version of SVF without needing to write and maintain a siginificant amount of boilerplate to interact with the native library.

## Approaches

There are a few ways to make SVF accessible to Java developers, not limited to:

1. Write a wrapper library to execute an SVF command line program as a process within a Java application 
2. Write a native wrapper and use JNI
3. Use a C++/Java bridge generator such as [javacpp](https://github.com/bytedeco/javacpp)
