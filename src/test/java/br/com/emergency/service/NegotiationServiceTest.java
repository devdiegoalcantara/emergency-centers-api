package br.com.emergency.service;

import br.com.emergency.domain.model.CommunityCenter;
import br.com.emergency.domain.model.Negotiation;
import br.com.emergency.dto.NegotiationRequestDTO;

import br.com.emergency.repository.CommunityCenterRepository;
import br.com.emergency.repository.NegotiationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NegotiationServiceTest {
    @Mock
    private NegotiationRepository negotiationRepository;
    @Mock
    private CommunityCenterRepository communityCenterRepository;


    @InjectMocks
    private NegotiationService negotiationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void negotiateResources_success() {
        CommunityCenter origin = new CommunityCenter();
        origin.setId("1");
        origin.setMaxCapacity(100);
        origin.setCurrentOccupation(10);
        // Setup more attributes as needed
        CommunityCenter destination = new CommunityCenter();
        destination.setId("2");
        destination.setMaxCapacity(100);
        destination.setCurrentOccupation(20);
        // Setup more attributes as needed

        NegotiationRequestDTO requestDTO = new NegotiationRequestDTO();
        // Setup DTO as needed

        when(communityCenterRepository.findById("1")).thenReturn(Optional.of(origin));
        when(communityCenterRepository.findById("2")).thenReturn(Optional.of(destination));
        // Add more mock setups as needed

        // Call the method under test
        // negotiationService.negotiateResources(requestDTO);
        // Add assertions as needed
        // Example:
        // assertEquals(expectedValue, actualValue);
    }

    @Test
    void negotiateResources_pointsNotEquivalent_failure() {
        CommunityCenter origin = new CommunityCenter();
        origin.setId("1");
        origin.setMaxCapacity(100);
        origin.setCurrentOccupation(10);
        CommunityCenter destination = new CommunityCenter();
        destination.setId("2");
        destination.setMaxCapacity(100);
        destination.setCurrentOccupation(20);
        NegotiationRequestDTO requestDTO = new NegotiationRequestDTO();
        // Setup DTO as needed

        when(communityCenterRepository.findById("1")).thenReturn(Optional.of(origin));
        when(communityCenterRepository.findById("2")).thenReturn(Optional.of(destination));
        // Add more mock setups as needed

        // Uncomment and adapt as needed:
        // assertThrows(InvalidNegotiationException.class, () -> {
        //     negotiationService.negotiateResources(requestDTO);
        // });
    }
}
