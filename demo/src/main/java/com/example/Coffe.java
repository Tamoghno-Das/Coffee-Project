package com.example;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Coffe {
    private String coffeeName;
    private double price;
    private String coffeeType;
    private String origin;
}
