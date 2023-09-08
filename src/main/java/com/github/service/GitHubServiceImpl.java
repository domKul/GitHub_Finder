package com.github.service;

import com.github.exception.MediaTypeNotFound;
import com.github.exception.UserNotFoundException;
import com.github.model.CommitInfo;
import com.github.model.GitHubBranch;
import com.github.model.GitHubRepository;
import com.github.model.UserInfo;
import com.github.repository.GitHubService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService {
    private static final String GITHUB_API_URL = "https://api.github.com";

    @Override
    public List<GitHubRepository> getUserRepositories(String username) {
        String repoUrl = GITHUB_API_URL + "/users/" + username + "/repos";
        RestTemplate restTemplate = new RestTemplate();
        try {
            GitHubRepository[] repositories = restTemplate.getForObject(repoUrl, GitHubRepository[].class);
            if (repositories != null) {
                return Arrays.asList(repositories);
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User not Found");
        }
        return null;
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

    @Override
    public UserInfo informationAboutUser(String userName) {
        List<GitHubRepository> repositories = getUserRepositories(userName);

        UserInfo userInfo = new UserInfo();
        userInfo.setOwner(userName);
        userInfo.setRepositories(repositories);

        for (GitHubRepository repository : repositories) {
            String lastCommitSha = getLastCommitSha(userName, repository.getName());
            List<GitHubBranch> branches = getBranches(userName, repository.getName());
            repository.setBranchList(branches);

            repository.setLastCommitSha(lastCommitSha);
        }

        return userInfo;
    }

    @Override
    public List<GitHubBranch> getBranches(String username, String repositoryName) {
        String branchesUrl = GITHUB_API_URL + "/repos/" + username + "/" + repositoryName + "/branches";
        RestTemplate restTemplate = new RestTemplate();
        GitHubBranch[] branches = restTemplate.getForObject(branchesUrl, GitHubBranch[].class);

        if (branches != null) {
            return Arrays.asList(branches);
        }
        return new ArrayList<>();
    }




}
