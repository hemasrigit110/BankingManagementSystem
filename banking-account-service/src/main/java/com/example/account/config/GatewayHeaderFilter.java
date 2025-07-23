//package com.example.account.config;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import java.io.IOException;
//
//@Component
//public class GatewayHeaderFilter implements Filter {
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpReq = (HttpServletRequest) request;
//        String gatewayHeader = httpReq.getHeader("X-Gateway-Auth");
//        if (!"true".equals(gatewayHeader)) {
//            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Direct access not allowed");
//            return;
//        }
//        chain.doFilter(request, response);
//    }
//}