package br.com.emergency.domain.model;

public enum ResourceType {
    MEDICAL(4),
    VOLUNTEER(3),
    MEDICAL_KIT(7),
    VEHICLE(5),
    BASIC_BASKET(2);

    private final int points;
    ResourceType(int points) { this.points = points; }
    public int getPoints() { return points; }
}
