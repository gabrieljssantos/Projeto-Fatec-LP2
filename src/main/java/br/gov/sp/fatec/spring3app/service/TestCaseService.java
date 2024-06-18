package br.gov.sp.fatec.spring3app.service;

import br.gov.sp.fatec.spring3app.entity.TestCase;
import br.gov.sp.fatec.spring3app.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseService {
    @Autowired
    private TestCaseRepository testCaseRepository;

    public TestCase saveTestCase(TestCase testCase) {
        return testCaseRepository.save(testCase);
    }

    public List<TestCase> getTestCasesByProblemCode(String problemCode) {
        return testCaseRepository.findByProblemCode(problemCode);
    }
}
