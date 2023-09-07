package com.github.model;

import java.util.List;

public class UserInfo {
    private String owner;
    private List<GitHubRepository> repositories;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<GitHubRepository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<GitHubRepository> repositories) {
        this.repositories = repositories;
    }
}
