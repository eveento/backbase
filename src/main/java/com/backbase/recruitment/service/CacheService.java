package com.backbase.recruitment.service;

import com.backbase.recruitment.client.OmdbClient;
import com.backbase.recruitment.domain.AcademyAward;
import com.backbase.recruitment.rest.dtos.OmdbDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Collection;

import static com.backbase.recruitment.config.CacheConfiguration.KEYS_BP_WINNER;

@Log4j2
@RequiredArgsConstructor
@Component
public class CacheService {

    private final OmdbClient client;

    @Cacheable(value = KEYS_BP_WINNER, keyGenerator = "moveMemcacheKeyGenerator")
    public Collection<OmdbDTO> getWinnerBestPicture(Collection<AcademyAward> movies) {
        ArrayList<OmdbDTO> omdbDTOS = new ArrayList<>();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        log.info("Started");
        movies.forEach(movie -> omdbDTOS
                .add(client.exchangeSync(movie.getNominee())
                        .orElse(new OmdbDTO())));
        stopWatch.stop();
        log.info("Finished. time[ms] " + stopWatch.getTotalTimeMillis());
        return omdbDTOS;
    }
}
