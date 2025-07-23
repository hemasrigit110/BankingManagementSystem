package com.example.banking.dto;

import java.math.BigDecimal;

public class FundTransferRequest {

    private String fromAccount;
    private String toAccount;
    private BigDecimal amount;
    private String ifscCode;

    // private String description;

    // Constructors
    public FundTransferRequest() {
    }

    public FundTransferRequest(String fromAccount, String toAccount, BigDecimal amount, String ifscCode) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.ifscCode = ifscCode;
        // this.description = description;
    }

    // Getters and Setters
    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    // public String getDescription() {
    // return description;
    // }
    //
    // public void setDescription(String description) {
    // this.description = description;
    // }
}
