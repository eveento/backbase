package com.backbase.recruitment.service;

import com.backbase.recruitment.repository.AcademyAwardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Log4j2
@RequiredArgsConstructor
@Component
public class SchedulerService {

    private final AcademyAwardRepository awardRepository;
    private final CacheService cacheService;

    @Scheduled(cron = "0 0 18 * * ?")
    public void updateMemcache() {
        log.debug("CRON:: getting winners from database");
        var bestPictureWinners = awardRepository.findAllByCategoryAndWin("Best Picture", true);
        log.debug("CRON:: retrieving and caching data");
        cacheService.getWinnerBestPicture(bestPictureWinners);

    }

}
