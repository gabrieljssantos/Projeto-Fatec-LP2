package br.gov.sp.fatec.spring3app.controller;

import br.gov.sp.fatec.spring3app.entity.Problem;
import br.gov.sp.fatec.spring3app.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/activity")
public class ProblemController {
    @Autowired
    private ProblemService problemService;

    @PostMapping
    public ResponseEntity<?> createProblem(@RequestBody Problem problem) {
        try {
            Problem savedProblem = problemService.saveProblem(problem);
            return new ResponseEntity<>(savedProblem, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Problem>> getAllProblems() {
        return new ResponseEntity<>(problemService.getAllProblems(), HttpStatus.OK);
    }
}
