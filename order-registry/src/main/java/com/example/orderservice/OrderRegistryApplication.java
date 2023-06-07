package com.example.orderservice;

import com.example.orderservice.intercept.RestTemplateInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.Arrays;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
public class OrderRegistryApplication {
    private ClientRegistrationRepository clientRegistrationRepository;
    private OAuth2AuthorizedClientRepository clientRepository;

    public OrderRegistryApplication(ClientRegistrationRepository clientRegistrationRepository, OAuth2AuthorizedClientRepository clientRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.clientRepository = clientRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderRegistryApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(
                Arrays.asList(
                        new RestTemplateInterceptor(
                                clientManager(clientRegistrationRepository, clientRepository)
                        )
                )
        );

        return restTemplate;
    }

    @Bean
    public OAuth2AuthorizedClientManager clientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository clientRepository) {
        OAuth2AuthorizedClientProvider clientProvider
                = OAuth2AuthorizedClientProviderBuilder
                .builder()
                .clientCredentials()
                .build();
        DefaultOAuth2AuthorizedClientManager clientManager
                = new DefaultOAuth2AuthorizedClientManager(
                clientRegistrationRepository,
                clientRepository
        );
        clientManager.setAuthorizedClientProvider(clientProvider);
        return clientManager;
    }
}
