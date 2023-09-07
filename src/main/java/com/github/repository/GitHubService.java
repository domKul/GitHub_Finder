package com.github.repository;

import com.github.GitHubRepository;

import java.util.List;

public interface GitHubService {
    List<GitHubRepository> getUserRepositories(String username);
    String getLastCommitSha(String username, String repositoryName);
    String getLastBranchName(String username, String repositoryName);


}
