package com.digitalbanking.authservice.validator;


import org.springframework.stereotype.Component;

import com.digitalbanking.authservice.exception.AuthErrorCode;
import com.digitalbanking.authservice.exception.AuthException;


@Component
public class PasswordValidator {

    public void validate(String password) {
        if (password == null || password.length() < 8 || password.length() > 30) {
            throw new AuthException(AuthErrorCode.INVALID_PASSWORD_FORMAT);
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialCharacter = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else {
                hasSpecialCharacter = true;
            }
        }

        if (!hasUppercase || !hasLowercase || !hasDigit || !hasSpecialCharacter) {
            throw new AuthException(AuthErrorCode.INVALID_PASSWORD_FORMAT);
        }
    }
}
