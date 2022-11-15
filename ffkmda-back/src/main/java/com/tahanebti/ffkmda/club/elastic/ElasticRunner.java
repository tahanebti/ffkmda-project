package com.tahanebti.ffkmda.club.elastic;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tahanebti.ffkmda.club.Club;
import com.tahanebti.ffkmda.club.ClubRepository;
import com.tahanebti.ffkmda.club.elastic.utils.ElasticConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElasticRunner {

	private final ClubRepository clubRepository;
	private final ClubElasticRepository clubElasticRepository;
	
//	 @Scheduled(cron = "0 */3 * * * *")
	 @Transactional
	    public void sync() {
	        log.info("Start Syncing - {}", LocalDateTime.now());
	        this.syncClubs();
	        log.info(" End Syncing - {}", LocalDateTime.now());
	    }


	   private void syncClubs() {

	        Specification<Club> clubSpecification = (root, criteriaQuery, criteriaBuilder) ->
	                getModificationDatePredicate(criteriaBuilder, root);
	        List<Club> clubList;
	        if (clubElasticRepository.count() == 0) {
	            clubList = clubRepository.findAll();
	        } else {
	            clubList = clubRepository.findAll(clubSpecification);
	        }
	        for(Club club: clubList) {
	            log.info("Syncing Club - {}", club.getId());
	            clubElasticRepository.save(map(club));
	        }
	    }
	   
	    private static Predicate getModificationDatePredicate(CriteriaBuilder cb, Root<?> root) {
	        Expression<Timestamp> currentTime;
	        currentTime = cb.currentTimestamp();
	        Expression<Timestamp> currentTimeMinus = cb.literal(new Timestamp(System.currentTimeMillis() -
	                (ElasticConstants.INTERVAL_IN_MILLISECONDE)));
	        return cb.between(root.<Date>get(ElasticConstants.MODIFICATION_DATE),
	                currentTimeMinus,
	                currentTime
	        );
	    }
	    


	private ClubModel map(Club club) {
		return ClubModel.builder()
				.code(club.getCode())
				.code_region(club.getCode_region())
				.etat(club.getEtat())
				.id(club.getId())
				.logo(club.getLogo())
				.logo_url(club.getLogo_url())
				.nom(club.getNom())
				.nom_court(club.getNom_court())
				.siege(SiegeModel.builder()
						.adresse_complete(club.getSiege().getAdresse_complete())
						.batiment(club.getSiege().getBatiment())
						.code_insee_departement(club.getSiege().getCode_insee_departement())
						.code_insee_region(club.getSiege().getCode_insee_region())
						.code_postal_et(club.getSiege().getCode_postal_et())
						.code_postal_fr(club.getSiege().getCode_postal_fr())
						.commune(club.getSiege().getCommune())
						.escalier(club.getSiege().getEscalier())
						.facebook(club.getSiege().getFacebook())
						.fax(club.getSiege().getFax())
						.fax_pro(club.getSiege().getFax_pro())
						.latitude(club.getSiege().getLatitude())
						.longitude(club.getSiege().getLongitude())
						.mail(club.getSiege().getMail())
						.mobile(club.getSiege().getMobile())
						.mobile2(club.getSiege().getMobile2())
						.mobile_pro(club.getSiege().getMobile_pro())
						.nom_voie(club.getSiege().getNom_voie())
						.num_voie(club.getSiege().getNum_voie())
						.pays_code(club.getSiege().getPays_code())
						.tel(club.getSiege().getTel())
						.tel2(club.getSiege().getTel2())
						.tel_pro(club.getSiege().getTel_pro())
						.twitter(club.getSiege().getTwitter())
						.type_voie(club.getSiege().getType_voie())
						.web(club.getSiege().getWeb())
						.build())
				
				.build();
	}
}