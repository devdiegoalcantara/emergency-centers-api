package br.com.emergency.controller;

import br.com.emergency.dto.CommunityCenterRequestDTO;
import br.com.emergency.dto.CommunityCenterResponseDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/community-centers")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Community Center", description = "Operations related to community centers")
public class CommunityCenterController {
    @org.springframework.beans.factory.annotation.Autowired
    private br.com.emergency.event.MaxCapacityNotificationPublisher notificationPublisher;
    @org.springframework.beans.factory.annotation.Autowired
    private br.com.emergency.repository.CommunityCenterRepository repository;
    @io.swagger.v3.oas.annotations.Operation(
        summary = "Register a new community center",
        description = "Creates a new community center with the provided information.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Community center registration data",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Sample Center",
                    value = "{\n  \"name\": \"Example Center\",\n  \"address\": \"123 Example Street\",\n  \"location\": {\"latitude\": 0.0, \"longitude\": 0.0},\n  \"maxCapacity\": 100,\n  \"currentOccupation\": 10,\n  \"resources\": [\n    {\"type\": \"MEDICAL\", \"quantity\": 2},\n    {\"type\": \"VOLUNTEER\", \"quantity\": 3},\n    {\"type\": \"MEDICAL_KIT\", \"quantity\": 1},\n    {\"type\": \"VEHICLE\", \"quantity\": 1},\n    {\"type\": \"BASIC_BASKET\", \"quantity\": 5}\n  ]\n}"
                )
            )
        )
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses({
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Community center created successfully", content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                name = "Created Center",
                value = "{\n  \"id\": \"1\",\n  \"name\": \"Example Center\",\n  \"address\": \"123 Example Street\",\n  \"location\": {\"latitude\": 0.0, \"longitude\": 0.0},\n  \"maxCapacity\": 100,\n  \"currentOccupation\": 10,\n  \"resources\": [\n    {\"type\": \"MEDICAL\", \"quantity\": 2},\n    {\"type\": \"VOLUNTEER\", \"quantity\": 3},\n    {\"type\": \"MEDICAL_KIT\", \"quantity\": 1},\n    {\"type\": \"VEHICLE\", \"quantity\": 1},\n    {\"type\": \"BASIC_BASKET\", \"quantity\": 5}\n  ]\n}"
            )
        )),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public CommunityCenterResponseDTO create(@RequestBody CommunityCenterRequestDTO request) {
        br.com.emergency.domain.model.CommunityCenter center = new br.com.emergency.domain.model.CommunityCenter();
        center.setName(request.getName());
        center.setAddress(request.getAddress());
        center.setLocation(request.getLocation());
        center.setMaxCapacity(request.getMaxCapacity());
        center.setCurrentOccupation(request.getCurrentOccupation());
        center.setResources(request.getResources());
        // Save to MongoDB
        center = repository.save(center);

        CommunityCenterResponseDTO response = new CommunityCenterResponseDTO();
        response.setId(center.getId());
        response.setName(center.getName());
        response.setAddress(center.getAddress());
        response.setLocation(center.getLocation());
        response.setMaxCapacity(center.getMaxCapacity());
        response.setCurrentOccupation(center.getCurrentOccupation());
        response.setResources(center.getResources());
        return response;
    }

    @io.swagger.v3.oas.annotations.Operation(
        summary = "List all community centers",
        description = "Retrieves a list of all registered community centers.",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of community centers",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Centers List",
                        value = "[{\n  \"id\": \"1\",\n  \"name\": \"Example Center\",\n  \"address\": \"123 Example Street\",\n  \"location\": {\"latitude\": 0.0, \"longitude\": 0.0},\n  \"maxCapacity\": 100,\n  \"currentOccupation\": 10,\n  \"resources\": [\n    {\"type\": \"MEDICAL\", \"quantity\": 2},\n    {\"type\": \"VOLUNTEER\", \"quantity\": 3},\n    {\"type\": \"MEDICAL_KIT\", \"quantity\": 1},\n    {\"type\": \"VEHICLE\", \"quantity\": 1},\n    {\"type\": \"BASIC_BASKET\", \"quantity\": 5}\n  ]\n}]"
                    )
                )
            )
        }
    )
    @GetMapping
    public List<CommunityCenterResponseDTO> list(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Integer minCapacity
    ) {
        List<br.com.emergency.domain.model.CommunityCenter> centers = repository.findAll();
        Stream<br.com.emergency.domain.model.CommunityCenter> stream = centers.stream();
        if (name != null) {
            stream = stream.filter(c -> c.getName() != null && c.getName().toLowerCase().contains(name.toLowerCase()));
        }
        if (address != null) {
            stream = stream.filter(c -> c.getAddress() != null && c.getAddress().toLowerCase().contains(address.toLowerCase()));
        }
        if (minCapacity != null) {
            stream = stream.filter(c -> c.getMaxCapacity() >= minCapacity);
        }
        return stream
            .map(center -> {
                CommunityCenterResponseDTO dto = new CommunityCenterResponseDTO();
                dto.setId(center.getId());
                dto.setName(center.getName());
                dto.setAddress(center.getAddress());
                dto.setLocation(center.getLocation());
                dto.setMaxCapacity(center.getMaxCapacity());
                dto.setCurrentOccupation(center.getCurrentOccupation());
                dto.setResources(center.getResources());
                return dto;
            })
            .collect(Collectors.toList());
    }

    @io.swagger.v3.oas.annotations.Operation(
        summary = "Delete a community center",
        description = "Deletes a community center by its ID.",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID of the community center to delete", required = true, example = "1")
        },
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Community center deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Community center not found")
        }
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        if (!repository.existsById(id)) {
            throw new br.com.emergency.exception.CommunityCenterNotFoundException("Community center not found");
        }
        repository.deleteById(id);
    }


    @io.swagger.v3.oas.annotations.Operation(
        summary = "Update all fields of a community center",
        description = "Updates all editable fields of a community center by its ID.\n\nAtenção: Os recursos (resources) NÃO podem ser alterados por este endpoint. Para modificar recursos, utilize o fluxo de negociação.",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID of the community center to update", required = true, example = "1")
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Updated data for the community center",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Update Center",
                    value = "{\n  \"name\": \"Updated Center\",\n  \"address\": \"456 Updated Ave\",\n  \"location\": {\"latitude\": 1.0, \"longitude\": 1.0},\n  \"maxCapacity\": 200,\n  \"currentOccupation\": 20,\n  \"resources\": [\n    {\"type\": \"MEDICAL\", \"quantity\": 4}\n  ]\n}"
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "Community center updated successfully",
                content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                        name = "Updated Center",
                        value = "{\n  \"id\": \"1\",\n  \"name\": \"Updated Center\",\n  \"address\": \"456 Updated Ave\",\n  \"location\": {\"latitude\": 1.0, \"longitude\": 1.0},\n  \"maxCapacity\": 200,\n  \"currentOccupation\": 20,\n  \"resources\": [\n    {\"type\": \"MEDICAL\", \"quantity\": 4}\n  ]\n}"
                    )
                )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input data"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Community center not found")
        }
    )
    @PutMapping("/{id}")
    public CommunityCenterResponseDTO updateAll(
        @PathVariable String id,
        @RequestBody br.com.emergency.dto.CommunityCenterRequestDTO request
    ) {
        br.com.emergency.domain.model.CommunityCenter center = repository.findById(id)
            .orElseThrow(() -> new br.com.emergency.exception.CommunityCenterNotFoundException("Community center not found"));
        center.setName(request.getName());
        center.setAddress(request.getAddress());
        center.setLocation(request.getLocation());
        center.setMaxCapacity(request.getMaxCapacity());
        center.setCurrentOccupation(request.getCurrentOccupation());
        // Explicitly do NOT update resources. Ignore any incoming resources in the request.
        repository.save(center);
        br.com.emergency.dto.CommunityCenterResponseDTO response = new br.com.emergency.dto.CommunityCenterResponseDTO();
        response.setId(center.getId());
        response.setName(center.getName());
        response.setAddress(center.getAddress());
        response.setLocation(center.getLocation());
        response.setMaxCapacity(center.getMaxCapacity());
        response.setCurrentOccupation(center.getCurrentOccupation());
        response.setResources(center.getResources());
        response.setMessage("Attention: The resources (resources) CANNOT be changed by this endpoint. To modify resources, use the negotiation flow.");
        return response;
    }

    @io.swagger.v3.oas.annotations.Operation(
        summary = "Update current occupation of a community center",
        description = "Updates the number of people currently occupying the center. Triggers a notification if max capacity is reached.",
        parameters = {
            @io.swagger.v3.oas.annotations.Parameter(name = "id", description = "ID of the community center", required = true)
        },
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Occupation update data",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Occupation Update",
                    value = "{\n  \"currentOccupation\": 95\n}"
                )
            )
        ),
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Occupation updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Community center not found")
        }
    )
    @PutMapping("/{id}/occupation")
    public br.com.emergency.dto.CommunityCenterResponseDTO updateOccupation(
            @PathVariable String id,
            @RequestBody br.com.emergency.dto.OccupationUpdateDTO occupationDTO) {
        var opt = repository.findById(id);
        if (opt.isEmpty()) {
            throw new br.com.emergency.exception.CommunityCenterNotFoundException("Community center not found");
        }
        var center = opt.get();
        center.setCurrentOccupation(occupationDTO.getCurrentOccupation());
        repository.save(center);
        // Notificação simulada
        if (center.getMaxCapacity() > 0 && center.getCurrentOccupation() >= center.getMaxCapacity()) {
            br.com.emergency.event.MaxCapacityNotificationEvent event = new br.com.emergency.event.MaxCapacityNotificationEvent();
            event.setCenterId(center.getId());
            event.setCenterName(center.getName());
            event.setMaxCapacity(center.getMaxCapacity());
            event.setCurrentOccupation(center.getCurrentOccupation());
            notificationPublisher.publish(event);
        }
        br.com.emergency.dto.CommunityCenterResponseDTO response = new br.com.emergency.dto.CommunityCenterResponseDTO();
        response.setId(center.getId());
        response.setName(center.getName());
        response.setAddress(center.getAddress());
        response.setLocation(center.getLocation());
        response.setMaxCapacity(center.getMaxCapacity());
        response.setCurrentOccupation(center.getCurrentOccupation());
        response.setResources(center.getResources());
        return response;
    }
}

