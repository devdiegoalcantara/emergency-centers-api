package br.com.emergency.controller;

import br.com.emergency.domain.model.CommunityCenter;
import br.com.emergency.domain.model.Location;
import br.com.emergency.domain.model.Resource;
import br.com.emergency.dto.CommunityCenterRequestDTO;
import br.com.emergency.repository.CommunityCenterRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;
import br.com.emergency.domain.model.ResourceType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommunityCenterController.class)
public class CommunityCenterControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommunityCenterRepository repository;
    @MockBean
    private br.com.emergency.event.MaxCapacityNotificationPublisher notificationPublisher;
    @Autowired
    private ObjectMapper objectMapper;

    private CommunityCenter center;
    private CommunityCenterRequestDTO requestDTO;

    @BeforeEach
    void setup() {
        center = new CommunityCenter();
        center.setId("1");
        center.setName("Test Center");
        center.setAddress("Test Address");
        center.setLocation(new Location(0.0, 0.0));
        center.setMaxCapacity(100);
        center.setCurrentOccupation(10);
        center.setResources(Arrays.asList(new Resource(ResourceType.MEDICAL, 2)));

        requestDTO = new CommunityCenterRequestDTO();
        requestDTO.setName("Test Center");
        requestDTO.setAddress("Test Address");
        requestDTO.setLocation(new Location(0.0, 0.0));
        requestDTO.setMaxCapacity(100);
        requestDTO.setCurrentOccupation(10);
        requestDTO.setResources(Arrays.asList(new Resource(ResourceType.MEDICAL, 2)));
    }

    @Test
    void testDelete_Success() throws Exception {
        Mockito.when(repository.existsById("1")).thenReturn(true);
        mockMvc.perform(delete("/community-centers/1").header("X-API-KEY", "supersecret"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDelete_NotFound() throws Exception {
        Mockito.when(repository.existsById("1")).thenReturn(false);
        mockMvc.perform(delete("/community-centers/1").header("X-API-KEY", "supersecret"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateAll_Success() throws Exception {
        Mockito.when(repository.findById("1")).thenReturn(Optional.of(center));
        Mockito.when(repository.save(any(CommunityCenter.class))).thenReturn(center);
        mockMvc.perform(put("/community-centers/1").header("X-API-KEY", "supersecret")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    void testUpdateAll_NotFound() throws Exception {
        Mockito.when(repository.findById("1")).thenReturn(Optional.empty());
        mockMvc.perform(put("/community-centers/1").header("X-API-KEY", "supersecret")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound());
    }


}
