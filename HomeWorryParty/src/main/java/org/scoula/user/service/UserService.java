package org.scoula.user.service;

import org.scoula.user.dto.*;

public interface UserService {

    boolean checkDuplicate(String username);

    boolean checkNameAndEmail(String username, String email);

    boolean existsByEmail(String email);

    boolean existsByToken(String token);

    String passwordVerify(String password, String username);

    PasswordResetTokenDTO savePasswordResetToken(PasswordResetTokenDTO passwordResetToken);

    UserDTO get(String username);

    PasswordResetTokenDTO getemail(String email);

    PasswordResetTokenDTO gettoken(String token);

    UserDTO getUserByEmail(String email);

    UserDTO join(UserJoinDTO member);

    boolean withdraw(String username);

    UserDTO update(UserUpdateDTO member);

    void PasswordRewrite(UserDTO member);

    void deleteToken(String email);

    void changePassword(ChangePasswordDTO changePassword);
}
