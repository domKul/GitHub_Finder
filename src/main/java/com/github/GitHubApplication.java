package com.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GitHubApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitHubApplication.class, args);
    }



}
