package com.backbase.recruitment.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Rating implements Serializable {

    @Serial
    private static final long serialVersionUID = 2602190965731586988L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    @SequenceGenerator(name = "seq_generator", sequenceName = "my_seq", allocationSize = 1)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @PositiveOrZero
    private Double rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academy_award_id")
    private AcademyAward academyAward;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating rating)) return false;
        return getId().equals(rating.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId()) * PrimeConstants.RATING;
    }

}
