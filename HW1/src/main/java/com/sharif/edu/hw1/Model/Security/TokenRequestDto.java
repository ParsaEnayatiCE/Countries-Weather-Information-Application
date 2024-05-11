package com.sharif.edu.hw1.Model.Security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequestDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("expire_date")
    private LocalDateTime expireDate;
}