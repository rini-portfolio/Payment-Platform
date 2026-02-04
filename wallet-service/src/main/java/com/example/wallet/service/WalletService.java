package com.example.wallet.service;

import com.example.wallet.model.PaymentMethod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class WalletService {
    private final Map<String, PaymentMethod> paymentMethods = new HashMap<>();

    public PaymentMethod addPaymentMethod(PaymentMethod method) {
        paymentMethods.put(method.getAccountId(), method);
        return method;
    }

    public boolean deletePaymentMethod(String accountId) {
        return paymentMethods.remove(accountId) != null;
    }

    public PaymentMethod updatePaymentMethod(String accountId, PaymentMethod method) {
        if (!paymentMethods.containsKey(accountId))
            return null;
        paymentMethods.put(accountId, method);
        return method;
    }

    public PaymentMethod getPaymentMethod(String accountId) {
        return paymentMethods.get(accountId);
    }

    public List<PaymentMethod> getAllPaymentMethods() {
        return new ArrayList<>(paymentMethods.values());
    }
}
