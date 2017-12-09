# SVN-broker

## Requirement
* Java v.9.0.1 (or highter)
* Gradle v.4.3.1 (or highter)

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
The REST-service schema is in /specs/serviece.yaml (Swagger-compatible).

### Warning!
Using POST-requests for /repositories requires the HTTP-headers:
```
Content-Type: multipart/form-data; boundary=HereGoes
```
Curl example:
``` bash
curl -F "url=http://svn.example.org/code" \
    -F "login=john_cena" \
    -F "password=tutudutu" \
    -F "id=111" \
    -H "Content-Type: multipart/form-data; boundary=HereGoes" \
    -X POST "http://localhost:3003/repositories"
```