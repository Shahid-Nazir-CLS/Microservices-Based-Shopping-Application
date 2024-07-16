package com.example.payment.restcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @GetMapping("/")
    public String getHome() {
        return "welcome to payment";
    }

}
