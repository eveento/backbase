package com.backbase.recruitment.repository;

import com.backbase.recruitment.domain.AcademyAward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface AcademyAwardRepository extends JpaRepository<AcademyAward, Long> {

    Optional<AcademyAward> findFirstByNomineeAndCategoryAndWin(String title, String category, boolean win);

    Collection<AcademyAward> findAllByCategoryAndWin(String category, boolean win);
}