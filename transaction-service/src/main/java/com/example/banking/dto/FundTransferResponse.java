package com.example.banking.dto;

import java.time.LocalDateTime;

public class FundTransferResponse {
    private Long transactionId;
    private String status;
    private String message;
    private LocalDateTime transactionDate;

    public FundTransferResponse() {
    } //default constructor


    public FundTransferResponse(Long transactionId, String status, String message, LocalDateTime transactionDate) {
        this.transactionId = transactionId;
        this.status = status;
        this.message = message;
        this.transactionDate = transactionDate;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}
