package com.tahanebti.ffkmda.authentication;

import java.time.Instant;

import com.tahanebti.ffkmda.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {


	private String accessToken;


}
