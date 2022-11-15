package com.tahanebti.ffkmda.phone;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhoneResponse {

	private Long id;
	private String tel;
	private String tel2;
	private String tel_pro;
	private String mobile;
	private String mobile2;
	private String mobile_pro;
	private String fax;
	private String fax_pro;

}
