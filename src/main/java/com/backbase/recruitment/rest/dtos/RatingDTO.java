package com.backbase.recruitment.rest.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatingDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -6168274140550968711L;
    @JsonProperty("Source")
    private String source;
    @JsonProperty("Value")
    private String value;
}
