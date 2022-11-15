package com.tahanebti.ffkmda.siege;

import org.springframework.stereotype.Service;

import com.tahanebti.ffkmda.base.BaseRepository;
import com.tahanebti.ffkmda.base.BaseServiceImpl;
import com.tahanebti.ffkmda.specification.SpecificationsBuilder;

@Service
public class SiegeServiceImpl extends BaseServiceImpl<Siege, Long> implements SiegeService{

	public SiegeServiceImpl(BaseRepository<Siege, Long> baseRepository, SpecificationsBuilder<Siege> spec) {
		super(baseRepository, spec);
		// TODO Auto-generated constructor stub
	}

}
