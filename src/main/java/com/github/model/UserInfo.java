package com.github.model;

import java.util.List;

public class UserInfo {
    private String username;
    private List<GitHubRepository> repositories;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<GitHubRepository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<GitHubRepository> repositories) {
        this.repositories = repositories;
    }
}
