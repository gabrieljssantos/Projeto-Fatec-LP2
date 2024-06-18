package br.gov.sp.fatec.spring3app.controller;

import br.gov.sp.fatec.spring3app.entity.TestCase;
import br.gov.sp.fatec.spring3app.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tc")
public class TestCaseController {
    @Autowired
    private TestCaseService testCaseService;

    @PostMapping
    public ResponseEntity<?> createTestCase(@RequestBody TestCase testCase) {
        TestCase savedTestCase = testCaseService.saveTestCase(testCase);
        return new ResponseEntity<>(savedTestCase, HttpStatus.CREATED);
    }

    @GetMapping("/{problemCode}")
    public ResponseEntity<List<TestCase>> getTestCasesByProblemCode(@PathVariable String problemCode) {
        List<TestCase> testCases = testCaseService.getTestCasesByProblemCode(problemCode);
        return new ResponseEntity<>(testCases, HttpStatus.OK);
    }
}
