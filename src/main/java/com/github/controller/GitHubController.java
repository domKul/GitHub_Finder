package com.github.controller;

import com.github.model.UserInfo;
import com.github.repository.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/github")
public class GitHubController {
    private final GitHubService gitHubService;

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping(value = "/user-info/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserInfo getUserInfo(@PathVariable String username,
                                @RequestHeader("Accept") MediaType acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        return gitHubService.informationAboutUser(username, acceptHeader);

    }


}
