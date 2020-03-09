OpenAPI-diff-ignore
============
[![Build Status](https://travis-ci.com/elibracha/openapi-diff-ignore.svg?branch=master)](https://travis-ci.com/elibracha/openapi-diff-ignore)
[![codecov](https://codecov.io/gh/elibracha/openapi-diff-ignore/branch/master/graph/badge.svg)](https://codecov.io/gh/elibracha/openapi-diff-ignore)
[![Jion the Slack chat room](https://img.shields.io/badge/Slack-Join%20the%20chat%20room-orange)](https://join.slack.com/t/openapi-diff-ignore/shared_invite/enQtOTM4MzYxNDAxMTEwLTk0ZDlmYzcwYWVlNWRiNDA4YmI1MDIwYjZlMTk4Mjc5OTI3NjY0Zjg0ZTEyNjA0NzBkOWY3MmNhNjQ2NjgxYjY)

Parse a OpenAPI-diff ignore file specifications(1.x). 
Can be use to integrate with OpenAPI-diff and customize your breaking changes.

If you think you have found a bug, please file an issue in the [Github Issues](https://github.com/elibracha/openapi-diff-ignore/issues).

Ignore Specification
------------------------

### Schema
In the following description, if a field is not explicitly REQUIRED or described with a MUST or SHALL, it can be considered OPTIONAL.

##### Fixed Fields

Field Name | Type | Description
---|:---:|---
<a name="extends"></a>extends | `string` | Can be used to inherit a pre defined ignore file functionality. Provides metadata about basic ignore file for your custom API ignore. (Like -> ".default")
<a name="version"></a>version | `string` | **REQUIRED**. This string MUST be the [semantic version number](https://semver.org/spec/v2.0.0.html) of the [Ignore Specification version](#versions) that the OpenAPI ignore document uses. The `version` field SHOULD be used by tooling specifications and clients to interpret the OpenAPI ignore document.
<a name="info"></a>info | `string` | Provides metadata about general info for the API ignore. The metadata MAY be used by tooling as required.
<a name="project"></a>project | `string` | Provides metadata about the Project using the ignore file. The metadata MAY be used by tooling as required.
<a name="paths"></a>paths | [Paths Object](#pathsObject) |  The available paths and operations for the API that needed to be ignored.

#### <a name="pathsObject"></a>Paths Object

Holds the relative paths to the individual endpoints and their operations.

##### Patterned Fields

Field Pattern | Type | Description
---|:---:|---
<a name="pathsPath"></a>/{path} | [Path Item Object](#pathItemObject) | A relative path to an individual endpoint. The field name MUST begin with a slash. Wildcard is allowed. When matching URLs. In case of ambiguous matching, it's up to the tooling to decide the execution order.

##### Path Wildcard Matching

Assuming the following paths, the concrete definition, `/pets/mine`, will be matched if used:

```
  /pets/**        # will match and also match all pets endpoints
  /**             # will match and also will match all endpoints
```
NOTICE! When using wildcards all endpoints matching the wildcard will apply the ignore specified.

##### Paths Object Example

```yaml
paths:
  /pet/**, /store/**:        # this path will match any 'pet' or 'store' endpoint 
    get:                      
      response:
        200:
          new: true
      parameters:
        - username
        - password

    post:
      request:
        content:
          application/json, application/octet-stream: $
      response:
        200:
          new: true
          content:
            application/json:
              schema:
                properties:
                  - petId
                  - quantity
                  - shipDate
                  - complete
      security:
        petstore_auth:
          - 'write:pets'
          - 'read:pets'

  /user/login:               # this path will match only '/user/login' endpoint. 
    post: $        
```

#### <a name="pathItemObject"></a>Path Item Object

Describes the operations available on a single path.
A Path Item MAY be empty, due to [ACL constraints](#securityFiltering).
The path itself is still exposed to the documentation viewer but they will not know which operations and parameters are available.

##### Fixed Fields

Field Name | Type | Description
---|:---:|---
<a name="pathItemGet"></a>get | [Operation Object](#operationObject) | A definition of a GET operation on this path.
<a name="pathItemPut"></a>put | [Operation Object](#operationObject) | A definition of a PUT operation on this path.
<a name="pathItemPost"></a>post | [Operation Object](#operationObject) | A definition of a POST operation on this path.
<a name="pathItemDelete"></a>delete | [Operation Object](#operationObject) | A definition of a DELETE operation on this path.
<a name="pathItemOptions"></a>options | [Operation Object](#operationObject) | A definition of a OPTIONS operation on this path.
<a name="pathItemHead"></a>head | [Operation Object](#operationObject) | A definition of a HEAD operation on this path.
<a name="pathItemPatch"></a>patch | [Operation Object](#operationObject) | A definition of a PATCH operation on this path.
<a name="pathItemTrace"></a>trace | [Operation Object](#operationObject) | A definition of a TRACE operation on this path.

##### Path Item Object Example


```yaml
 post:
      request:
        content:
          application/json, application/octet-stream: $
      response:
        200:
          new: true
          content:
            application/json:
              schema:
                properties:
                  - petId
                  - quantity
                  - shipDate
                  - complete
      security:
        petstore_auth:
          - 'write:pets'
          - 'read:pets'
put: $
delete: $
```

#### <a name="operationObject"></a>Operation Object

Describes a single API ignore operation on a path.

##### Fixed Fields

Field Name | Type | Description
---|:---:|---
<a name="security"></a>security | [Security Object](#parameterObject) | A list of security requirements that are going to be ignored.
<a name="parameters"></a>parameters | [Parameter Object](#parameterObject)| A list of parameters that are going to be ignored.
<a name="request"></a>request | [Request Object](#requestBodyObject) | The request body applicable for this operation. can specify ignore for content-type.
<a name="response"></a>response | [Response Object](#responsesObject) | The list of possible response statuses that are going to be ignored.

##### Operation Object Example

```yaml
request:
  content:
    application/json, application/octet-stream: $

parameters:
  - username
  - password

response:
  200:
    new: true
    content:
      application/json:
        schema:
          properties:
            - petId
            - quantity
            - shipDate
            - complete

security:
  petstore_auth:
    - 'write:pets'
    - 'read:pets'
```
#### <a name="securityObject"></a>Security Object

Describes a security requirements that needed to be ignored.

##### Fixed Fields

Field Name | Type | Description
---|:---:|---
<a name="schmea"></a>{schema} | Map<String, List> | A map of security requirements that are going to be ignored.
##### Security Object Example

```yaml
security:
  petstore_auth:
    - 'write:pets'
    - 'read:pets'
  api_key:
    - 'read:users'
```

Documentation
-------------

More information can be found on [Docs][openapi-diff-ignore-home].


Where can I get the latest release?
-----------------------------------
You can download release source from our release page.

Contributing
------------

If you are interested in the development of OpenAPI-diff-ignore, please consult the 
documentation first and afterwards you are welcome to join the developers 
mailing list to ask question or discuss new ideas / features / bugs etc.

Take a look into the [contribution guidelines](CONTRIBUTING.md).



Quick Build
-------
If you want to bootstrap openapi-diff-ignore, you'll need:
- Java 1.8+
- Maven 3.x.x or later
- Run Maven, specifying a location into which the completed Maven distro should be installed:
```
./mvnw clean package
```

[openapi-diff-ignore-home]: https://maven.apache.org/
