package com.example.recruitment2.Repository;

import com.example.recruitment2.Entity.OutTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OutTransactionRepository extends MongoRepository<OutTransaction, String> {
    public int countAllByEventId(String eventId);
    public List<OutTransaction> findByEventId(String eventId, Pageable pageable);
}
