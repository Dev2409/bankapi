package com.example.BankingApiApplication;

import jakarta.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Account createAccount(int accountId, String accountHolderName, long mobileNumber, double initialBalance) {
        Account account = new Account(accountId, accountHolderName, mobileNumber, initialBalance);
        return accountRepository.save(account);
    }

    @Transactional
    public Account deposit(int accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found!"));
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    @Transactional
    public Account withdraw(int accountId, double amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found!"));
        double balance = account.getBalance();
        if (balance < amount) {
            throw new IllegalArgumentException("Insufficient funds!");
        }
        account.setBalance(balance - amount);
        return accountRepository.save(account);
    }

    public double getBalance(int accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new IllegalArgumentException("Account not found!"));
        return account.getBalance();
    }

    @Transactional
    public void transfer(int senderAccountId, int recipientAccountId, double amount) {
        Account senderAccount = accountRepository.findById(senderAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Sender account not found!"));
        Account recipientAccount = accountRepository.findById(recipientAccountId)
                .orElseThrow(() -> new IllegalArgumentException("Recipient account not found!"));

        if (senderAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds in sender account!");
        }

        senderAccount.setBalance(senderAccount.getBalance() - amount);
        recipientAccount.setBalance(recipientAccount.getBalance() + amount);

        accountRepository.save(senderAccount);
        accountRepository.save(recipientAccount);
    }
//    public List<Transaction> getAllTransactions(int accountId) {
//        Account account = accountRepository.findById(accountId)
//                .orElseThrow(() -> new IllegalArgumentException("Account not found with id: " + accountId));
//
//        return account.getTransactions();
//    }
}
