package br.gov.sp.fatec.spring3app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.gov.sp.fatec.spring3app.entity.Result;
import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository < Result, Long > {

  void deleteByResultId(Long resultId);

  List < Result > findByactivityCode(String activityCode);
}