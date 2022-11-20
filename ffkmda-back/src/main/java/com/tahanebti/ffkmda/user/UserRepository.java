package com.tahanebti.ffkmda.user;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.tahanebti.ffkmda.base.BaseRepository;

public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
