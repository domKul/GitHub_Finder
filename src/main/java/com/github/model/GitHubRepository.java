package com.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepository {
    private String name;
    private String lastCommitSha;

    private String branchName;

    public String getBranchName() {
        return branchName;
    }


    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLastCommitSha() {
        return lastCommitSha;
    }

    public void setLastCommitSha(String lastCommitSha) {
        this.lastCommitSha = lastCommitSha;
    }
}
