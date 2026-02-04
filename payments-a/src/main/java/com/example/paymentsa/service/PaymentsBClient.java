package com.example.paymentsa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentsBClient {
    @Autowired
    private S2STokenService s2STokenService;

    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> callPaymentsB(String url, Object requestBody) {
        String token = s2STokenService.getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
    }
}
