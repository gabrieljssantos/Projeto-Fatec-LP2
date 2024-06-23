package br.gov.sp.fatec.spring3app.service;

import br.gov.sp.fatec.spring3app.entity.Result;
import br.gov.sp.fatec.spring3app.entity.TestCase;
import br.gov.sp.fatec.spring3app.entity.Activity;
import br.gov.sp.fatec.spring3app.repository.ResultRepository;
import br.gov.sp.fatec.spring3app.repository.TestCaseRepository;
import br.gov.sp.fatec.spring3app.repository.ActivityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ResultService {

    @Autowired
    private ActivityRepository activityRepository;
    @Autowired
    private TestCaseRepository testCaseRepo;
    @Autowired
    private ResultRepository resultRepository;

    public List<Result> getAllResults() {
        return resultRepository.findAll();
    }

    public Optional<Result> getResultById(Long id) {
        return resultRepository.findById(id);
    }

    @Transactional
    public ResponseEntity<?> fileResult(String author, String activityCode, String filename, MultipartFile sourceCode) {
        Optional<Activity> activityOpt = activityRepository.findByactivityCode(activityCode);

        if (activityOpt.isEmpty()) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "[Activity] Error", "O campo 'ACTIVITY' NÃO foi encontrado!");
        }

        Activity activity = activityOpt.get();
        if (!filename.equals(activity.getFilename() + ".py")) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST, "[File] Error", "O campo FILENAME NÃO corresponde a um PROBLEM");
        }

        List<TestCase> testCases = testCaseRepo.findByActivityId(activity.getId());
        boolean allTestsPassed = executeTestCases(sourceCode, filename, testCases);

        String status = allTestsPassed ? "SUCCESS" : "FAIL";

        Result inputResult = saveResult(author, activityCode, filename, sourceCode, status, activity.getId());

        return buildSuccessResponse(inputResult);
    }

    private ResponseEntity<?> buildErrorResponse(HttpStatus status, String errorResponseCode, String errorResponseMsg) {
        return ResponseEntity.status(status).body(Map.of("errorResponseCode", errorResponseCode, "errorResponseMsg", errorResponseMsg));
    }

    private boolean executeTestCases(MultipartFile sourceCode, String filename, List<TestCase> testCases) {
        String files = "filesOutput";
        createDirectory(files);

        String pathOutput = saveSourceCodeToFile(files, filename, sourceCode);
        if (pathOutput == null) {
            return false;
        }

        for (TestCase testCase : testCases) {
            if (!runTestsResults(pathOutput, testCase)) {
                return false;
            }
        }
        return true;
    }

    private void createDirectory(String files) {
        File filesOutputDir = new File(files);
        if (!filesOutputDir.exists()) {
            filesOutputDir.mkdir();
        }
    }

    private String saveSourceCodeToFile(String files, String filename, MultipartFile sourceCode) {
        String pathOutput = files + "/" + filename;
        File filesOutput = new File(pathOutput);
        try {
            Files.write(filesOutput.toPath(), sourceCode.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return pathOutput;
    }

    private boolean runTestsResults(String pathOutput, TestCase testCase) {
        String inputFilePath = testCase.getInputFile();
        String outputFilePath = testCase.getOutputFile();
        String inputFileContents;
        String outputFileContents;

        try {
            inputFileContents = new String(Files.readAllBytes(Paths.get(inputFilePath)));
            outputFileContents = new String(Files.readAllBytes(Paths.get(outputFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            ProcessBuilder pb = new ProcessBuilder("python3", pathOutput);
            pb.redirectInput(new File(inputFilePath));
            pb.redirectOutput(new File("filesOutput/fileResult.txt"));
            Process processRunning = pb.start();
            processRunning.waitFor();

            String fileResult = new String(Files.readAllBytes(Paths.get("filesOutput/fileResult.txt"))).trim();
            return fileResult.equals(outputFileContents.trim());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Result saveResult(String author, String activityCode, String filename, MultipartFile sourceCode, String status, Long activityId) {
        Result inputResult = new Result();
        inputResult.setAuthor(author);
        inputResult.setFilename(filename);
        inputResult.setActivityCode(activityCode);
        inputResult.setSourceCode("filesOutput/" + filename);
        inputResult.setStatus(status);
        inputResult.setCreatedAt(LocalDateTime.now());
        /* inputResult.setResultId(resultId); */
        inputResult.setResultId(activityId);
        resultRepository.save(inputResult);
        return inputResult;
    }

    private ResponseEntity<Result> buildSuccessResponse(Result inputResult) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inputResult);
    }

    @Transactional
    public ResponseEntity<Void> removeResult(Long resultId) {
        if (!resultRepository.existsById(resultId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        resultRepository.deleteById(resultId);
        return ResponseEntity.ok().build();
    }
}
