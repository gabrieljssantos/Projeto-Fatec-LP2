package br.gov.sp.fatec.spring3app.entity;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String activityCode;

  @Column(nullable = false)
  private String sourceCode;

  @Column(nullable = false)
  private Long resultId;

  @Column(nullable = false)
  private String author;

  @Column(nullable = false)
  private String filename;

  @Column(nullable = false)
  private String status;

  @Column(nullable = false)
  private LocalDateTime createdAt;


  public Result(String activityCode, String sourceCode, Long resultId, String author, String filename, String status, LocalDateTime createdAt) {
    this.activityCode = activityCode;
    this.sourceCode = sourceCode;
    this.resultId = resultId;
    this.author = author;
    this.filename = filename;
    this.status = status;
    this.createdAt = createdAt;
  }


}
