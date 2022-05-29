# SVF-Java-example
Using SVF in Java Projects

## Project aims

Being written in C/C++, SVF is not easily accessible to developers who wish to utilize the native library in other languages (such as Java). This project aims to make it simple for Java developers to start using SVF in their projects as if it were a native JAR. That is, Java developers should be able to use the latest version of SVF without needing to write and maintain a siginificant amount of boilerplate to interact with the native library.

## Quick Start

### Prerequisites
#### Linux
```sh
# Toolchain, Node/NPM, and utils
sudo apt install zlib1g-dev unzip cmake gcc g++ nodejs npm wget curl libncurses5
# JDK
sudo apt install openjdk-17-jdk-headless
```
#### Mac
```sh
# Toolchain
xcode-select --install
# Node/NPM, utils
brew install unzip nodejs npm wget curl
# JDK
brew install openjdk@17
echo 'export PATH="/usr/local/opt/openjdk@17/bin:$PATH"' >> ~/.zshrc
```
### Steps
1. Install SVF Tools using NPM
```sh
npm install -g svf-tools
```
2. Clone this repository
```sh
git clone https://github.com/SVF-Tools/SVF-Java-example
```
3. Produce LLVM IR of `example.c`
```sh
cd SVF-Java-example
clang -S -c -g -fno-discard-value-names -emit-llvm example.c -o example.ll
```
4. Run the gradle project
```sh
./gradlew runDemo --args="example.ll"
```

## Approaches

There are a few ways to make SVF accessible to Java developers, not limited to:

1. Write a wrapper library to execute an SVF command line program as a process within a Java application 
2. Write a native wrapper and use JNI
3. Use a C++/Java bridge generator such as [javacpp](https://github.com/bytedeco/javacpp)
