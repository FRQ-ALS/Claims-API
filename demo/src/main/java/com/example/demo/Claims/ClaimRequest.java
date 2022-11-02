package com.example.demo.Claims;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClaimRequest {

    private String vin;
    private long insuranceNo;
}
