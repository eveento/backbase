package com.backbase.recruitment.client;

import com.backbase.recruitment.rest.dtos.OmdbDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Optional;

@Log4j2
@RequiredArgsConstructor
@Component
public class OmdbClient {

    @Value("${spring.app.client.omdb.key}")
    private String apiKey;
    @Value("${spring.app.client.omdb.url:http://www.omdbapi.com}")
    private String url;

    private final RestTemplate restTemplate;

    public Optional<OmdbDTO> exchangeSync(String title) {
        log.debug(MessageFormat.format("Get sync data for title {0}", title));
        URI uri = createUri(title);
        return Optional.ofNullable(
                restTemplate.exchange(uri, HttpMethod.GET, null, OmdbDTO.class)
                        .getBody()
        );
    }

    private URI createUri(String title) {
        return UriComponentsBuilder
                .fromUriString(url)
                .queryParam("apikey", apiKey)
                .queryParam("t", title)
                .build().toUri();
    }
}
