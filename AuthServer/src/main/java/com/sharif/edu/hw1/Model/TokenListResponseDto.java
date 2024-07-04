package com.sharif.edu.hw1.Model;

import com.sharif.edu.hw1.Model.Security.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenListResponseDto {

    private List<TokenDto> tokens;
    private int count;

}