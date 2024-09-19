package com.namequickly.logistics.auth.domain.repository;

import com.namequickly.logistics.auth.domain.model.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.slackId = :slackId")
    boolean checkUserExistsbySlackId(@Param("slackId") String slackId);

    boolean existsByCompanyAffiliationId(UUID affiliationId);
    boolean existsByCourierAffiliationId(UUID affiliationId);
    boolean existsByHubAffiliationId(UUID affiliationId);

}
