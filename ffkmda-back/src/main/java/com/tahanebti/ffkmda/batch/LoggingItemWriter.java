package com.tahanebti.ffkmda.batch;

import java.util.List;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.tahanebti.ffkmda.club.Club;
import com.tahanebti.ffkmda.club.ClubRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggingItemWriter implements ItemWriter<Club> {

	 @Autowired ClubRepository clubRepository;
	
	@Override
	public void write(List<? extends Club> items) throws Exception {
        log.info("Writing clubs: {}", items);
        
        clubRepository.saveAll(items);

	}
}
