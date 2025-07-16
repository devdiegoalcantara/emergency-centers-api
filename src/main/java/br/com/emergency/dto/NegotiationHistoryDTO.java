package br.com.emergency.dto;

import java.time.LocalDateTime;
import java.util.List;

public class NegotiationHistoryDTO {
    private String negotiationId;
    private String originCenterId;
    private String destinationCenterId;
    private LocalDateTime dateTime;
    private List<ItemNegotiatedDTO> originItems;
    private List<ItemNegotiatedDTO> destinationItems;
    private int totalPoints;

    // Getters and setters
    public String getNegotiationId() { return negotiationId; }
    public void setNegotiationId(String negotiationId) { this.negotiationId = negotiationId; }
    public String getOriginCenterId() { return originCenterId; }
    public void setOriginCenterId(String originCenterId) { this.originCenterId = originCenterId; }
    public String getDestinationCenterId() { return destinationCenterId; }
    public void setDestinationCenterId(String destinationCenterId) { this.destinationCenterId = destinationCenterId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public List<ItemNegotiatedDTO> getOriginItems() { return originItems; }
    public void setOriginItems(List<ItemNegotiatedDTO> originItems) { this.originItems = originItems; }
    public List<ItemNegotiatedDTO> getDestinationItems() { return destinationItems; }
    public void setDestinationItems(List<ItemNegotiatedDTO> destinationItems) { this.destinationItems = destinationItems; }
    public int getTotalPoints() { return totalPoints; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }
}
