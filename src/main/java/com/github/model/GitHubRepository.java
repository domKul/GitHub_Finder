package com.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubRepository {
    private String name;
    private String lastCommitSha;
    private List<GitHubBranch> branchList;

    public List<GitHubBranch> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<GitHubBranch> branchList) {
        this.branchList = branchList;
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
