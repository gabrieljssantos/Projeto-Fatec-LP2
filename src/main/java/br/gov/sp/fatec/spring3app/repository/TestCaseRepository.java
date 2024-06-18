package br.gov.sp.fatec.spring3app.repository;

import br.gov.sp.fatec.spring3app.entity.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    List<TestCase> findByProblemCode(String problemCode);
}
