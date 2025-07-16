package br.com.emergency.service;

import br.com.emergency.domain.model.Negotiation;
import br.com.emergency.dto.NegotiationRequestDTO;
import br.com.emergency.repository.NegotiationRepository;
import br.com.emergency.repository.CommunityCenterRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NegotiationService {
    @Autowired
    private NegotiationRepository negotiationRepository;
    @Autowired
    private CommunityCenterRepository communityCenterRepository;


    public void negotiateResources(NegotiationRequestDTO requestDTO) {
        // TODO: Implement negotiation logic
    }
}
