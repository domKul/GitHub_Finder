# GitHub Repository API

This is a Java Spring Boot application that provides an API to list GitHub repositories of a user that are not forks. It also handles error responses for non-existing users and unsupported media types.

### Endpoint List:
- getUserInfo(PathVariable String username, @RequestHeader("Accept") MediaType acceptHeader)

   endpint addres --> http://localhost:8080/github/user-info/domkul 

   ``respons in JSON format with information about owner, repositories and branches with last commit SHA``



EXAMPLE OF RESPOSN

#### Status  200 OK 
```   
Status: 200 OK
{
    "owner": "domkul",
    "repositories": [
        {
            "name": "brudnopis",
            "lastCommitSha": "dcbc8254c44ee7aaec3c7d02dbb9184f00113bdf",
            "branchList": [
                {
                    "name": "main",
                    "commit": {
                        "sha": "dcbc8254c44ee7aaec3c7d02dbb9184f00113bdf"
                    }
                }
            ]
        }
    ]
}
```

#### Status 404 Not Found when userName not exist
```
{
"status ": 404,
"Message ": "User not Found"
}
```
#### Status 406 Not Acceptable when given header “Accept: application/xml”

```
{
    "type": "about:blank",
    "title": "Not Acceptable",
    "status": 406,
    "detail": "Acceptable representations: [application/json].",
    "instance": "/github/user-info/domkull"
}