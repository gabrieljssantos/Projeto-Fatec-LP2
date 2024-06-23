package br.gov.sp.fatec.spring3app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.gov.sp.fatec.spring3app.entity.Activity;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    Optional<Activity> findByactivityCode(String activityCode);
    Optional<Activity> findByFilename(String filename);
    Optional<Activity> findByLps(String lps);
    void deleteByactivityCode(String activityCode);
}
