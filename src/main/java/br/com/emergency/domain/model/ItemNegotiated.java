package br.com.emergency.domain.model;

@io.swagger.v3.oas.annotations.media.Schema(
    example = "{\n  \"type\": \"MEDICAL_KIT\",\n  \"quantity\": 5\n}"
)
public class ItemNegotiated {
    private ResourceType type;
    private int quantity;

    public ItemNegotiated() {}
    public ItemNegotiated(ResourceType type, int quantity) {
        this.type = type;
        this.quantity = quantity;
    }
    public ResourceType getType() { return type; }
    public void setType(ResourceType type) { this.type = type; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
