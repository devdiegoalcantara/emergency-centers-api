package br.com.emergency.dto;

import br.com.emergency.domain.model.Location;
import br.com.emergency.domain.model.Resource;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(
    example = "{\n" +
        "  \"id\": \"1\",\n" +
        "  \"name\": \"Example Center\",\n" +
        "  \"address\": \"123 Example Street\",\n" +
        "  \"location\": {\"latitude\": 0.0, \"longitude\": 0.0},\n" +
        "  \"maxCapacity\": 100,\n" +
        "  \"currentOccupation\": 10,\n" +
        "  \"resources\": [\n" +
        "    {\"type\": \"MEDICAL\", \"quantity\": 2},\n" +
        "    {\"type\": \"VOLUNTEER\", \"quantity\": 3},\n" +
        "    {\"type\": \"MEDICAL_KIT\", \"quantity\": 1},\n" +
        "    {\"type\": \"VEHICLE\", \"quantity\": 1},\n" +
        "    {\"type\": \"BASIC_BASKET\", \"quantity\": 5}\n" +
        "  ]\n" +
        "}"
)
public class CommunityCenterResponseDTO {
    private String id;
    private String name;
    private String address;
    private Location location;
    private int maxCapacity;
    private int currentOccupation;
    private List<Resource> resources;
    private String message; // Optional field for informative messages

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
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
