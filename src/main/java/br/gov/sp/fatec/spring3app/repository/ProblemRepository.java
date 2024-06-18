package br.gov.sp.fatec.spring3app.repository;

import br.gov.sp.fatec.spring3app.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemRepository extends JpaRepository<Problem, Long> {
    boolean existsByProblemCode(String problemCode);
}
