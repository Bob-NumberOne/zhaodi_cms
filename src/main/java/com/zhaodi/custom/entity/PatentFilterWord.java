package com.zhaodi.custom.entity;

/**
 * @author WangBo
 * @version 1.0
 * @Describe 描述
 * @date 2020/5/6 13:35
 */
public class PatentFilterWord {
    private Integer id;
    private String word;
    private String nature;
    private String english;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
