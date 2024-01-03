package com.example.recruitment2.Repository;

import com.example.recruitment2.Entity.Enum.ETransactionStatus;
import com.example.recruitment2.Entity.InTransaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InTransactionRepository extends MongoRepository<InTransaction, String> {
    public int countAllByEventIdAndStatus(String eventId, ETransactionStatus status);
    public int countAllByEventId(String eventId);
    public List<InTransaction> findByEventIdAndStatus(String eventId, ETransactionStatus status, Pageable pageable);
    public List<InTransaction> findByEventId(String eventId, Pageable pageable);

}
