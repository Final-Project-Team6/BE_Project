package com.fastcampus.aptner.member.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class CryptPasswordServiceImpl implements CryptPasswordService {

    PasswordEncoder passwordEncoder;

    @Override
    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Override
    public boolean isPasswordMatch(String enterPassword, String storedPassword) {
        return passwordEncoder.matches(enterPassword, storedPassword);
    }
}
