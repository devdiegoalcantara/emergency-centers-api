package br.com.emergency.dto;

import br.com.emergency.domain.model.ResourceType;

public class ItemNegotiatedDTO {
    private ResourceType type;
    private int quantity;
    public ResourceType getType() { return type; }
    public void setType(ResourceType type) { this.type = type; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
