package com.example.BankingApiApplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/create")
    public String createAccount(@RequestBody Account account) {
        accountRepository.save(account);
        return "Account created successfully.";
    }

    @PostMapping("/{accountId}/deposit")
    public String deposit(@PathVariable int accountId,@RequestBody deposit deposit) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + accountId));
        double currentBalance = account.getBalance();
        account.setBalance(currentBalance + deposit.getAmount());
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(deposit.getAmount());
        transaction.setDateTime(LocalDateTime.now());
        transaction.setAccount(account);

        account.getTransactions().add(transaction);

        accountRepository.save(account);
        accountRepository.save(account);
        return "Deposit of " + deposit.getAmount() + " successful. Current balance: " + account.getBalance();
    }

    @PostMapping("/{accountId}/withdraw")
    public String withdraw(@PathVariable int accountId, @RequestBody deposit depost) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + accountId));
        double currentBalance = account.getBalance();
        if (currentBalance < depost.getAmount()) {
            return "Insufficient funds. Withdrawal failed.";
        }
        account.setBalance(currentBalance - depost.getAmount());
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setAmount(depost.getAmount());
        transaction.setDateTime(LocalDateTime.now());
        transaction.setAccount(account);

        account.getTransactions().add(transaction);
        accountRepository.save(account);
        return "Withdrawal of " + depost.getAmount() + " successful. Current balance: " + account.getBalance();
    }

    @GetMapping("/{accountId}/balance")
    public double getBalance(@PathVariable int accountId) {
        return accountService.getBalance(accountId);
    }

}
