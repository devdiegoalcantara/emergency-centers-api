package br.com.emergency.domain.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "negotiations")
public class Negotiation {
    @Id
    private String id;
    private String originCenterId;
    private String destinationCenterId;
    private LocalDateTime dateTime;
    private java.util.List<ItemNegotiated> originItems;
    private java.util.List<ItemNegotiated> destinationItems;
    private int totalPoints;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getOriginCenterId() { return originCenterId; }
    public void setOriginCenterId(String originCenterId) { this.originCenterId = originCenterId; }
    public String getDestinationCenterId() { return destinationCenterId; }
    public void setDestinationCenterId(String destinationCenterId) { this.destinationCenterId = destinationCenterId; }
    public LocalDateTime getDateTime() { return dateTime; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public java.util.List<ItemNegotiated> getOriginItems() {
        return originItems;
    }
    public void setOriginItems(java.util.List<ItemNegotiated> originItems) {
        this.originItems = originItems;
    }
    public java.util.List<ItemNegotiated> getDestinationItems() {
        return destinationItems;
    }
    public void setDestinationItems(java.util.List<ItemNegotiated> destinationItems) {
        this.destinationItems = destinationItems;
    }
    public int getTotalPoints() { return totalPoints; }
    public void setTotalPoints(int totalPoints) { this.totalPoints = totalPoints; }
}
