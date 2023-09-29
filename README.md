# GitHub Repository API

This is a Java Spring Boot application that provides an API to list GitHub repositories of a user that are not forks. It also handles error responses for non-existing users and unsupported media types.
# [Docker image](https://hub.docker.com/r/dominikdk/github_finder)

## Endpoint List:
- getUserInfo(PathVariable String username, @RequestHeader("Accept") MediaType acceptHeader)

   endpint addres --> http://localhost:8080/github/user-info/domkul 

   ``respons in JSON format with information about owner, repositories and branches with last commit SHA``



EXAMPLE OF RESPOSN

#### Status  200 OK 
```   
{
    "owner": "domkul",
    "repositories": [
        {
            "name": "GitHub_Finder",
            "lastCommitSha": "e269b7ec220dc88db7f728026fb9f2b837e0716d",
            "branchList": [
                {
                    "name": "master",
                    "commit": {
                        "sha": "e269b7ec220dc88db7f728026fb9f2b837e0716d"
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
```
