package com.example.paymentsa.service;

import com.example.paymentsa.model.PaymentRequest;
import com.example.paymentsa.model.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentsAService {

    private final RestTemplate restTemplate;
    private final String orchestrationUrl;

    public PaymentsAService(RestTemplate restTemplate, @Value("${payments-b.url:http://localhost:8082}") String orchestrationUrl) {
        this.restTemplate = restTemplate;
        this.orchestrationUrl = orchestrationUrl;
    }

    public PaymentResponse forwardToOrchestration(PaymentRequest req) {
        try {
            ResponseEntity<PaymentResponse> resp = restTemplate.postForEntity(orchestrationUrl + "/orchestrate", req, PaymentResponse.class);
            return resp.getBody() != null ? resp.getBody() : new PaymentResponse(false, "Empty response from orchestration");
        } catch (Exception e) {
            return new PaymentResponse(false, "Orchestration call failed: " + e.getMessage());
        }
    }
}
