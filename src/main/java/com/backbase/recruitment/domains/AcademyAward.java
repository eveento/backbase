package com.backbase.recruitment.domains;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AcademyAward implements Serializable {

    @Serial
    private static final long serialVersionUID = 2694136047909273689L;

    @Setter(AccessLevel.NONE)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    @SequenceGenerator(name = "seq_generator", sequenceName = "my_seq", allocationSize = 100)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Size(min = 4, max = 25)
    @NotBlank
    @NotEmpty
    @Column(name = "year", nullable = false)
    private String year;

    @Size(max = 255)
    @NotEmpty
    @NotBlank
    @Column(name = "category", nullable = false)
    private String category;

    @NotBlank
    @NotEmpty
    @Column(name = "nominee", nullable = false)
    private String nominee;

    @Size(max = 255)
    private String description;

    @Builder.Default
    @Column(name = "is_win", nullable = false)
    private boolean isWin = false;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AcademyAward academyAward)) return false;
        return getId().equals(academyAward.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId()) * PrimeConstants.ACADEMY_AWARD;
    }
}
