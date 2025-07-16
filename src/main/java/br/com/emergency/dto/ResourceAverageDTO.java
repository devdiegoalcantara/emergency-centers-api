package br.com.emergency.dto;

import java.util.Map;

public class ResourceAverageDTO {
    private Map<String, Double> averageResources;

    public Map<String, Double> getAverageResources() {
        return averageResources;
    }
    public void setAverageResources(Map<String, Double> averageResources) {
        this.averageResources = averageResources;
    }
}
