package org.scoula.user.service;

import org.scoula.user.dto.ChangePasswordDTO;
import org.scoula.user.dto.UserDTO;
import org.scoula.user.dto.UserJoinDTO;
import org.scoula.user.dto.UserUpdateDTO;

public interface UserService {

    boolean checkDuplicate(String username);

    UserDTO get(String username);

    UserDTO join(UserJoinDTO member);

    UserDTO update(UserUpdateDTO member);

    void changePassword(ChangePasswordDTO changePassword);
}
