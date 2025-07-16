package br.com.emergency.dto;

public class NegotiationErrorDTO {
    private String message;
    private String centerId;
    private String resourceType;
    private Integer available;
    private Integer requested;
    private String originCenterId;
    private Integer originPoints;
    private String destinationCenterId;
    private Integer destinationPoints;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getCenterId() { return centerId; }
    public void setCenterId(String centerId) { this.centerId = centerId; }

    public String getResourceType() { return resourceType; }
    public void setResourceType(String resourceType) { this.resourceType = resourceType; }

    public Integer getAvailable() { return available; }
    public void setAvailable(Integer available) { this.available = available; }

    public Integer getRequested() { return requested; }
    public void setRequested(Integer requested) { this.requested = requested; }

    public String getOriginCenterId() { return originCenterId; }
    public void setOriginCenterId(String originCenterId) { this.originCenterId = originCenterId; }

    public Integer getOriginPoints() { return originPoints; }
    public void setOriginPoints(Integer originPoints) { this.originPoints = originPoints; }

    public String getDestinationCenterId() { return destinationCenterId; }
    public void setDestinationCenterId(String destinationCenterId) { this.destinationCenterId = destinationCenterId; }

    public Integer getDestinationPoints() { return destinationPoints; }
    public void setDestinationPoints(Integer destinationPoints) { this.destinationPoints = destinationPoints; }
}
