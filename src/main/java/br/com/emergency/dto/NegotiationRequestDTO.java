package br.com.emergency.dto;

import br.com.emergency.domain.model.ItemNegotiated;
import java.util.List;

@io.swagger.v3.oas.annotations.media.Schema(
    example = "{\n" +
        "  \"originCenterId\": \"1\",\n" +
        "  \"destinationCenterId\": \"2\",\n" +
        "  \"originItems\": [\"WATER\", \"FOOD\"],\n" +
        "  \"destinationItems\": [\"MEDICAL_KIT\"],\n" +
        "  \"dateTime\": \"2025-07-15T18:00:00\"\n" +
        "}"
)
public class NegotiationRequestDTO {
    private String originCenterId;
    private String destinationCenterId;
    private java.util.List<br.com.emergency.domain.model.ItemNegotiated> originItems;
    private java.util.List<br.com.emergency.domain.model.ItemNegotiated> destinationItems;

    public String getOriginCenterId() { return originCenterId; }
    public void setOriginCenterId(String originCenterId) { this.originCenterId = originCenterId; }
    public String getDestinationCenterId() { return destinationCenterId; }
    public void setDestinationCenterId(String destinationCenterId) { this.destinationCenterId = destinationCenterId; }
    public java.util.List<br.com.emergency.domain.model.ItemNegotiated> getOriginItems() { return originItems; }
    public void setOriginItems(java.util.List<br.com.emergency.domain.model.ItemNegotiated> originItems) { this.originItems = originItems; }
    public java.util.List<br.com.emergency.domain.model.ItemNegotiated> getDestinationItems() { return destinationItems; }
    public void setDestinationItems(java.util.List<br.com.emergency.domain.model.ItemNegotiated> destinationItems) { this.destinationItems = destinationItems; }
}
