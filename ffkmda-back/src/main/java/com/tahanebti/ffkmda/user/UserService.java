package com.tahanebti.ffkmda.user;

import java.util.List;
import java.util.Optional;

import com.tahanebti.ffkmda.base.BaseService;

public interface UserService extends BaseService<User, Long> {


    Optional<User> getUserByUsername(String username);

    boolean hasUserWithUsername(String username);

    boolean hasUserWithEmail(String email);

    User validateAndGetUserByUsername(String username);

    Optional<User> validUsernameAndPassword(String username, String password);

}
