package com.agloval.infrastructure.output.persistence;

import com.agloval.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    java.util.Optional<User> findByEmail(String email);
}
