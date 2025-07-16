package br.com.emergency.controller;

import br.com.emergency.dto.NegotiationRequestDTO;
import br.com.emergency.dto.NegotiationResponseDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/negotiations")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Negotiation", description = "Operations related to negotiations between community centers")
public class NegotiationController {
    private final br.com.emergency.repository.CommunityCenterRepository repository;
    private final br.com.emergency.repository.NegotiationRepository negotiationRepository;
    public NegotiationController(br.com.emergency.repository.CommunityCenterRepository repository,
                                 br.com.emergency.repository.NegotiationRepository negotiationRepository) {
        this.repository = repository;
        this.negotiationRepository = negotiationRepository;
    }
    // Example endpoints, adapt as needed
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Negotiate resources between centers",
        description = "Initiates a negotiation between two community centers with the specified items.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Negotiation request data",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Sample Negotiation Request",
                    value = "{\n  \"originCenterId\": \"abc123\",\n  \"destinationCenterId\": \"def456\",\n  \"originItems\": [\n    { \"type\": \"MEDICAL\", \"quantity\": 1 },\n    { \"type\": \"VOLUNTEER\", \"quantity\": 2 },\n    { \"type\": \"MEDICAL_KIT\", \"quantity\": 0 },\n    { \"type\": \"VEHICLE\", \"quantity\": 0 },\n    { \"type\": \"BASIC_BASKET\", \"quantity\": 0 }\n  ],\n  \"destinationItems\": [\n    { \"type\": \"MEDICAL\", \"quantity\": 0 },\n    { \"type\": \"VOLUNTEER\", \"quantity\": 1 },\n    { \"type\": \"MEDICAL_KIT\", \"quantity\": 2 },\n    { \"type\": \"VEHICLE\", \"quantity\": 0 },\n    { \"type\": \"BASIC_BASKET\", \"quantity\": 0 }\n  ],\n  \"dateTime\": \"2025-07-15T18:00:00\"\n}"
                )
            )
        )
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Negotiation created successfully", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Negotiation Response",
                value = "{\n  \"negotiationId\": \"abc123\",\n  \"originCenterId\": \"1\",\n  \"destinationCenterId\": \"2\",\n  \"originItems\": [\n    {\"type\": \"MEDICAL\", \"quantity\": 3},\n    {\"type\": \"VOLUNTEER\", \"quantity\": 2},\n    {\"type\": \"MEDICAL_KIT\", \"quantity\": 1},\n    {\"type\": \"VEHICLE\", \"quantity\": 0},\n    {\"type\": \"BASIC_BASKET\", \"quantity\": 0}\n  ],\n  \"destinationItems\": [\n    {\"type\": \"MEDICAL\", \"quantity\": 0},\n    {\"type\": \"VOLUNTEER\", \"quantity\": 1},\n    {\"type\": \"MEDICAL_KIT\", \"quantity\": 2},\n    {\"type\": \"VEHICLE\", \"quantity\": 1},\n    {\"type\": \"BASIC_BASKET\", \"quantity\": 0}\n  ],\n  \"dateTime\": \"2025-07-15T21:52:40\",\n  \"status\": \"APPROVED\"\n}"
            )
        )),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid negotiation request")
    })
    @PostMapping
    public org.springframework.http.ResponseEntity<?> negotiate(@RequestBody NegotiationRequestDTO request) {
        // Fetch centers
        var originOpt = repository.findById(request.getOriginCenterId());
        var destOpt = repository.findById(request.getDestinationCenterId());
        if (originOpt.isEmpty() || destOpt.isEmpty()) {
            br.com.emergency.dto.NegotiationErrorDTO error = new br.com.emergency.dto.NegotiationErrorDTO();
            error.setMessage("Community center not found");
            return org.springframework.http.ResponseEntity.badRequest().body(error);
        }
        var origin = originOpt.get();
        var dest = destOpt.get();

        // Calculate item points
        int originPoints = 0;
        for (var item : request.getOriginItems()) {
            originPoints += item.getType().getPoints() * item.getQuantity();
        }
        int destPoints = 0;
        for (var item : request.getDestinationItems()) {
            destPoints += item.getType().getPoints() * item.getQuantity();
        }

        boolean originHighOcc = origin.getMaxCapacity() > 0 && ((double)origin.getCurrentOccupation() / origin.getMaxCapacity()) > 0.9;
        boolean destHighOcc = dest.getMaxCapacity() > 0 && ((double)dest.getCurrentOccupation() / dest.getMaxCapacity()) > 0.9;

        // Points rule
        if (!(originPoints == destPoints || originHighOcc || destHighOcc)) {
            br.com.emergency.dto.NegotiationErrorDTO error = new br.com.emergency.dto.NegotiationErrorDTO();
            error.setMessage("Points offered by both centers must be equal unless one is above 90% occupation.");
            error.setOriginCenterId(origin.getId());
            error.setOriginPoints(originPoints);
            error.setDestinationCenterId(dest.getId());
            error.setDestinationPoints(destPoints);
            return org.springframework.http.ResponseEntity.badRequest().body(error);
        }

        // Check if centers have enough resources
        java.util.Map<br.com.emergency.domain.model.ResourceType, Integer> originMap = new java.util.HashMap<>();
        if (origin.getResources() != null) {
            for (var r : origin.getResources()) originMap.put(r.getType(), r.getQuantity());
        }
        for (var item : request.getOriginItems()) {
            int qty = originMap.getOrDefault(item.getType(), 0);
            if (qty < item.getQuantity()) {
                br.com.emergency.dto.NegotiationErrorDTO error = new br.com.emergency.dto.NegotiationErrorDTO();
                error.setMessage("Origin center does not have enough " + item.getType());
                error.setCenterId(origin.getId());
                error.setResourceType(item.getType().toString());
                error.setAvailable(qty);
                error.setRequested(item.getQuantity());
                return org.springframework.http.ResponseEntity.badRequest().body(error);
            }
        }
        java.util.Map<br.com.emergency.domain.model.ResourceType, Integer> destMap = new java.util.HashMap<>();
        if (dest.getResources() != null) {
            for (var r : dest.getResources()) destMap.put(r.getType(), r.getQuantity());
        }
        for (var item : request.getDestinationItems()) {
            int qty = destMap.getOrDefault(item.getType(), 0);
            if (qty < item.getQuantity()) {
                br.com.emergency.dto.NegotiationErrorDTO error = new br.com.emergency.dto.NegotiationErrorDTO();
                error.setMessage("Destination center does not have enough " + item.getType());
                error.setCenterId(dest.getId());
                error.setResourceType(item.getType().toString());
                error.setAvailable(qty);
                error.setRequested(item.getQuantity());
                return org.springframework.http.ResponseEntity.badRequest().body(error);
            }
        }

        // Transfer resources: remove from origin, add to destination
        for (var item : request.getOriginItems()) {
            originMap.put(item.getType(), originMap.get(item.getType()) - item.getQuantity());
            destMap.put(item.getType(), destMap.getOrDefault(item.getType(), 0) + item.getQuantity());
        }
        for (var item : request.getDestinationItems()) {
            destMap.put(item.getType(), destMap.get(item.getType()) - item.getQuantity());
            originMap.put(item.getType(), originMap.getOrDefault(item.getType(), 0) + item.getQuantity());
        }
        // Update Resource lists
        java.util.List<br.com.emergency.domain.model.Resource> newOriginRes = new java.util.ArrayList<>();
        for (var entry : originMap.entrySet()) {
            if (entry.getValue() > 0) newOriginRes.add(new br.com.emergency.domain.model.Resource(entry.getKey(), entry.getValue()));
        }
        java.util.List<br.com.emergency.domain.model.Resource> newDestRes = new java.util.ArrayList<>();
        for (var entry : destMap.entrySet()) {
            if (entry.getValue() > 0) newDestRes.add(new br.com.emergency.domain.model.Resource(entry.getKey(), entry.getValue()));
        }
        origin.setResources(newOriginRes);
        dest.setResources(newDestRes);
        repository.save(origin);
        repository.save(dest);

        // Save history
        var negotiation = new br.com.emergency.domain.model.Negotiation();
        negotiation.setOriginCenterId(origin.getId());
        negotiation.setDestinationCenterId(dest.getId());
        negotiation.setDateTime(java.time.LocalDateTime.now());
        negotiation.setOriginItems(request.getOriginItems());
        negotiation.setDestinationItems(request.getDestinationItems());
        negotiation.setTotalPoints(Math.max(originPoints, destPoints));
        negotiation = negotiationRepository.save(negotiation);

        // Build response
        var resp = new br.com.emergency.dto.NegotiationResponseDTO();
        resp.setNegotiationId(negotiation.getId());
        resp.setOriginCenterId(negotiation.getOriginCenterId());
        resp.setDestinationCenterId(negotiation.getDestinationCenterId());
        resp.setOriginItems(negotiation.getOriginItems());
        resp.setDestinationItems(negotiation.getDestinationItems());
        resp.setDateTime(negotiation.getDateTime());
        resp.setStatus("APPROVED");
        // Exception message if negotiation was approved due to high occupation
        if (originHighOcc || destHighOcc) {
            StringBuilder msg = new StringBuilder("The exchange was approved because ");
            if (originHighOcc && destHighOcc) {
                msg.append("both centers are above 90% capacity, allowing an exception to the points rule.");
            } else if (originHighOcc) {
                msg.append("the origin center (ID: ").append(origin.getId()).append(") is above 90% capacity, allowing an exception to the points rule.");
            } else {
                msg.append("the destination center (ID: ").append(dest.getId()).append(") is above 90% capacity, allowing an exception to the points rule.");
            }
            resp.setMessage(msg.toString());
        }
        return org.springframework.http.ResponseEntity.ok(resp);
    }
    @io.swagger.v3.oas.annotations.Operation(
        summary = "List all negotiations",
        description = "Retrieves a list of all negotiations between community centers.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of negotiations",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Negotiations List",
                        value = "[{\n  \"negotiationId\": \"abc123\",\n  \"originCenterId\": \"1\",\n  \"destinationCenterId\": \"2\",\n  \"originItems\": [\n    {\"type\": \"MEDICAL\", \"quantity\": 2},\n    {\"type\": \"VOLUNTEER\", \"quantity\": 1},\n    {\"type\": \"MEDICAL_KIT\", \"quantity\": 0},\n    {\"type\": \"VEHICLE\", \"quantity\": 0},\n    {\"type\": \"BASIC_BASKET\", \"quantity\": 0}\n  ],\n  \"destinationItems\": [\n    {\"type\": \"MEDICAL\", \"quantity\": 0},\n    {\"type\": \"VOLUNTEER\", \"quantity\": 0},\n    {\"type\": \"MEDICAL_KIT\", \"quantity\": 1},\n    {\"type\": \"VEHICLE\", \"quantity\": 1},\n    {\"type\": \"BASIC_BASKET\", \"quantity\": 0}\n  ],\n  \"dateTime\": \"2025-07-15T18:00:00\",\n  \"status\": \"APPROVED\"\n}]"
                    )
                )
            )
        }
    )
    @GetMapping
    public java.util.List<br.com.emergency.domain.model.Negotiation> list() {
        return negotiationRepository.findAll();
    }
}
