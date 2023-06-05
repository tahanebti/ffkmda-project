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
	
	@Query(value = "SELECT * FROM clubs c INNER JOIN sieges s ON c.siege_id = s.id " +
            "WHERE s.code_postal_fr = :zip AND s.commune = :commune " +
            "ORDER BY :sortBy :sortDirection",
    countQuery = "SELECT count(*) FROM clubs c INNER JOIN sieges s ON c.siege_id = s.id " +
                 "WHERE s.code_postal_fr = :zip AND s.commune = :commune",
    nativeQuery = true)
List<Club> findClubsBySiegeAddress(@Param("zip") String zip,
                                @Param("commune") String commune,
                                @Param("sortBy") String sortBy,
                                @Param("sortDirection") String sortDirection,
                                Pageable pageable);


	
    @Query("SELECT c FROM Club c INNER JOIN FETCH c.siege s WHERE c.type = :type AND c.code_departement = :codeDepartement AND s.code_insee_departement = :codeInseeDepartement")
    List<Club> findClubsByTypeAndCodeDepartement(@Param("type") String type, @Param("codeDepartement") String codeDepartement, @Param("codeInseeDepartement") String codeInseeDepartement);


}
