package com.example.account.model;


import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Column(name ="id")
    private Long id;

    private String accountHolderName;
    // @Id
    @Column(unique = true)
    private String accountNumber;
    private String ifscCode;
    private String accountType;
    private BigDecimal balance;
    // private String username; // username from auth-service
//    @Column(name = "user_id")
//    private Long userId;

    public Account() {
    }

    public Account(Long id, String accountHolderName, String accountNumber, String ifscCode, String accountType, BigDecimal balance){//} Long userId) {//,String username) {
        this.id = id;
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.accountType = accountType;
        this.balance = balance;
        //this.userId = userId; // Assuming userId is same as account id for simplicity
        //this.username=username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


}



























