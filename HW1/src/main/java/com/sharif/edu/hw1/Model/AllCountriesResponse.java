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
public class AllCountriesResponse {
    @JsonProperty("countries")
    private List<CountryResponse> countries;
    @JsonProperty("count")
    private int count;
}
