package br.com.emergency.repository;

import br.com.emergency.domain.model.CommunityCenter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityCenterRepository extends MongoRepository<CommunityCenter, String> {
}
