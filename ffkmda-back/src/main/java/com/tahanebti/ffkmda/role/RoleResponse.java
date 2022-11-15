package com.tahanebti.ffkmda.role;

import java.sql.Date;
import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleResponse  {

	private Long id;
	private String name;
	private Date createdAt;
	private Date updatedAt;
	
}
