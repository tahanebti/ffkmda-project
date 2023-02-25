package com.tahanebti.ffkmda.club;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.tahanebti.ffkmda.base.BaseService;
import com.tahanebti.ffkmda.role.Role;

public interface ClubService extends BaseService<Club, Long>{

	public Club validateAndGetByCode(String code);

	public List<Club> validateAndGetByEtat(String etat);

	public List<Club> validateAndGetByType(String type);

	public List<Club> findClubsBySiegeAddress(String commune, String code_postal_fr, String nom_voie, String type_voie, String code_insee_departement,
	        String sortBy, String sortDirection
	        );

	public List<Club> findClubsBySiegePhone(String tel, String mobile, String fax);

	public Page<Club> searchByAddress(String commune, String code_postal_fr, String nom_voie, String type_voie,
			String code_insee_departement,String sortBy, String sortDirection, Pageable page);


    public Page<Club> searchClubs(
            String city,
            String commune,
            String code_postal_fr,
            String nom_voie,
            String type_voie,
            String code_insee_departement,
            int page,
            int size,
            String sortBy,
            String sortDirection
    );
}
