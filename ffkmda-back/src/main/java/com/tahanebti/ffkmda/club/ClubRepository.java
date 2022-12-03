package com.tahanebti.ffkmda.club;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tahanebti.ffkmda.base.BaseRepository;

public interface ClubRepository extends BaseRepository<Club, Long>{

	Optional<Club> findByCode(String code);

	List<Club> findByEtat(String etat);

	List<Club> findByType(String type);
	
	@Query(value = "select * from clubs c inner join sieges s where s.code_postal_fr = :zip and s.commune = :commune" , nativeQuery = true)
    List<Club> findClubsBySiegeAddress(@Param("zip") String zip, @Param("commune") String commune);
	
	

}
