//package com.example.account.controller;
//
//import com.example.account.dto.UpdateBalanceRequest;
//import com.example.account.model.Account;
//import com.example.account.service.AccountServiceImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.math.BigDecimal;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@WebMvcTest(AccountController.class)
//public class AccountControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private AccountServiceImpl accountService;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    void testGetAccountDetails_Found() throws Exception {
//        Account account = new Account();
//        account.setAccountNumber("ACC123");
//        account.setAccountHolderName("Suresh");
//        account.setBalance(new BigDecimal("5000.00"));
//
//        when(accountService.getAccountDetails("ACC123")).thenReturn(account);
//
//        mockMvc.perform(get("/account/accountNumber/ACC123"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.accountNumber").value("ACC123"))
//                .andExpect(jsonPath("$.balance").value(5000.00))
//                .andExpect(jsonPath("$.accountHolderName").value("Suresh"));
//    }
//
//    @Test
//    void testGetAccountDetails_NotFound() throws Exception {
//        when(accountService.getAccountDetails("ACC999")).thenReturn(null);
//
//        mockMvc.perform(get("/account/accountNumber/ACC999"))
//                .andExpect(status().isOk())
//                .andExpect(content().string("No account found with account number: ACC999"));
//    }
//
//    @Test
//    void testUpdateBalance_Credit() throws Exception {
//        UpdateBalanceRequest request = new UpdateBalanceRequest();
//        request.setAccountNumber("ACC123");
//        request.setAmount(new BigDecimal("1000.00"));
//        request.setTransactionType("CREDIT");
//
//        doNothing().when(accountService).updateBalance(anyString(), any(BigDecimal.class), anyString());
//
//        mockMvc.perform(post("/account/update-balance")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Balance updated successfully"));
//    }
//
//    @Test
//    void testUpdateBalance_InsufficientFunds() throws Exception {
//        UpdateBalanceRequest request = new UpdateBalanceRequest();
//        request.setAccountNumber("ACC123");
//        request.setAmount(new BigDecimal("10000.00"));
//        request.setTransactionType("DEBIT");
//
//        doThrow(new RuntimeException("Insufficient balance"))
//                .when(accountService)
//                .updateBalance(anyString(), any(BigDecimal.class), anyString());
//
//        mockMvc.perform(post("/account/update-balance")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Insufficient balance"));
//    }
//}
