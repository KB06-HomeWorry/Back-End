package org.scoula.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.user.dto.ChangePasswordDTO;
import org.scoula.user.dto.UserDTO;
import org.scoula.user.dto.UserJoinDTO;
import org.scoula.user.dto.UserUpdateDTO;
import org.scoula.user.exception.PasswordMissmatchException;
import org.scoula.user.mapper.UserMapper;
import org.scoula.security.account.domain.AuthVO;
import org.scoula.security.account.domain.UserVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final PasswordEncoder passwordEncoder;
    final UserMapper mapper;

    @Override
    public boolean checkDuplicate(String username) {
        UserVO user = mapper.findByUsername(username);
        return user != null;
    }

    @Override
    public UserDTO get(String username) {
        UserVO member = Optional.ofNullable(mapper.get(username))
                .orElseThrow(NoSuchElementException::new);
        return UserDTO.of(member);
    }

    private void saveAvatar(MultipartFile avatar, String username) {
        //아바타 업로드
        if (avatar != null && !avatar.isEmpty()) {
            File dest = new File("c:/upload/avatar", username + ".png");
            try {
                avatar.transferTo(dest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Transactional
    @Override
    public UserDTO join(UserJoinDTO dto) {
        UserVO member = dto.toVO();

        member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호 암호화
        mapper.insertUser(member);

        AuthVO auth = new AuthVO();
        auth.setUserId(member.getUserId());
        auth.setAuth("ROLE_MEMBER");
        mapper.insertAuth(auth);

        saveAvatar(dto.getAvatar(), member.getUsername());

        return get(member.getUsername());
    }

    @Override
    public UserDTO update(UserUpdateDTO member) {
        //1. 패스워드가 맞지 않으면 update처리하지 않음.
        //내가 입력한 pw는 member에 들어있고.
        //db에 pw를 검색해서 가지고 와서 가지고 와야함.
        UserVO vo = mapper.get(member.getUsername());
        if (!passwordEncoder.matches(member.getPassword(), vo.getPassword())) {
            throw new PasswordMissmatchException();
        }
        //2. mybatis에 update()처리 요청
        mapper.update(member.toVO());

        //3. 아바타 저장
        saveAvatar(member.getAvatar(), member.getUsername());

        //4. 리턴은 검색해서 리턴
        return get(member.getUsername());
    }


    @Override
    public void changePassword(ChangePasswordDTO changePassword) {
        UserVO member = mapper.get(changePassword.getUsername());

        if (!passwordEncoder.matches(changePassword.getOldPassword(), member.getPassword())) {
            throw new PasswordMissmatchException();
        }

        changePassword.setNewPassword(passwordEncoder.encode(changePassword.getNewPassword()));

        mapper.updatePassword(changePassword);
    }

}

















