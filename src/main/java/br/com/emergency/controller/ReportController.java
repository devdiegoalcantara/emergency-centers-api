package br.com.emergency.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Report", description = "Operations related to system reports")
public class ReportController {
    private final br.com.emergency.repository.CommunityCenterRepository communityCenterRepository;
    private final br.com.emergency.repository.NegotiationRepository negotiationRepository;

    public ReportController(br.com.emergency.repository.CommunityCenterRepository communityCenterRepository,
                            br.com.emergency.repository.NegotiationRepository negotiationRepository) {
        this.communityCenterRepository = communityCenterRepository;
        this.negotiationRepository = negotiationRepository;
    }

    @io.swagger.v3.oas.annotations.Operation(
        summary = "List community centers with occupation greater than 90%",
        description = "Returns all community centers where currentOccupation/maxCapacity > 90%.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of high occupation centers"
            )
        }
    )
    @GetMapping("/high-occupation-centers")
    public java.util.List<br.com.emergency.dto.HighOccupationCenterDTO> getHighOccupationCenters() {
        java.util.List<br.com.emergency.domain.model.CommunityCenter> centers = communityCenterRepository.findAll();
        java.util.List<br.com.emergency.dto.HighOccupationCenterDTO> result = new java.util.ArrayList<>();
        for (br.com.emergency.domain.model.CommunityCenter c : centers) {
            if (c.getMaxCapacity() > 0 && ((double)c.getCurrentOccupation() / c.getMaxCapacity()) > 0.9) {
                br.com.emergency.dto.HighOccupationCenterDTO dto = new br.com.emergency.dto.HighOccupationCenterDTO();
                dto.setId(c.getId());
                dto.setName(c.getName());
                dto.setAddress(c.getAddress());
                dto.setLocation(c.getLocation());
                dto.setMaxCapacity(c.getMaxCapacity());
                dto.setCurrentOccupation(c.getCurrentOccupation());
                dto.setResources(c.getResources());
                result.add(dto);
            }
        }
        return result;
    }

    @io.swagger.v3.oas.annotations.Operation(
        summary = "Get average quantity of each resource type per center",
        description = "Returns the average quantity of each resource type registered in the system (e.g., 2 vehicles per center).",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Average resource quantities"
            )
        }
    )
    @GetMapping("/average-resources")
    public br.com.emergency.dto.ResourceAverageDTO getAverageResources() {
        java.util.List<br.com.emergency.domain.model.CommunityCenter> centers = communityCenterRepository.findAll();
        java.util.Map<String, Double> averages = new java.util.HashMap<>();
        int totalCenters = centers.size();
        java.util.Map<String, Integer> totalByType = new java.util.HashMap<>();
        for (br.com.emergency.domain.model.ResourceType type : br.com.emergency.domain.model.ResourceType.values()) {
            totalByType.put(type.name(), 0);
        }
        for (br.com.emergency.domain.model.CommunityCenter center : centers) {
            if (center.getResources() != null) {
                for (br.com.emergency.domain.model.Resource resource : center.getResources()) {
                    String typeName = resource.getType().name();
                    totalByType.put(typeName, totalByType.getOrDefault(typeName, 0) + resource.getQuantity());
                }
            }
        }
        for (String type : totalByType.keySet()) {
            averages.put(type, totalCenters > 0 ? ((double)totalByType.get(type))/totalCenters : 0.0);
        }
        br.com.emergency.dto.ResourceAverageDTO dto = new br.com.emergency.dto.ResourceAverageDTO();
        dto.setAverageResources(averages);
        return dto;
    }

    @io.swagger.v3.oas.annotations.Operation(
        summary = "Negotiation history for a specific center",
        description = "Returns the negotiation history for a given center. Requires centerId as a parameter. Optionally filter by startDate (ISO 8601).",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "centerId", description = "ID of the community center (origin or destination)", required = true),
            @io.swagger.v3.oas.annotations.Parameter(name = "startDate", description = "Filter negotiations from this date/time (inclusive). Use ISO 8601 format.", required = false)
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Negotiation history for center"
            )
        }
    )
    @GetMapping("/negotiation-history")
    public java.util.List<br.com.emergency.dto.NegotiationHistoryDTO> getNegotiationHistory(
            @RequestParam String centerId,
            @RequestParam(required = false) java.time.LocalDateTime startDate) {
        java.util.List<br.com.emergency.domain.model.Negotiation> negotiations = negotiationRepository.findAll();
        java.util.List<br.com.emergency.dto.NegotiationHistoryDTO> result = new java.util.ArrayList<>();
        for (br.com.emergency.domain.model.Negotiation n : negotiations) {
            boolean isForCenter = centerId.equals(n.getOriginCenterId()) || centerId.equals(n.getDestinationCenterId());
            boolean afterStart = (startDate == null) || (n.getDateTime() != null && !n.getDateTime().isBefore(startDate));
            if (isForCenter && afterStart) {
                br.com.emergency.dto.NegotiationHistoryDTO dto = new br.com.emergency.dto.NegotiationHistoryDTO();
                dto.setNegotiationId(n.getId());
                dto.setOriginCenterId(n.getOriginCenterId());
                dto.setDestinationCenterId(n.getDestinationCenterId());
                dto.setDateTime(n.getDateTime());
                dto.setTotalPoints(n.getTotalPoints());
                // Converter originItems e destinationItems para DTO
                java.util.List<br.com.emergency.dto.ItemNegotiatedDTO> originItems = new java.util.ArrayList<>();
                if (n.getOriginItems() != null) {
                    for (br.com.emergency.domain.model.ItemNegotiated item : n.getOriginItems()) {
                        br.com.emergency.dto.ItemNegotiatedDTO itemDTO = new br.com.emergency.dto.ItemNegotiatedDTO();
                        itemDTO.setType(item.getType());
                        itemDTO.setQuantity(item.getQuantity());
                        originItems.add(itemDTO);
                    }
                }
                java.util.List<br.com.emergency.dto.ItemNegotiatedDTO> destinationItems = new java.util.ArrayList<>();
                if (n.getDestinationItems() != null) {
                    for (br.com.emergency.domain.model.ItemNegotiated item : n.getDestinationItems()) {
                        br.com.emergency.dto.ItemNegotiatedDTO itemDTO = new br.com.emergency.dto.ItemNegotiatedDTO();
                        itemDTO.setType(item.getType());
                        itemDTO.setQuantity(item.getQuantity());
                        destinationItems.add(itemDTO);
                    }
                }
                dto.setOriginItems(originItems);
                dto.setDestinationItems(destinationItems);
                result.add(dto);
            }
        }
        return result;
    }

    @io.swagger.v3.oas.annotations.Operation(
        summary = "Generate system report",
        description = "Returns a summary report of the emergency centers, negotiations, and resources.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Report generated successfully",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Sample Report",
                        value = "{\n  \"totalCenters\": 5,\n  \"totalNegotiations\": 12,\n  \"resources\": {\n    \"MEDICAL\": 10,\n    \"VOLUNTEER\": 20,\n    \"MEDICAL_KIT\": 5,\n    \"VEHICLE\": 3,\n    \"BASIC_BASKET\": 15\n  }\n}"
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping
    public br.com.emergency.dto.ReportDTO getReport() {
        java.util.List<br.com.emergency.domain.model.CommunityCenter> centers = communityCenterRepository.findAll();
        int totalCenters = centers.size();
        int totalNegotiations = (int) negotiationRepository.count();
        java.util.Map<String, Integer> resources = new java.util.HashMap<>();
        for (br.com.emergency.domain.model.ResourceType type : br.com.emergency.domain.model.ResourceType.values()) {
            resources.put(type.name(), 0);
        }
        for (br.com.emergency.domain.model.CommunityCenter center : centers) {
            if (center.getResources() != null) {
                for (br.com.emergency.domain.model.Resource resource : center.getResources()) {
                    String typeName = resource.getType().name();
                    resources.put(typeName, resources.getOrDefault(typeName, 0) + resource.getQuantity());
                }
            }
        }
        br.com.emergency.dto.ReportDTO dto = new br.com.emergency.dto.ReportDTO();
        dto.setTotalCenters(totalCenters);
        dto.setTotalNegotiations(totalNegotiations);
        dto.setResources(resources);
        return dto;
    }
}

