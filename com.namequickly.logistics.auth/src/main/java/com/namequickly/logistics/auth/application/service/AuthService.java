package com.namequickly.logistics.auth.application.service;

import com.namequickly.logistics.auth.application.dto.UserInfoResponseDto;
import com.namequickly.logistics.auth.application.dto.UserSignupRequestDto;
import com.namequickly.logistics.auth.application.dto.UserSignupResponseDto;
import com.namequickly.logistics.auth.application.mapper.UserMapper;
import com.namequickly.logistics.auth.domain.model.User;
import com.namequickly.logistics.auth.domain.repository.UserRepository;
import com.namequickly.logistics.auth.infrastructure.configuration.security.UserDetailsServiceImpl;
import com.namequickly.logistics.common.exception.GlobalException;
import com.namequickly.logistics.common.response.ResultCase;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "AuthService")
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserDetailsServiceImpl userDetailsService;

    /**
     * 회원가입
     */
    // TODO : @Valid와 같은 요청 유효성 검증은 컨트롤러 단에서 하면 좋을 것 같습니다! 서비스 레벨에서는 비즈니스 로직에 집중하는 것이 좋을 것 같아요.
    // TODO : 기존에 unique 인 필드가 이미 있는지 검증하는 로직이 필요해보여요!
    @Transactional
    public UserSignupResponseDto signup(@Valid UserSignupRequestDto request) {
        log.info("Signup request: {}", request);

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.password());

        // 유저 entity 생성 (유저의 이름, 패스워드를 전달 받아 회원가입을 진행)
        User user = User.create(
            request.username(),
            encodedPassword,
            request.role()
        );

        // 회원가입 완료
        userRepository.save(user);

        return new UserSignupResponseDto(user.getUsername());
    }

    /**
     * 회원정보 단일검색
     */
    public UserInfoResponseDto getUserByUsername(String username) {
        log.info("Get user by username: {}", username);

        User user = userRepository.findById(username)
            .orElseThrow(() -> new GlobalException(ResultCase.USER_NOT_FOUND));
        return userMapper.userToUserInfoResponseDto(user);
    }

    /**
     * 회원정보 전체검색
     */
    // TODO : 컨트롤러 단에서 Pageable 을 받아서 바로 처리할 수 있습니다!
    // import org.springframework.data.domain.Pageable; 입니다!
    public List<UserInfoResponseDto> getAllUsers(int page, int size, String sortBy) {
        log.info("Get all users");

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).descending());
        return userRepository.findAll(pageRequest).stream()
            .map(userMapper::userToUserInfoResponseDto)
            .toList();
    }

}