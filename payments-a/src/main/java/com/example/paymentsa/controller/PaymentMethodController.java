package com.example.paymentsa.controller;

import com.example.paymentsa.model.PaymentMethod;
import com.example.paymentsa.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment-method")
public class PaymentMethodController {
    @Autowired
    private PaymentMethodService paymentMethodService;

    @PostMapping("/addPaymentMethod")
    public ResponseEntity<PaymentMethod> addPaymentMethod(@RequestBody PaymentMethod method) {
        return ResponseEntity.ok(paymentMethodService.addPaymentMethod(method));
    }

    @DeleteMapping("/deletePaymentMethod/{accountId}")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable String accountId) {
        boolean deleted = paymentMethodService.deletePaymentMethod(accountId);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/updatePaymentMethod/{accountId}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(@PathVariable String accountId,
            @RequestBody PaymentMethod method) {
        PaymentMethod updated = paymentMethodService.updatePaymentMethod(accountId, method);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getPaymentMethod/{accountId}")
    public ResponseEntity<PaymentMethod> getPaymentMethod(@PathVariable String accountId) {
        PaymentMethod method = paymentMethodService.getPaymentMethod(accountId);
        return method != null ? ResponseEntity.ok(method) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllPaymentMethods")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods() {
        return ResponseEntity.ok(paymentMethodService.getAllPaymentMethods());
    }
}
