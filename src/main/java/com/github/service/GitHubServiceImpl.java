package com.github.service;

import com.github.CommitInfo;
import com.github.repository.GitHubService;
import com.github.GitHubRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService {
    private static final String GITHUB_API_URL = "https://api.github.com";

    @Override
    public List<GitHubRepository> getUserRepositories(String username) {
        String repoUrl = GITHUB_API_URL + "/users/" + username + "/repos";
        RestTemplate restTemplate = new RestTemplate();
        GitHubRepository[] repositories = restTemplate.getForObject(repoUrl, GitHubRepository[].class);
        return Arrays.asList(repositories);
    }

    @Override
    public String getLastCommitSha(String username, String repositoryName) {
        String commitsUrl = GITHUB_API_URL + "/repos/" + username + "/" + repositoryName + "/commits";
        RestTemplate restTemplate = new RestTemplate();
        CommitInfo[] commits = restTemplate.getForObject(commitsUrl, CommitInfo[].class);

        if (commits != null && commits.length > 0) {
            return commits[0].getSha();
        }

        return null;
    }

    public String getLastBranchName(String username, String repositoryName){
        String commitsUrl = GITHUB_API_URL + "/repos/" + username + "/" + repositoryName + "/branches";
        RestTemplate restTemplate = new RestTemplate();
        CommitInfo[] forObject = restTemplate.getForObject(commitsUrl, CommitInfo[].class);

        if (forObject != null && forObject.length > 0){
            return forObject[0].getBranchName();
        }

        return null;
    }



}
