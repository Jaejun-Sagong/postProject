package com.sparta.postproject.domain;

import lombok.Getter;

@Getter
public class MemoRequestDto {
    private String title;
    private String username;
    private String contents;
    private String pw;
}