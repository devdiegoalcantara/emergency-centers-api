package br.com.emergency.domain.model;

@io.swagger.v3.oas.annotations.media.Schema(
    example = "{\n  \"latitude\": -23.5505,\n  \"longitude\": -46.6333\n}"
)
public class Location {
    private double latitude;
    private double longitude;

    public Location() {}
    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}
