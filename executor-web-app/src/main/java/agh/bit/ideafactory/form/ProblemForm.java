package agh.bit.ideafactory.form;


import agh.bit.ideafactory.model.helpers.LanguageEnum;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProblemForm {

    private String problemName;
    private Set<LanguageEnum> languageSet = new HashSet<LanguageEnum>();
    private MultipartFile problemFile;
    private List<MultipartFile> testSet = new ArrayList<MultipartFile>();
    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public Set<LanguageEnum> getLanguageSet() {
        return languageSet;
    }

    public void setLanguageSet(Set<LanguageEnum> languageSet) {
        this.languageSet = languageSet;
    }

    public MultipartFile getProblemFile() {
        return problemFile;
    }

    public void setProblemFile(MultipartFile problemFile) {
        this.problemFile = problemFile;
    }

    public List<MultipartFile> getTestSet() {
        return testSet;
    }

    public void setTestSet(List<MultipartFile> testSet) {
        this.testSet = testSet;
    }
}
