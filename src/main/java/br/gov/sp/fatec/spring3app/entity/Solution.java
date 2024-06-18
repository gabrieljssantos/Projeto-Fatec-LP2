package br.gov.sp.fatec.spring3app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String autor;
    private String filename;
    private String problemCode;
    private String sourceCode;
    private String status;
    private LocalDateTime executionDate;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getProblemCode() {
        return problemCode;
    }
    public void setProblemCode(String problemCode) {
        this.problemCode = problemCode;
    }
    public String getSourceCode() {
        return sourceCode;
    }
    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocalDateTime getExecutionDate() {
        return executionDate;
    }
    public void setExecutionDate(LocalDateTime executionDate) {
        this.executionDate = executionDate;
    }
    
}
