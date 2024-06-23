package br.gov.sp.fatec.spring3app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import br.gov.sp.fatec.spring3app.service.ActivityService;
import br.gov.sp.fatec.spring3app.service.ActivityService.ActivityInfo;
import java.util.List;
import java.util.Optional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/activity")
public class ActivityController {

  @Autowired
  private ActivityService activityService;

  @GetMapping
  public ResponseEntity < List < ActivityInfo >> getAllActivitys() {
    List < ActivityInfo > activitys = activityService.getAllActivitys();
    return ResponseEntity.ok(activitys);
  }

  @GetMapping("/{activityCode}")
  public ResponseEntity < ActivityInfo > getActivityByActivityCode(@PathVariable String activityCode) {
    Optional < ActivityInfo > activity = activityService.getActivityByActivityCode(activityCode);
    return activity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity < ? > newActivity(@Valid @RequestBody ActivityInfo activityInfo) {
    return activityService.newActivity(activityInfo);
  }

  @DeleteMapping("/{activityCode}")
  public ResponseEntity < Void > removeActivity(@PathVariable String activityCode) {
    return activityService.removeActivity(activityCode);
  }
}