package com.sharif.edu.hw1.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllCountriesRequest {
    @JsonProperty("error")
    private String error;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("data")
    private List<CountryRequest> data;
}
