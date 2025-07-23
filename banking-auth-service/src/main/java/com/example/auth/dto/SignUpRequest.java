package com.example.auth.dto;

import com.example.auth.model.Account;
import com.example.auth.model.User;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class SignUpRequest {

    private String username;
    private String password;

    private String accountHolderName;
    private String accountNumber;
    private String accountType;
    private String ifscCode;

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }

    public Account toAccount() {
        Account acc = new Account();
        acc.setAccountHolderName(accountHolderName);
        acc.setAccountNumber(accountNumber);
        acc.setIfscCode(ifscCode);
        //acc.setBalance(BigDecimal.ZERO); // Initialize with zero balance
        return acc;
    }
}