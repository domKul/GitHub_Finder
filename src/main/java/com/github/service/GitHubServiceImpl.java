package com.github.service;

import com.github.exception.UserNotFoundException;
import com.github.model.CommitInfo;
import com.github.model.GitHubBranch;
import com.github.model.GitHubRepository;
import com.github.model.UserInfo;
import com.github.repository.GitHubService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService {
    private static final String GITHUB_API_URL = "https://api.github.com";
    private final WebClient webClient;

    public GitHubServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(GITHUB_API_URL).build();
    }

    @Override
    public List<GitHubRepository> getUserRepositories(String username) {
        String repoUrl = "/users/" + username + "/repos";
        try {
            GitHubRepository[] repositories = webClient.get()
                    .uri(repoUrl)
                    .retrieve()
                    .bodyToMono(GitHubRepository[].class)
                    .block();

            if (repositories != null) {
                return Arrays.asList(repositories);
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User not Found");
        }
        return null;
    }

    @Override
    public Mono<String> getLastCommitSha(String username, String repositoryName) {
        String commitsUrl = "/repos/" + username + "/" + repositoryName + "/commits";

        return webClient.get()
                .uri(commitsUrl)
                .retrieve()
                .bodyToMono(CommitInfo[].class)
                .map(commits -> {
                    if (commits != null && commits.length > 0) {
                        return commits[0].getSha();
                    }
                    return null;
                });
    }

    @Override
    public UserInfo informationAboutUser(String userName) {
        List<GitHubRepository> repositories = getUserRepositories(userName);

        UserInfo userInfo = new UserInfo();
        userInfo.setOwner(userName);
        userInfo.setRepositories(repositories);

        for (GitHubRepository repository : repositories) {
            String lastCommitSha = getLastCommitSha(userName, repository.getName()).block();
            List<GitHubBranch> branches = getBranches(userName, repository.getName());
            repository.setBranchList(branches);

            repository.setLastCommitSha(lastCommitSha);
        }

        return userInfo;
    }

    @Override
    public List<GitHubBranch> getBranches(String username, String repositoryName) {
        String branchesUrl = "/repos/" + username + "/" + repositoryName + "/branches";

        GitHubBranch[] branches = webClient.get()
                .uri(branchesUrl)
                .retrieve()
                .bodyToMono(GitHubBranch[].class)
                .block();

        if (branches != null) {
            return Arrays.asList(branches);
        }
        return new ArrayList<>();
    }
}
