# SVN-broker

## Requirement

* Java 8 (or higher)
* Gradle 4.3.1 (or higher)

## Building

Command for build:

``` bash
gradle build
```

## Running

Commands for run (Windows and Linux):

``` bash
svn-broker
```

## Using

The REST-service schema is in /specs/service.yaml (Swagger-compatible).

### Warning!

Using POST-requests for /repositories requires the HTTP-headers:

``` http
Content-Type: multipart/form-data; boundary=HereGoes
```

## Testing

You can use for test SVN-repository of the Codeblocks project: http://svn.code.sf.net/p/codeblocks/code

First of all you need to register your repository in web-service. You can do it with [Curl](https://curl.haxx.se/):

``` bash
curl -F "url=svn://svn.code.sf.net/p/codeblocks/code" \
     -F "id=codeblocks" \
     -H "Content-Type: multipart/form-data; boundary=HereGoes" \
     -X POST http://localhost:3003/repositories
```

After you can do GET-requests to repository using ID, which you noted on registration:
* /repositories - shows a list of registered repositories;
* /repositories/{id} - shows an overview information for repository with {id};
* /repositories/{id}/commits - shows a list of commits for repository with {id};
* /repositories/{id}/branches - shows a list of branches for repository with {id};
* /repositories/{id}/commits/{commitId} - shows an overview information for commit named {commitId} in repository {id};
* /repositories/{id}/branches/{branchId} - shows an overview information for branch named {branchId} in repository {id};

You can also run the Unit-tests:
``` bash
gradle test
```

Note: Some caching processes are working asynchronously and taking time. You can see info about it in warnings. Please, don't stop web-service until they finished.