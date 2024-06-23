package br.gov.sp.fatec.spring3app.service;

import br.gov.sp.fatec.spring3app.entity.TestCase;
import br.gov.sp.fatec.spring3app.entity.Activity;
import br.gov.sp.fatec.spring3app.repository.TestCaseRepository;
import br.gov.sp.fatec.spring3app.repository.ActivityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class TestCaseService {

  @Autowired
  private TestCaseRepository testCaseRepo;
  @Autowired
  private ActivityRepository activityRepository;


  public List < TestCase > getAllTc() {
    return testCaseRepo.findAll();
  }

  public List < TestCase > getTcByCode(String activityCode) {
    Optional < Activity > activityOpt = activityRepository.findByactivityCode(activityCode);
    return activityOpt.map(activity -> testCaseRepo.findByActivityId(activity.getId())).orElse(null);
  }

  @Transactional
  public ResponseEntity < ? > uploadTestCase(Map < String, String > formData, MultipartFile inputFile, MultipartFile outputFile) {
    String activityCode = formData.get("activityCode");
    Optional < Activity > activityOpt = activityRepository.findByactivityCode(activityCode);
    if (activityOpt.isEmpty()) {
      return buildErrorResponse(
          HttpStatus.BAD_REQUEST, 
          "[Activity] Error", 
          "O campo 'ACTIVITY' não foi encontrado!"
        );
    }

    String inputFilePath = saveFile(inputFile);
    String outputFilePath = saveFile(outputFile);
    if (inputFilePath == null || outputFilePath == null) {
      return buildErrorResponse(
          HttpStatus.INTERNAL_SERVER_ERROR, 
          "[File] Error", 
          "Ocorreu um Erro ao Salvar o arquivo."
        );
    }

    TestCase testCase = createAndSaveTestCase(activityOpt.get(), inputFilePath, outputFilePath);

    return ResponseEntity.status(HttpStatus.CREATED).body(testCase);
  }

  private ResponseEntity < Map < String, String >> buildErrorResponse(HttpStatus status, String title, String message) {
    return ResponseEntity.status(status).body(Map.of(
        "errorResponseCode", 
        title, "errorResponseMsg", 
        message
      ));
  }

  private String saveFile(MultipartFile file) {
    try {
      File tempFile = File.createTempFile(
          "filesOutput", 
          ".tmp"
        );
      try (InputStream inStream = file.getInputStream()) {
        Files.copy(inStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
      }
      return tempFile.getAbsolutePath();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  private TestCase createAndSaveTestCase(Activity activity, String inputFilePath, String outputFilePath) {
    TestCase testCase = new TestCase();
      testCase.setInputFile(inputFilePath);
      testCase.setOutputFile(outputFilePath);
      testCase.setActivity(activity);
      testCaseRepo.save(testCase);
    return testCase;
  }

  @Transactional
  public ResponseEntity < Void > removeTestCase(Long tcId) {
    if (!testCaseRepo.existsById(tcId)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    testCaseRepo.deleteById(tcId);
    return ResponseEntity.ok().build();
  }
}