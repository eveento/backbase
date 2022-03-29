package com.backbase.recruitment.rest.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AcademyAwardDTO {

    @NotEmpty @NotBlank
    private String year;
    @Size(max = 255)
    @NotEmpty
    @NotBlank
    private String category;
    @NotBlank @NotEmpty
    private String nominee;
    private String description;
    @JsonProperty("is_win")
    private boolean isWin;
}
