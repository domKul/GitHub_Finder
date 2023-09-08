package com.github.repository;

import com.github.model.GitHubBranch;
import com.github.model.GitHubRepository;
import com.github.model.UserInfo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.util.List;
@Repository
public interface GitHubService {
    List<GitHubRepository> getUserRepositories(String username);
    String getLastCommitSha(String username, String repositoryName);
     UserInfo informationAboutUser(String userName);
    List<GitHubBranch>getBranches(String username, String repositoryName);

}
