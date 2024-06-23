package br.gov.sp.fatec.spring3app.repository;

import org.springframework.stereotype.Repository;
import br.gov.sp.fatec.spring3app.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface TestCaseRepository extends JpaRepository < TestCase, Long > {

  List < TestCase > findByActivityId(Long activityId);
  void deleteById(Long id);
}