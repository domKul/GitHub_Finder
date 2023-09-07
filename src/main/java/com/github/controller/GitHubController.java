package com.github.controller;

import com.github.model.UserInfo;
import com.github.repository.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/github")
public class GitHubController {
    private final GitHubService gitHubService;

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/user-info/{username}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(gitHubService.informationAboutUser(username));
    }
}
