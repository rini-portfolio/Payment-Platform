package com.example.paymentsa.service;

import com.example.paymentsa.model.PaymentMethod;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Arrays;

@Service
public class PaymentMethodService {
    private final RestTemplate restTemplate;
    private final String orchestrationUrl;

    public PaymentMethodService(RestTemplate restTemplate,
            @Value("${payments-b.url:http://localhost:8082}") String orchestrationUrl) {
        this.restTemplate = restTemplate;
        this.orchestrationUrl = orchestrationUrl;
    }

    public PaymentMethod addPaymentMethod(PaymentMethod method) {
        return restTemplate.postForObject(orchestrationUrl + "/payment-method/addPaymentMethod", method,
                PaymentMethod.class);
    }

    public boolean deletePaymentMethod(String accountId) {
        restTemplate.delete(orchestrationUrl + "/payment-method/deletePaymentMethod/" + accountId);
        return true;
    }

    public PaymentMethod updatePaymentMethod(String accountId, PaymentMethod method) {
        restTemplate.put(orchestrationUrl + "/payment-method/updatePaymentMethod/" + accountId, method);
        return method;
    }

    public PaymentMethod getPaymentMethod(String accountId) {
        return restTemplate.getForObject(orchestrationUrl + "/payment-method/getPaymentMethod/" + accountId,
                PaymentMethod.class);
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        PaymentMethod[] methods = restTemplate.getForObject(orchestrationUrl + "/payment-method/getAllPaymentMethods",
                PaymentMethod[].class);
        return methods != null ? Arrays.asList(methods) : List.of();
    }
}
