package com.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubBranch {
    @JsonProperty("name")
    private String branchName;
    @JsonProperty("commit")
    private CommitInfo commit;

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public CommitInfo getCommit() {
        return commit;
    }

    public void setCommit(CommitInfo commit) {
        this.commit = commit;
    }
}
