package com.github.controller;

import com.github.GitHubRepository;
import com.github.UserInfo;
import com.github.repository.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/github")
public class GitHubController {
    private final GitHubService gitHubService;

    @Autowired
    public GitHubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/repositories/{username}")
    public ResponseEntity<List<GitHubRepository>> getUserRepositories(@PathVariable String username) {
        List<GitHubRepository> repositories = gitHubService.getUserRepositories(username);
        if (repositories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(repositories);
    }

//    @GetMapping("/last-commit/{username}/{repositoryName}")
//    public ResponseEntity<String> getLastCommitSha(@PathVariable String username, @PathVariable String repositoryName) {
//        String lastCommitSha = gitHubService.getLastCommitSha(username, repositoryName);
//        if (lastCommitSha == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(lastCommitSha);
//    }

    @GetMapping("/user-info/{username}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable String username) {
        List<GitHubRepository> repositories = gitHubService.getUserRepositories(username);
        if (repositories.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setRepositories(repositories);

        for (GitHubRepository repository : repositories) {
            String lastCommitSha = gitHubService.getLastCommitSha(username, repository.getName());
            String lastBranchName = gitHubService.getLastBranchName(username,repository.getName());
            repository.setBranchName(lastBranchName);

            repository.setLastCommitSha(lastCommitSha);
        }

        return ResponseEntity.ok(userInfo);
    }
}
