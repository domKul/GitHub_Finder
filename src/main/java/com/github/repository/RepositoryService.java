package com.github.repository;


import com.github.GitHubRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryService {
    List<GitHubRepository> listNonForkRepository(String userName);
    String getLastCommitSha(String username, String repositoryName);

}
