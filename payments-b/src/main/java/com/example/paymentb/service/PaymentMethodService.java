package com.example.paymentb.service;

import com.example.paymentb.model.PaymentMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Arrays;

@Service
public class PaymentMethodService {
    private final RestTemplate restTemplate;
    private final String walletServiceUrl;

    public PaymentMethodService(RestTemplate restTemplate,
            @Value("${wallet-service.url:http://localhost:8083}") String walletServiceUrl) {
        this.restTemplate = restTemplate;
        this.walletServiceUrl = walletServiceUrl;
    }

    public PaymentMethod addPaymentMethod(PaymentMethod method) {
        return restTemplate.postForObject(walletServiceUrl + "/wallet/addPaymentMethod", method, PaymentMethod.class);
    }

    public boolean deletePaymentMethod(String accountId) {
        restTemplate.delete(walletServiceUrl + "/wallet/deletePaymentMethod/" + accountId);
        return true;
    }

    public PaymentMethod updatePaymentMethod(String accountId, PaymentMethod method) {
        restTemplate.put(walletServiceUrl + "/wallet/updatePaymentMethod/" + accountId, method);
        return method;
    }

    public PaymentMethod getPaymentMethod(String accountId) {
        return restTemplate.getForObject(walletServiceUrl + "/wallet/getPaymentMethod/" + accountId,
                PaymentMethod.class);
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        PaymentMethod[] methods = restTemplate.getForObject(walletServiceUrl + "/wallet/getAllPaymentMethods",
                PaymentMethod[].class);
        return methods != null ? Arrays.asList(methods) : List.of();
    }
}
