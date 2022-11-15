package com.tahanebti.ffkmda.user;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tahanebti.ffkmda.base.BaseRepository;
import com.tahanebti.ffkmda.base.BaseServiceImpl;
import com.tahanebti.ffkmda.exception.DataNotFoundException;
import com.tahanebti.ffkmda.specification.SpecificationsBuilder;


@Service 
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {

	private final UserRepository userRepository ;
    private final PasswordEncoder passwordEncoder;

	public UserServiceImpl(BaseRepository<User, Long> baseRepository, SpecificationsBuilder<User> spec,
			UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super(baseRepository, spec);
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;

	}
	

	    @Override
	    public Optional<User> getUserByUsername(String username) {
	        return userRepository.findByUsername(username);
	    }

	    @Override
	    public boolean hasUserWithUsername(String username) {
	        return userRepository.existsByUsername(username);
	    }

	    @Override
	    public boolean hasUserWithEmail(String email) {
	        return userRepository.existsByEmail(email);
	    }

	    @Override
	    public User validateAndGetUserByUsername(String username) {
	        return getUserByUsername(username)
	                .orElseThrow(() -> new DataNotFoundException(String.format("User with username %s not found", username)));
	    }

	

	    @Override
	    public Optional<User> validUsernameAndPassword(String username, String password) {
	        return getUserByUsername(username)
	                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
	    }

}
