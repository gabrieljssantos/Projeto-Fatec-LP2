package br.gov.sp.fatec.spring3app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import br.gov.sp.fatec.spring3app.entity.Result;
import br.gov.sp.fatec.spring3app.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.util.Optional;
import java.util.List;


@RestController
@RequestMapping("/activity/solution")
public class ResultController {

  @Autowired
  private ResultService resultService;

  @GetMapping
  public ResponseEntity < List < Result >> getAllResults() {
    List < Result > results = resultService.getAllResults();
    return ResponseEntity.ok(results);
  }

  @GetMapping("/{id}")
  public ResponseEntity < Result > getResultById(@PathVariable Long id) {
    Optional < Result > result = resultService.getResultById(id);
    return result.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }

  @PostMapping(consumes = "multipart/form-data")
  public ResponseEntity < ? > fileResult(@RequestParam("author") String author,
    @RequestParam("activityCode") String activityCode,
    @RequestParam("filename") String filename,
    @RequestParam("sourceCode") MultipartFile sourceCode) {
    return resultService.fileResult(author, activityCode, filename, sourceCode);
  }

  @DeleteMapping("/{resultId}")
  public ResponseEntity < Void > removeResult(@PathVariable Long resultId) {
    return resultService.removeResult(resultId);
  }
}