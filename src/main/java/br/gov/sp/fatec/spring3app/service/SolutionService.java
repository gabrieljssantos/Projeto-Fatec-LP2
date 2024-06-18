package br.gov.sp.fatec.spring3app.service;

import br.gov.sp.fatec.spring3app.entity.Solution;
import br.gov.sp.fatec.spring3app.repository.SolutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SolutionService {
    @Autowired
    private SolutionRepository solutionRepository;

    public Solution saveSolution(Solution solution) {
        solution.setExecutionDate(LocalDateTime.now());
        // Aqui você pode adicionar lógica para executar o código fonte e definir o status
        solution.setStatus("Executado"); // Exemplo de status
        return solutionRepository.save(solution);
    }
}
