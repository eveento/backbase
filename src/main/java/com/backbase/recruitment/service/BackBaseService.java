package com.backbase.recruitment.service;

import com.backbase.recruitment.domain.AcademyAward;
import com.backbase.recruitment.domain.Rating;
import com.backbase.recruitment.repository.AcademyAwardRepository;
import com.backbase.recruitment.rest.dtos.AcademyAwardDTO;
import com.backbase.recruitment.rest.dtos.OmdbDTO;
import com.backbase.recruitment.service.mapper.AcademyAwardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Log4j2
@Transactional
@Service
public class BackBaseService {

    private static final String BEST_PICTURE = "Best Picture";

    private final AcademyAwardRepository awardRepository;
    private final AcademyAwardMapper academyAwardMapper;
    private final CacheService cacheService;

    public Optional<AcademyAwardDTO> getWinnerBP(String title) {
        return awardRepository
                .findFirstByNomineeAndCategoryAndWin(title, BEST_PICTURE, true)
                .map(academyAwardMapper::toDto);
    }

    public Collection<OmdbDTO> getTopTenRatedMovies() {
        Collection<OmdbDTO> winnerBestPicture = cacheService.getWinnerBestPicture(awardRepository.findAllByCategoryAndWin(BEST_PICTURE, true));
        return winnerBestPicture.stream()
                .filter(movie -> Objects.nonNull(movie.getBoxOffice()))
                .sorted(Comparator.comparing(OmdbDTO::getBoxOffice))
                .limit(10)
                .sorted(Comparator.comparing(OmdbDTO::getBoxOffice).reversed())
                .toList();
    }

    public Optional<AcademyAwardDTO> rate(String title, double rate) {
        Optional<AcademyAward> firstByNominee = awardRepository.findFirstByNominee(title);
        if (firstByNominee.isPresent()) {
            firstByNominee.get().getRates()
                    .add(Rating.builder().rate(rate).academyAward(firstByNominee.get()).build());
            AcademyAwardDTO academyAwardDTO = academyAwardMapper.toDto(firstByNominee.get());
            return Optional.of(academyAwardDTO);
        } else {
            return Optional.empty();
        }
    }

    public OptionalDouble getRationByTitle(String title) {
        Optional<AcademyAward> firstByNominee = awardRepository.findFirstByNominee(title);
        if (firstByNominee.isPresent()) {
            Set<Rating> rates = firstByNominee.get().getRates();
            return rates.stream().mapToDouble(Rating::getRate).average();
        }
        return OptionalDouble.empty();
    }

}
