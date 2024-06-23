package br.gov.sp.fatec.spring3app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import br.gov.sp.fatec.spring3app.entity.TestCase;
import br.gov.sp.fatec.spring3app.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Map;
import java.util.List;


@RestController
@RequestMapping("/tc")
public class TestCaseController {

  @Autowired
  private TestCaseService testCaseService;

  @GetMapping
  public ResponseEntity < List < TestCase >> getAllTc() {
    List < TestCase > testCaseList = testCaseService.getAllTc();
    return ResponseEntity.ok(testCaseList);
  }

  @GetMapping("/{activityCode}")
  public ResponseEntity < List < TestCase >> getTcByCode(@PathVariable String activityCode) {
    List < TestCase > testCases = testCaseService.getTcByCode(activityCode);
    return testCases != null ? ResponseEntity.ok(testCases) : ResponseEntity.notFound().build();
  }

  @PostMapping
  public ResponseEntity < ? > uploadTestCase(@RequestParam Map < String, String > formData,
    @RequestParam("inputFile") MultipartFile inputFile,
    @RequestParam("outputFile") MultipartFile outputFile) {
    return testCaseService.uploadTestCase(formData, inputFile, outputFile);
  }

  @DeleteMapping("/{tcId}")
  public ResponseEntity < Void > removeTestCase(@PathVariable Long tcId) {
    return testCaseService.removeTestCase(tcId);
  }
}