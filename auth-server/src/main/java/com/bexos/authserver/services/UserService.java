package com.bexos.authserver.services;

import com.bexos.authserver.dto.FormChangePasswordDto;
import com.bexos.authserver.dto.FormRegisterDto;

import java.util.Map;

public interface UserService {

    void form_register(FormRegisterDto user);

    Map<String, String> confirmEmail(String confirmationToken);

    Map<String, String> changePassword(FormChangePasswordDto passwordField, String userId);

    boolean isValidPassword(String password);
}
