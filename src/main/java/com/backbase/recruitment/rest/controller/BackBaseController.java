package com.backbase.recruitment.rest.controller;

import com.backbase.recruitment.rest.dtos.AcademyAwardDTO;
import com.backbase.recruitment.rest.dtos.OmdbDTO;
import com.backbase.recruitment.service.BackBaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Optional;

@Validated
@Log4j2
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BackBaseController {

    private final BackBaseService backBaseService;

    @GetMapping("best-picture")
    public ResponseEntity<AcademyAwardDTO> winnerBP(@NotBlank @NotNull @NotEmpty @RequestParam("title") String title) {
        log.info(MessageFormat.format("Getting winner for BestPicture based on {0}", title));
        Optional<AcademyAwardDTO> winnerBP = backBaseService.getWinnerBP(title);
        return winnerBP.map(response -> ResponseEntity.ok().body(response))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, MessageFormat.format("Cannot find {0} in database", title)));
    }

    @GetMapping("top-ten")
    public ResponseEntity<Collection<OmdbDTO>> getTopTenBasedOnBoxOfficeValue() {
        log.info("Getting top 10 movies");
        Collection<OmdbDTO> topTenRatedMovies = backBaseService.getTopTenRatedMovies();
        return ResponseEntity.ok(topTenRatedMovies);
    }
}
