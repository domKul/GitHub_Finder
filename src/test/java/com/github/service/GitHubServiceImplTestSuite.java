package com.github.service;

import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(classes = GitHubServiceImpl.class)
@AutoConfigureWebTestClient
public class GitHubServiceImplTestSuite {

    @MockBean
    private GitHubServiceImpl gitHubService;
    private WebTestClient webTestClient;




}
