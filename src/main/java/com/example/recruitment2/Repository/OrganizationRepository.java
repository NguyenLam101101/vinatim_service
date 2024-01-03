package com.example.recruitment2.Repository;

import com.example.recruitment2.Entity.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrganizationRepository extends MongoRepository<Organization, String> {
//    List<Organization> findOrganizationsByN
}
