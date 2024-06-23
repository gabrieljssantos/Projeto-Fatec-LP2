package br.gov.sp.fatec.spring3app.entity;

import lombok.Data;
import jakarta.persistence.*;

@Entity
@Data
public class Activity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String activityCode;

  @Column(nullable = false, unique = true)
  private String filename;

  @Column(nullable = false)
  private String lps;
}