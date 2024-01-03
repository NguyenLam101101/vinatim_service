package com.example.recruitment2;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="domain")
public class Domain{
    @Id
    private ObjectId _id;
    private String name;
}
