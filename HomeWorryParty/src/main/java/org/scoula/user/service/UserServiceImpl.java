package org.scoula.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.user.domain.PasswordResetTokenVO;
import org.scoula.user.dto.*;
import org.scoula.user.mapper.UserMapper;
import org.scoula.security.account.domain.UserVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final PasswordEncoder passwordEncoder;
    final UserMapper mapper;

    @Override
    public boolean checkNameAndEmail(String username, String email) {
        UserVO user = mapper.findByemail(email);
        return user.getUsername().equals(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        UserVO user = mapper.findByemail(email);
        return user != null;
    }

    @Override
    public boolean existsByToken(String token) {
        PasswordResetTokenVO prvo = mapper.findByToken(token);
        return prvo != null;
    }

    @Override
    public void savePasswordResetToken(PasswordResetTokenDTO passwordResetToken) {
        PasswordResetTokenVO passwordResetTokenVO = passwordResetToken.toVO();
        PasswordResetTokenVO dto = mapper.getemail(passwordResetTokenVO.getEmail());

        if (dto != null) {
            mapper.updatePRT(passwordResetTokenVO);
        } else {
            mapper.insertPRT(passwordResetTokenVO);
        }
    }

    @Override
    public UserDTO get(String username) {
        UserVO member = Optional.ofNullable(mapper.get(username))
                .orElseThrow(NoSuchElementException::new);
        return UserDTO.of(member);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        UserVO member = Optional.ofNullable(mapper.findByemail(email))
                .orElseThrow(NoSuchElementException::new);
        return UserDTO.of(member);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        UserVO member = Optional.ofNullable(mapper.findById(userId))
                .orElseThrow(NoSuchElementException::new);
        return UserDTO.of(member);
    }

    @Override
    public PasswordResetTokenDTO getemail(String email) {
        PasswordResetTokenVO prt = Optional.ofNullable(mapper.getemail(email))
                .orElseThrow(NoSuchElementException::new);
        return PasswordResetTokenDTO.of(prt);
    }

    @Override
    public PasswordResetTokenDTO gettoken(String token) {
        PasswordResetTokenVO prt = Optional.ofNullable(mapper.findByToken(token))
                .orElseThrow(NoSuchElementException::new);
        return PasswordResetTokenDTO.of(prt);
    }

    @Transactional
    @Override
    public UserDTO join(UserJoinDTO dto) {
        UserVO member = dto.toVO();

        member.setPassword(passwordEncoder.encode(member.getPassword())); // 비밀번호 암호화
        mapper.insertUser(member);

        return getUserByEmail(member.getEmail());
    }

    @Override
    public boolean withdraw(Long userId) {
        return mapper.withdraw(userId) == 1;
    }

    @Override
    public void PasswordRewrite(UserDTO member) {
        mapper.passwordRewrite(member.toVO());
    }

    @Override
    public void deleteToken(String email) {
        mapper.deleteToken(email);
    }

}