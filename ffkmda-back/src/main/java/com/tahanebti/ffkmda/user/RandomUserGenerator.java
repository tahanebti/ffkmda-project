package com.tahanebti.ffkmda.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.tahanebti.ffkmda.profile.UserProfile;
import com.tahanebti.ffkmda.role.Role;
import com.tahanebti.ffkmda.role.RoleName;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@Transactional
public class RandomUserGenerator implements CommandLineRunner {

	   private final UserRepository userRepository;
	   private final PasswordEncoder passwordEncoder;

    
    private static final List<User> USERS = Arrays.asList(
    		User.builder()    			
    			.username("admin")
    			.password("admin")
    			.email("admin@mycompany.com")
    			.enabled(true)
    			.roles(Collections.singleton(Role.builder()
    					.name(RoleName.ROLE_ADMIN)
    					.build())
    					)
    			.profile(UserProfile.builder() 	    			   
    		    			.firstName("Taha")
    		    			.lastName("Nebti")
    		    			//.phones(Collections.singletonList(Phone.builder().type(PhoneType.MOBILE).number("55426041").build()))
    					.build())
    			.build(),
    			
    			User.builder()    			
    			.username("user")
    			.password("user")
    			.email("user@mycompany.com")
    			.enabled(false)
    			.roles(Collections.singleton(Role.builder().name(RoleName.ROLE_USER).build()))
    			.profile(UserProfile.builder() 	    			   
		    			.firstName("Edson")
		    			.lastName("Ynaga")
		    			//.phones(Collections.singletonList(Phone.builder().type(PhoneType.MOBILE).number("+1555555555").build()))
					.build())
    			.build()

    );


	@Override
	public void run(String... args) throws Exception {
		   if (!userRepository.findAll().isEmpty()) {
	            return;
	        }
	        USERS.forEach(user -> {
	            user.setPassword(passwordEncoder.encode(user.getPassword()));
	            userRepository.save(user);
	        });
	        
	
	        
	        log.info("Database initialized");
	}

}

