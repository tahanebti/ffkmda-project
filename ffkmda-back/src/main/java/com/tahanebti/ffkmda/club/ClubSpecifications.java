package com.tahanebti.ffkmda.club;

import org.springframework.data.jpa.domain.Specification;

import com.tahanebti.ffkmda.siege.Siege;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public class ClubSpecifications implements Specification<Club> {

    private final Map<String, String> searchCriteria;

    public ClubSpecifications(Map<String, String> searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Club> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate predicate = criteriaBuilder.conjunction();
     

        for (Map.Entry<String, String> entry : searchCriteria.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (value == null || value.trim().isEmpty()) {
                continue;
            }

            switch (key) {
                case "commune":
                    predicate = criteriaBuilder.and(predicate, hasCityName(value).toPredicate(root, query, criteriaBuilder));
                    break;
                case "code_postal_fr":
                    predicate = criteriaBuilder.and(predicate, hasPostalCode(value).toPredicate(root, query, criteriaBuilder));
                    break;
                case "nom_voie":
                    predicate = criteriaBuilder.and(predicate, hasStreetName(value).toPredicate(root, query, criteriaBuilder));
                    break;
                case "type_voie":
                    predicate = criteriaBuilder.and(predicate, hasStreetType(value).toPredicate(root, query, criteriaBuilder));
                    break;
                case "code_insee_departement":
                    predicate = criteriaBuilder.and(predicate, hasDepartmentInseeCode(value).toPredicate(root, query, criteriaBuilder));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid search criteria: " + key);
            }
        }

        return predicate;
    }

    public static Specification<Club> hasCityName(String cityName) {
        
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.join("siege", JoinType.LEFT).get("commune")), "%" + cityName.toLowerCase() + "%");
    }

    public static Specification<Club> hasPostalCode(String postalCode) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("siege", JoinType.LEFT).get("code_postal_fr"), postalCode);
    }

    public static Specification<Club> hasStreetName(String streetName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.join("siege", JoinType.LEFT).get("nom_voie")), "%" + streetName.toLowerCase() + "%");
    }

    public static Specification<Club> hasStreetType(String streetType) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.join("siege", JoinType.LEFT).get("type_voie")), "%" + streetType.toLowerCase() + "%");
    }

    public static Specification<Club> hasDepartmentInseeCode(String departmentInseeCode) {
        return (root, query, criteriaBuilder) ->
        criteriaBuilder.like(criteriaBuilder.lower(root.join("siege", JoinType.LEFT).get("code_insee_departement")), departmentInseeCode + "%");
    }
}
