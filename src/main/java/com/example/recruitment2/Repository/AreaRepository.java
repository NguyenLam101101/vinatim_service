package com.example.recruitment2.Repository;

import com.example.recruitment2.Entity.Area;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AreaRepository extends MongoRepository<Area, String> {
    public List<Area> findAreasByProvince(String province);
}
