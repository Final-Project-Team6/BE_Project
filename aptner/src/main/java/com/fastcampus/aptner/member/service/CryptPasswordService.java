package com.fastcampus.aptner.member.service;

public interface CryptPasswordService {
    String encryptPassword(String password);

    boolean isPasswordMatch(String enterPassword, String storedPassword);
}
