package com.gamesys.roulette.core;

/**
 * @author Alexander Nesterov
 * @version 1.0
 */
public class ConfigValues {

    private String fileName;
    private String maxBetSum;

    public ConfigValues(String fileName, String maxBetSum) {
        this.fileName = fileName;
        this.maxBetSum = maxBetSum;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getMaxBetSum() {
        return maxBetSum;
    }

    public void setMaxBetSum(String maxBetSum) {
        this.maxBetSum = maxBetSum;
    }

}
