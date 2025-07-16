package br.com.emergency.repository;

import br.com.emergency.domain.model.Negotiation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegotiationRepository extends MongoRepository<Negotiation, String> {
}
