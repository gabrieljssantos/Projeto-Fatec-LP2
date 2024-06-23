package br.gov.sp.fatec.spring3app.entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class TestCase {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String inputFile;

  @Column(nullable = false)
  private String outputFile;

  @ManyToOne
  @JoinColumn(name = "activityId", nullable = false)
  private Activity activity;

  @Transient
  private String activityCode;

  public String getactivityCode() {
    return this.activity != null ? this.activity.getActivityCode() : null;
  }
}