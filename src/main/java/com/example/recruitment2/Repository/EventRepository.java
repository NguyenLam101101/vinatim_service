package com.example.recruitment2.Repository;

import com.example.recruitment2.Entity.Event;
import com.example.recruitment2.Entity.Organization;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    public List<Event> findEventsByOrganizationId(String organizationId);
}
