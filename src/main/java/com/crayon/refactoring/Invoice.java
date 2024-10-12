package com.crayon.refactoring;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Invoice {
    public String customer;
    public List<Performance> performances;

    @JsonCreator
    public Invoice(@JsonProperty("customer") String customer, @JsonProperty("performances") List<Performance> performances) {
        this.customer = customer;
        this.performances = performances;
    }
}