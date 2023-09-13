package com.github.service;

import com.github.exception.BranchNotFoundException;
import com.github.exception.ForbiddenAccessException;
import com.github.exception.UserNotFoundException;
import com.github.model.CommitInfo;
import com.github.model.GitHubBranch;
import com.github.model.GitHubRepository;
import com.github.model.UserInfo;
import com.github.repository.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class GitHubServiceImpl implements GitHubService {
    private static final String GITHUB_API_URL = "https://api.github.com";
    private final WebClient webClient;

    @Autowired
    public GitHubServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(GITHUB_API_URL).build();
    }

    @Override
    public List<GitHubRepository> getUserRepositories(String username) {
        String repoUrl = "/users/" + username + "/repos";
        return webClient.get()
                .uri(repoUrl)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    if (HttpStatus.FORBIDDEN.equals(response.statusCode())) {
                        return Mono.error(new ForbiddenAccessException("Access Forbidden"));
                    } else if (HttpStatus.NOT_FOUND.equals(response.statusCode())) {
                        return Mono.error(new UserNotFoundException("User Not Found"));
                    } else {
                        return Mono.error(new UserNotFoundException("Something went wrong"));
                    }
                })
                .bodyToFlux(GitHubRepository.class)
                .collectList()
                .block();
    }

    @Override
    public Mono<String> getLastCommitSha(String username, String repositoryName) {
        String commitsUrl = "/repos/" + username + "/" + repositoryName + "/commits";

        return webClient.get()
                .uri(commitsUrl)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new UserNotFoundException("User  not found")))
                .bodyToMono(CommitInfo[].class)
                .map((CommitInfo[] commits) -> {
                    if (commits != null && commits.length > 0) {
                        return commits[0].sha();
                    }
                    return " No Sha";
                });
    }

    @Override
    public Flux<GitHubBranch> getBranches(String username, String repositoryName) {
        String branchesUrl = "/repos/" + username + "/" + repositoryName + "/branches";

        return webClient.get()
                .uri(branchesUrl)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, branch -> Mono.error(new BranchNotFoundException("Branch Not Found")))
                .bodyToFlux(GitHubBranch.class)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public UserInfo informationAboutUser(String userName, MediaType acceptedHeader) throws HttpMediaTypeNotAcceptableException {
        jsonAccepter(acceptedHeader);
        List<GitHubRepository> repositories = getUserRepositories(userName);

        UserInfo userInfo = new UserInfo();
        userInfo.setOwner(userName);
        userInfo.setRepositories(repositories);

        for (GitHubRepository repository : repositories) {
            String lastCommitSha = getLastCommitSha(userName, repository.getName()).block();
            Flux<GitHubBranch> branches = getBranches(userName, repository.getName());
            List<GitHubBranch> branchList = branches.collectList().block();
            repository.setBranchList(branchList);
            repository.setLastCommitSha(lastCommitSha);
        }

        return userInfo;
    }

    private void jsonAccepter(MediaType acceptHeader) throws HttpMediaTypeNotAcceptableException {
        if (!MediaType.APPLICATION_JSON.isCompatibleWith(acceptHeader)) {
            throw new HttpMediaTypeNotAcceptableException("Only JSON is accepted");
        }
    }

}
