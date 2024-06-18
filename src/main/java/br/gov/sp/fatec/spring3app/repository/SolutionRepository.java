package br.gov.sp.fatec.spring3app.repository;

import br.gov.sp.fatec.spring3app.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolutionRepository extends JpaRepository<Solution, Long> {
}
