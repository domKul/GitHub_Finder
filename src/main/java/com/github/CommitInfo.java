package com.github;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitInfo {
    private String sha;
    private String branchName;


    public CommitInfo() {
    }
@JsonProperty("name")
    public String getBranchName() {
        return branchName;
    }

    @JsonProperty("name")
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    @JsonProperty("sha")
    public String getSha() {
        return sha;
    }

    @JsonProperty("sha")
    public void setSha(String sha) {
        this.sha = sha;
    }

}
