package com.example.BankingApiApplication;

import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
public class Account {
    @Getter
    @Setter
    @Id
    private int accountId;
    @Setter
    @Getter
    private String accountHolderName;
    @Setter
    @Getter
    private long mobileNumber;
    private double balance;

    public Account() {
        // Default constructor required by JPA
    }

    public Account(int accountId, String accountHolderName, long mobileNumber, double balance) {
        this.accountId = accountId;
        this.accountHolderName = accountHolderName;
        this.mobileNumber = mobileNumber;
        this.balance = balance;
    }

    public double getInitialBalance() {
        return balance;
    }

    public void setInitialBalance(double balance) {
        this.balance = balance;
    }
}

    // Getters and setters if not provided by Lombok

