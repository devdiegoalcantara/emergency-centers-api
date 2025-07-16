package br.com.emergency.domain.model;

import java.util.List;

public class CommunityCenter {
    private String id;
    private String name;
    private String address;
    private Location location;
    private int maxCapacity;
    private int currentOccupation;
    private List<Resource> resources;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }
    public int getCurrentOccupation() { return currentOccupation; }
    public void setCurrentOccupation(int currentOccupation) { this.currentOccupation = currentOccupation; }
    public List<Resource> getResources() { return resources; }
    public void setResources(List<Resource> resources) { this.resources = resources; }
}
