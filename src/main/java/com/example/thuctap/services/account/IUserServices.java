package com.example.thuctap.services.account;

import com.example.thuctap.models.User;

public interface IUserServices {

    User findByUserName(String userName);
    User findByEmail(String email);
    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
    void saveOrUpdate(User user);
//    void createPasswordResetTokenForUser(User user, String token);
//    P
//    String validatePasswordResetToken(String token);
//    boolean isTokenFound(PasswordResetToken passwordResetToken);
//    boolean isTokenExpired(PasswordResetToken passwordResetToken);
    void updatePassword(User user, String newPassword);
    void updateResetPasswordToken(String token, String email);
    User getPasswordResetToken(String token);
}
