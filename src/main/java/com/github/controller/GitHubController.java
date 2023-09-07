package com.github.controller;

import com.github.exception.UserNotFoundException;
import com.github.model.GitHubBranch;
import com.github.model.UserInfo;
import com.github.repository.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/github")
public class GitHubController {
    private final GitHubService gitHubService;

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/user-info/{username}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable String username) throws UserNotFoundException{
        try {
            UserInfo userInfo = gitHubService.informationAboutUser(username);
            return ResponseEntity.ok(userInfo);
        } catch (UserNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }

    }

}
