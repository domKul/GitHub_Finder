package com.github.model;

public record GitHubBranch(
        String name,
        CommitInfo commit) {

}
