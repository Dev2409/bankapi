package com.example.BankingApiApplication;

import lombok.Getter;
import lombok.Setter;

public class deposit {
    @Setter
    @Getter
    private double amount;

    public deposit(double amount) {
        this.amount = amount;
    }
    public deposit(){

    }
}
