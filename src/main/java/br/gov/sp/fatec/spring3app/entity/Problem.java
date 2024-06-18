package br.gov.sp.fatec.spring3app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;
    private String problemCode;
    private String lps;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getLps() {
        return lps;
    }
    public void setLps(String lps) {
        this.lps = lps;
    }


}
