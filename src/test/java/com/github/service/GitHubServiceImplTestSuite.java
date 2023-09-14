package com.github.service;

import com.github.exception.UserNotFoundException;
import com.github.model.GitHubBranch;
import com.github.model.GitHubRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GitHubServiceImplTestSuite {

    private GitHubServiceImpl gitHubService;

    @Mock
    private WebTestClient.Builder webTestClientBuilder;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        WebClient.Builder webClientBuilder = WebClient.builder();
        gitHubService = new GitHubServiceImpl(webClientBuilder);
    }

    @Test
     void testGetUserRepositories_Success() {
        // Given
        String username = "domKul";
        int expected = 12;

        // When
        List<GitHubRepository> userRepositories = gitHubService.getUserRepositories(username);

        // Then
        assertEquals(expected, userRepositories.size());
    }

    @Test
     void testGetUserRepositories_should_throw_user_not_found() {
        // Given
        String username = "domKulll";
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;

        // When
        Throwable exception = assertThrows(UserNotFoundException.class, () -> {
            gitHubService.getUserRepositories(username);
        });

        // Then
        System.out.println(exception.getMessage());
        assertEquals("User Not Found", exception.getMessage());
    }
    @Test
    void shouldFindBranches() {
        // Given
        String userName = "domKul";
        String repository = "GitHub_Finder";

        // When
        Flux<GitHubBranch> branches = gitHubService.getBranches(userName, repository);

        // Then
        StepVerifier.create(branches)
                .expectNextMatches(branch -> branch.name().equals("master"))
                .expectComplete()
                .verify();
    }
}
