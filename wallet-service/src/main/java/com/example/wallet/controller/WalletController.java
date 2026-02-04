package com.example.wallet.controller;

import com.example.wallet.model.PaymentMethod;
import com.example.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    @PostMapping("/addPaymentMethod")
    public ResponseEntity<PaymentMethod> addPaymentMethod(@RequestBody PaymentMethod method) {
        return ResponseEntity.ok(walletService.addPaymentMethod(method));
    }

    @DeleteMapping("/deletePaymentMethod/{accountId}")
    public ResponseEntity<?> deletePaymentMethod(@PathVariable String accountId) {
        boolean deleted = walletService.deletePaymentMethod(accountId);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("/updatePaymentMethod/{accountId}")
    public ResponseEntity<PaymentMethod> updatePaymentMethod(@PathVariable String accountId,
            @RequestBody PaymentMethod method) {
        PaymentMethod updated = walletService.updatePaymentMethod(accountId, method);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getPaymentMethod/{accountId}")
    public ResponseEntity<PaymentMethod> getPaymentMethod(@PathVariable String accountId) {
        PaymentMethod method = walletService.getPaymentMethod(accountId);
        return method != null ? ResponseEntity.ok(method) : ResponseEntity.notFound().build();
    }

    @GetMapping("/getAllPaymentMethods")
    public ResponseEntity<List<PaymentMethod>> getAllPaymentMethods() {
        return ResponseEntity.ok(walletService.getAllPaymentMethods());
    }
}
