package com.geekhub.finalwork.authentication.registration;

import com.geekhub.finalwork.authentication.user.User;
import com.geekhub.finalwork.authentication.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRegistrationService {
    private final UserRegistrationFormValidator userRegistrationFormValidator;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegistrationService(UserRegistrationFormValidator userRegistrationFormValidator,
                                   UserService userService,
                                   PasswordEncoder passwordEncoder) {
        this.userRegistrationFormValidator = userRegistrationFormValidator;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserRegistrationResult register(UserRegistrationForm userRegistrationForm) {
        UserRegistrationFormValidationResult userRegistrationFormValidationResult = userRegistrationFormValidator.validate(userRegistrationForm);
        if (!userRegistrationFormValidationResult.hasErrors()) {
            userService.saveUser(insertDataToUser(userRegistrationForm));
            return UserRegistrationResult.ok();
        } else {
            return UserRegistrationResult.fail(userRegistrationFormValidationResult.getErrors());
        }
    }

    public User insertDataToUser(UserRegistrationForm userRegistrationForm) {
        User user = new User();
        user.setUsername(userRegistrationForm.getUsername());
        user.setFirstName(userRegistrationForm.getFirstName());
        user.setLastName(userRegistrationForm.getLastName());
        user.setPassword(passwordEncoder.encode(userRegistrationForm.getPassword()));
        user.setTitle(userRegistrationForm.getTitle());
        user.setAddress(userRegistrationForm.getAddress());
        user.setEdrpouCode(userRegistrationForm.getEdrpouCode());
        user.setTelephone(userRegistrationForm.getTelephone());
        user.setEmail(userRegistrationForm.getEmail());
        return user;
    }
}