package br.com.emergency.event;

public class MaxCapacityNotificationEvent {
    private String centerId;
    private String centerName;
    private int maxCapacity;
    private int currentOccupation;

    public String getCenterId() { return centerId; }
    public void setCenterId(String centerId) { this.centerId = centerId; }
    public String getCenterName() { return centerName; }
    public void setCenterName(String centerName) { this.centerName = centerName; }
    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    public int getCurrentOccupation() { return currentOccupation; }
    public void setCurrentOccupation(int currentOccupation) { this.currentOccupation = currentOccupation; }
}
