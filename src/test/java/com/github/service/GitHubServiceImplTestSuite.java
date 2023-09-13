package com.github.service;

import com.github.model.GitHubRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GitHubServiceImplTestSuite {

    @Autowired
    private GitHubServiceImpl gitHubService;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Test
    public void testGetUserRepositories_Success() {
        // Given
        String user = "domkul";

        WebClient webClient = mock(WebClient.class);
        WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec<?> requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);


        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.onStatus(eq(HttpStatus.FORBIDDEN::equals), any())).thenReturn(responseSpec);
        when(responseSpec.onStatus(eq(HttpStatus.NOT_FOUND::equals), any())).thenReturn(responseSpec);

        // When
        List<GitHubRepository> userRepositories = gitHubService.getUserRepositories(user);

        // Then
        assertEquals(12, userRepositories.size());
    }


}
