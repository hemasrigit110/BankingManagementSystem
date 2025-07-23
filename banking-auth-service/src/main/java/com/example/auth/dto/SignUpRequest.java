// package com.example.auth.dto;

// import com.example.auth.model.Account;
// import com.example.auth.model.User;
// import lombok.Data;
// import java.math.BigDecimal;

// @Data
// public class SignUpRequest {

//     private String username;
//     private String password;

//     private String accountHolderName;
//     private String accountNumber;
//     private String accountType;
//     private String ifscCode;

//     public User toUser() {
//         User user = new User();
//         user.setUsername(username);
//         user.setPassword(password);
//         return user;
//     }

//     public Account toAccount() {
//         Account acc = new Account();
//         acc.setAccountHolderName(accountHolderName);
//         acc.setAccountNumber(accountNumber);
//         acc.setIfscCode(ifscCode);
//         //acc.setBalance(BigDecimal.ZERO); // Initialize with zero balance
//         return acc;
//     }
// }

package com.example.auth.dto;

import com.example.auth.model.Account;
import com.example.auth.model.User;

public class SignUpRequest {

    private String username;
    private String password;
    private String accountHolderName;
    private String accountNumber;
    private String accountType;
    private String ifscCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

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
        return acc;
    }
}
