package com.github.controller;

import com.github.model.GitHubRepository;
import com.github.model.UserInfo;
import com.github.repository.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/github")
public class GitHubController {
    private final GitHubService gitHubService;

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping(value = "/user-info/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable String username,
                                                @RequestHeader("Accept") MediaType acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        UserInfo userInfo = gitHubService.informationAboutUser(username, acceptHeader);
        return ResponseEntity.ok(userInfo);

    }

    @GetMapping(value = "/repo/{userName}")
    public ResponseEntity<List<GitHubRepository>>getRepo(@PathVariable String userName){
        return ResponseEntity.ok(gitHubService.getUserRepositories(userName));
    }

}
