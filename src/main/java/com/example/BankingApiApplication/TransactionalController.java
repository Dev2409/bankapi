package com.example.BankingApiApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionalController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/{accountId}/transfer")
    public String transfer(@PathVariable int accountId,
                           @RequestParam int recipientAccountId,
                           @RequestParam double amount) {
        accountService.transfer(accountId, recipientAccountId, amount);
        return "Transfer successful!";
    }

}
