package br.gov.sp.fatec.spring3app.service;

import br.gov.sp.fatec.spring3app.entity.Result;
import br.gov.sp.fatec.spring3app.entity.TestCase;
import br.gov.sp.fatec.spring3app.entity.Activity;
import br.gov.sp.fatec.spring3app.repository.ResultRepository;
import br.gov.sp.fatec.spring3app.repository.TestCaseRepository;
import br.gov.sp.fatec.spring3app.repository.ActivityRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private ActivityRepository activityRepositorysitory;
    @Autowired
    private TestCaseRepository testCaseRepository;
    @Autowired
    private ResultRepository testCaseRepositorysitory;

    public static class ActivityInfo {
        @NotBlank(message = "É necessário implementar o campo 'Filename'")
        @Size(max = 255, message = "O campo 'Filename' não pode exceder 255 caracteres")
        @JsonProperty("filename")
        private String filename;

        @NotBlank(message = "É necessário implementar o campo 'Activity code'")
        @Pattern(regexp = "[A-Za-z0-9]+", message = "O campo 'Activity code' deve conter apenas letras e números")
        @Size(max = 255, message = "O campo 'Activity code' não pode exceder 255 caracteres")
        @JsonProperty("activityCode")
        private String activityCode;

        @NotBlank(message = "É necessário implementar o campo 'LPS'")
        @Pattern(regexp = "[A-Za-z0-9]+", message = "O campo 'LPs' deve conter apenas letras e números")
        @Size(max = 255, message = "O campo 'LPS' não pode exceder 255 caracteres")
        @JsonProperty("lps")
        private String lps;

        // Getters and Setters
        public String getFilename() {
            return filename;
        }

        public void setFilename(String filename) {
            this.filename = filename;
        }

        public String getActivityCode() {
            return activityCode;
        }

        public void setActivityCode(String activityCode) {
            this.activityCode = activityCode;
        }

        public String getLps() {
            return lps;
        }

        public void setLps(String lps) {
            this.lps = lps;
        }
    }

    public static class ErrorInfo {
        private String errorResponseCode;
        private String errorResponseMsg;

        public ErrorInfo(String errorResponseCode, String errorResponseMsg) {
            this.errorResponseCode = errorResponseCode;
            this.errorResponseMsg = errorResponseMsg;
        }

        // Getters and Setters
        public String getErrorCode() {
            return errorResponseCode;
        }

        public void setErrorCode(String errorResponseCode) {
            this.errorResponseCode = errorResponseCode;
        }

        public String getErrorMsg() {
            return errorResponseMsg;
        }

        public void setErrorMsg(String errorResponseMsg) {
            this.errorResponseMsg = errorResponseMsg;
        }
    }

    public List<ActivityInfo> getAllActivitys() {
        return activityRepositorysitory.findAll().stream()
                .map(this::convertToActivityInfo)
                .collect(Collectors.toList());
    }

    public Optional<ActivityInfo> getActivityByActivityCode(String activityCode) {
        return activityRepositorysitory.findByactivityCode(activityCode)
                .map(this::convertToActivityInfo);
    }

    @Transactional
    public ResponseEntity<?> newActivity(@Valid ActivityInfo activityInfo) {
        if (activityRepositorysitory.findByactivityCode(activityInfo.getActivityCode()).isPresent()) {
            return buildErrorResponse(
              "[Activity] Error", 
              "O campo 'ACTIVITY' Já Existe!"
              );
        }

        if (activityRepositorysitory.findByFilename(activityInfo.getFilename()).isPresent()) {
            return buildErrorResponse(
              "[File] Error", 
              "O campo 'FILENAME' Já Existe!"
              );
        }

        Activity activity = new Activity();
        activity.setFilename(activityInfo.getFilename());
        activity.setActivityCode(activityInfo.getActivityCode());
        activity.setLps(activityInfo.getLps());

        activityRepositorysitory.save(activity);
        return ResponseEntity.status
          (201).body(activityInfo);
    }

    @Transactional
    public ResponseEntity<Void> removeActivity(String activityCode) {
        Optional<Activity> activityOptional = activityRepositorysitory.findByactivityCode(activityCode);
        if (activityOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Activity activity = activityOptional.get();
        deleteAssociatedResults(activity.getActivityCode());
        deleteAssociatedTestCases(activity.getId());

        activityRepositorysitory.delete(activity);
        return ResponseEntity.ok().build();
    }

    private ActivityInfo convertToActivityInfo(Activity activity) {
        ActivityInfo info = new ActivityInfo();
        info.setFilename(activity.getFilename());
        info.setActivityCode(activity.getActivityCode());
        info.setLps(activity.getLps());
        return info;
    }

    private ResponseEntity<ErrorInfo> buildErrorResponse(String title, String message) {
        logger.error(
          "Erro: {} - {}", 
          title, message);
        return ResponseEntity.badRequest().body(new ErrorInfo(title, message));
    }

    private void deleteAssociatedResults(String activityCode) {
        List<Result> results = testCaseRepositorysitory.findByactivityCode(activityCode);
        for (Result result : results) {
            testCaseRepositorysitory.delete(result);
        }
    }

    private void deleteAssociatedTestCases(Long activityId) {
        List<TestCase> testCases = testCaseRepository.findByActivityId(activityId);
        for (TestCase testCase : testCases) {
            testCaseRepository.delete(testCase);
        }
    }
}
