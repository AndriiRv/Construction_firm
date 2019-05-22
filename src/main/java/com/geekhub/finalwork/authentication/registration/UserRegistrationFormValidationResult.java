package com.geekhub.finalwork.authentication.registration;

import java.util.ArrayList;
import java.util.List;

class UserRegistrationFormValidationResult {
    private final List<String> errors = new ArrayList<>();

    void addError(String error) {
        errors.add(error);
    }

    List<String> getErrors() {
        return errors;
    }

    boolean hasErrors() {
        return !errors.isEmpty();
    }
}