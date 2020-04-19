# API-first-approach

This is a sample end-to-end of API-first-approach.

[![CircleCI](https://circleci.com/gh/quen2404/openapi-diff/tree/master.svg?style=svg)](https://circleci.com/gh/quen2404/openapi-diff/tree/master)

###Requirements
`jdk1.8+`


## Goal
1. Generator allows generation of API client libraries(base code) from given an OpenAPI Spec v2 and v3(Swagger)
2. Generate Swagger from code.
3. Compare two OpenAPI specifications(Swagger) while pharsing ignore file and render the difference to html file or markdown file.

-------
## How its works
In this sample you can find our "Swagger Petstore - OpenAPI 3.0" in 'src/test/resources/openapi/documentation/original.yml' as the original swagger.
Using  [OpenApi Generator](https://github.com/OpenAPITools/openapi-generator) for creating Api client libraries (SDK generation), server stubs, documentation and configuration automatically.
then by [SpringDoc](https://springdoc.org/) generating new swagger (post programing) called "generated" in 'src/test/resources/openapi/documentation/generated.json'.
We will compare two swaggers while customize the breaking changes by pharsing 'diffignore.yaml' file.
for more information about [OpenApi-diff-ignore](https://github.com/elibracha/openapi-diff-ignore).

-------
  ## How to run sample
-------
If you want to run, you'll need:
- Java 1.8+
- Maven 3.x.x or later
- Run Maven, specifying a location into which the completed Maven distro should be installed.
###Two ways to run
1.Using Junit, and checkout our test to see how its works.
```
./mvn clean install
```


2.Using [OpenApi-diff](https://github.com/elibracha/openapi-diff). this is Command line tool to detect breaking changes between two openapi specifications(swaggers).
 ####How to use the cli
 - download the jar file,and open CMD at location of jar.
```
java -jar openapi-diff-2.3.1-SNAPSHOT.jar <old swagger> <new swagger>
```
####Simple example:

```
java -jar openapi-diff-2.3.1-SNAPSHOT.jar original.yml generated.json -i --ignore-path .diffignore.yaml
```
-------

##  Command Line

```bash
usage: openapi-diff <old> <new>
    --debug                     Print debugging information
    --error                     Print error information
    --fail-on-incompatible      Fail only if API changes broke backward
                                compatibility
 -h,--help                      print this message
    --header <property=value>   use given header for authorisation
    --html <file>               export diff as html in given file
 -i                             activate diff ignore
    --ignore-path <file>        path to diff ignore file
    --info                      Print additional information
 -l,--log <level>               use given level for log (TRACE, DEBUG,
                                INFO, WARN, ERROR, OFF). Default: ERROR
    --markdown <file>           export diff as markdown in given file
 -o,--output <format=file>      use given format (html, markdown) for
                                output in file
    --off                       No information printed
    --query <property=value>    use query param for authorisation
    --state                     Only output diff state: no_changes,
                                incompatible, compatible
    --trace                     be extra verbose
    --version                   print the version information and exit
    --warn                      Print warning information
```



