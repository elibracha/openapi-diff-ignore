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
<a name="oasVersion"></a>version | `string` | **REQUIRED**. This string MUST be the [semantic version number](https://semver.org/spec/v2.0.0.html) of the [Ignore Specification version](#versions) that the OpenAPI ignore document uses. The `version` field SHOULD be used by tooling specifications and clients to interpret the OpenAPI ignore document.
<a name="oasInfo"></a>info | `string` | Provides metadata about general info for the API ignore. The metadata MAY be used by tooling as required.
<a name="oasInfo"></a>project | `string` | Provides metadata about the Project using the ignore file. The metadata MAY be used by tooling as required.
<a name="paths"></a>paths | [Paths Object](#pathsObject) |  The available paths and operations for the API.

#### <a name="pathsObject"></a>Paths Object

Holds the relative paths to the individual endpoints and their operations.

##### Patterned Fields

Field Pattern | Type | Description
---|:---:|---
<a name="pathsPath"></a>/{path} | [Path Item Object](#pathItemObject) | A relative path to an individual endpoint. The field name MUST begin with a slash. Wildcard is allowed. When matching URLs, concrete (non-wildcard) paths would be matched before their wildcard counterparts. Templated paths with the same hierarchy but different templated names MUST NOT exist as they are identical. In case of ambiguous matching, it's up to the tooling to decide which one to use.

##### Path Templating Matching

Assuming the following paths, the concrete definition, `/pets/mine`, will be matched first if used:

```
  /pets/{petId}
  /pets/*         # matches all pets endpoints
  /*              # match all endpoints
```
NOTICE! When using wildcards all all endpoints matching the wildcard will apply the ignore specified.

##### Paths Object Example

```yaml
paths:
  /*:
    ignore:
      post:
        request:
          content-type:
            - application/octet-stream
        response:
          status:
            - default
            - 200
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


This object MAY be extended with [Specification Extensions](#specificationExtensions).

##### Path Item Object Example


```yaml
 get:
  parameters:
    - username
    - password
  response:
    status:
      - default
      - 200
```

#### <a name="operationObject"></a>Operation Object

Describes a single API operation on a path.

##### Fixed Fields

Field Name | Type | Description
---|:---:|---
<a name="operationDescription"></a>info | `string` | A verbose explanation of the operation behavior. [CommonMark syntax](http://spec.commonmark.org/) MAY be used for rich text representation.
<a name="operationParameters"></a>parameters | [[Parameter Object](#parameterObject) \| [Reference Object](#referenceObject)] | A list of parameters that are applicable for this operation. If a parameter is already defined at the [Path Item](#pathItemParameters), the new definition will override it but can never remove it. The list MUST NOT include duplicated parameters. A unique parameter is defined by a combination of a [name](#parameterName) and [location](#parameterIn). The list can use the [Reference Object](#referenceObject) to link to parameters that are defined at the [OpenAPI Object's components/parameters](#componentsParameters).
<a name="operationRequestBody"></a>request | [Request Body Object](#requestBodyObject) \| [Reference Object](#referenceObject) | The request body applicable for this operation.  The `requestBody` is only supported in HTTP methods where the HTTP 1.1 specification [RFC7231](https://tools.ietf.org/html/rfc7231#section-4.3.1) has explicitly defined semantics for request bodies.  In other cases where the HTTP spec is vague, `requestBody` SHALL be ignored by consumers.
<a name="operationResponses"></a>response | [Responses Object](#responsesObject) | **REQUIRED**. The list of possible responses as they are returned from executing this operation.

This object MAY be extended with [Specification Extensions](#specificationExtensions).

##### Operation Object Example

```yaml
request:
  content-type:
    - application/octet-stream
  response:
    status:
      - default
      - 200
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
