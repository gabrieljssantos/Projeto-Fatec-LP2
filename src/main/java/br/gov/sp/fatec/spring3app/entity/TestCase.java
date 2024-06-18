package br.gov.sp.fatec.spring3app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String problemCode;
    private String inputFile;
    private String expectedResult;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getProblemCode() {
        return problemCode;
    }
    public void setProblemCode(String problemCode) {
        this.problemCode = problemCode;
    }
    public String getInputFile() {
        return inputFile;
    }
    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }
    public String getExpectedResult() {
        return expectedResult;
    }
    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }


}
