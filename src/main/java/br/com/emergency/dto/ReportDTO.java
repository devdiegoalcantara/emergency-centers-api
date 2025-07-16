package br.com.emergency.dto;

import java.util.Map;

public class ReportDTO {
    private int totalCenters;
    private int totalNegotiations;
    private Map<String, Integer> resources;

    public int getTotalCenters() {
        return totalCenters;
    }
    public void setTotalCenters(int totalCenters) {
        this.totalCenters = totalCenters;
    }
    public int getTotalNegotiations() {
        return totalNegotiations;
    }
    public void setTotalNegotiations(int totalNegotiations) {
        this.totalNegotiations = totalNegotiations;
    }
    public Map<String, Integer> getResources() {
        return resources;
    }
    public void setResources(Map<String, Integer> resources) {
        this.resources = resources;
    }
}
