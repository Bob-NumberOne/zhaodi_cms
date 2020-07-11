package com.zhaodi.custom.entity;

import java.util.List;

/**
 * @version 1.0
 * @Describe 查询词云的实体
 * @Author WangBo
 * @Date 2020/5/5 18:05
 */
public class SearchWords {
    private String flag;//是否默认使用分词库：1代表使用；0代表不用
    private String wordColumn;//数据列====》逗号隔开
    private String wordPart;//词性====》逗号隔开
    private String selfWords;//自定义语句
    private String number;//分词量
    private String filterWords;//过滤分词====》逗号隔开
    private String belongToBlock;//分块

    private Integer type;
    private String companyName;
    private Integer top;
    private String year;
    private String yearRange;
    private String startYear;
    private String endYear;
    private List<String> company;

    public List<String> getCompany() {
        return company;
    }

    public void setCompany(List<String> company) {
        this.company = company;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getTop() {
        return top;
    }

    public void setTop(Integer top) {
        this.top = top;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYearRange() {
        return yearRange;
    }

    public void setYearRange(String yearRange) {
        this.yearRange = yearRange;
    }

    public String getBelongToBlock() {
        return belongToBlock;
    }

    public void setBelongToBlock(String belongToBlock) {
        this.belongToBlock = belongToBlock;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getWordColumn() {
        return wordColumn;
    }

    public void setWordColumn(String wordColumn) {
        this.wordColumn = wordColumn;
    }

    public String getWordPart() {
        return wordPart;
    }

    public void setWordPart(String wordPart) {
        this.wordPart = wordPart;
    }

    public String getSelfWords() {
        return selfWords;
    }

    public void setSelfWords(String selfWords) {
        this.selfWords = selfWords;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFilterWords() {
        return filterWords;
    }

    public void setFilterWords(String filterWords) {
        this.filterWords = filterWords;
    }
}
