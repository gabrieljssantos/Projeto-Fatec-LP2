package br.gov.sp.fatec.spring3app.controller;

import br.gov.sp.fatec.spring3app.entity.Solution;
import br.gov.sp.fatec.spring3app.service.SolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity/solution")
public class SolutionController {
    @Autowired
    private SolutionService solutionService;

    @PostMapping
    public ResponseEntity<?> createSolution(@RequestBody Solution solution) {
        Solution savedSolution = solutionService.saveSolution(solution);
        return new ResponseEntity<>(savedSolution, HttpStatus.CREATED);
    }
}
