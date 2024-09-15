package com.namequickly.logistics.auth.domain.repository;

import com.namequickly.logistics.auth.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// TODO : 구글 자바 코드 스타일 적용 안됨?
public interface UserRepository extends JpaRepository<User, String> {

}
