package com.geekhub.finalwork.authentication.registration;

import com.geekhub.finalwork.authentication.user.User;
import com.geekhub.finalwork.authentication.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationFormValidator {
    private final UserService userService;

    @Autowired
    public UserRegistrationFormValidator(UserService userService) {
        this.userService = userService;
    }

    UserRegistrationFormValidationResult validate(UserRegistrationForm userRegistrationForm) {
        UserRegistrationFormValidationResult userRegistrationFormValidationResult = new UserRegistrationFormValidationResult();
        validatePassword(userRegistrationForm, userRegistrationFormValidationResult);
        validateUsername(userRegistrationForm, userRegistrationFormValidationResult);
        return userRegistrationFormValidationResult;
    }

    private void validatePassword(UserRegistrationForm userRegistrationForm, UserRegistrationFormValidationResult userRegistrationFormValidationResult) {
        String password = userRegistrationForm.getPassword();
        assert password != null;
        if (!password.equals(userRegistrationForm.getPasswordConfirmation())) {
            userRegistrationFormValidationResult.addError("Password must match password confirmation");
        }
    }

    private void validateUsername(UserRegistrationForm userRegistrationForm, UserRegistrationFormValidationResult userRegistrationFormValidationResult) {
        String username = userRegistrationForm.getUsername();
        User getUsernameFromDatabase = userService.getUserByUsername(username);
        if (getUsernameFromDatabase != null) {
            userRegistrationFormValidationResult.addError("User name is already in use");
        }
    }
}