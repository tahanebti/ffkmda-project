package com.tahanebti.ffkmda.siege;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class CreateSiegeRequest {
	
	//social
	private String web;
	private String twitter;
	private String facebook;
	
	private String batiment;
	private String escalier;	
	private String mail;
}
