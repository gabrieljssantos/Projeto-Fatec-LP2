package br.gov.sp.fatec.spring3app.service;

import br.gov.sp.fatec.spring3app.entity.Problem;
import br.gov.sp.fatec.spring3app.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {
    @Autowired
    private ProblemRepository problemRepository;

    public Problem saveProblem(Problem problem) {
        if (problemRepository.existsByProblemCode(problem.getProblemCode())) {
            throw new IllegalArgumentException("Problem code already exists.");
        }
        return problemRepository.save(problem);
    }

    public Iterable<Problem> getAllProblems() {
        return problemRepository.findAll();
    }
}
