package com.example.paymentsa.model;

import java.math.BigDecimal;

public class PaymentMethod {
    private String accountId;
    private String type;
    private String details;
    private BigDecimal balance;

    public PaymentMethod() {
    }

    public PaymentMethod(String accountId, String type, String details, BigDecimal balance) {
        this.accountId = accountId;
        this.type = type;
        this.details = details;
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
