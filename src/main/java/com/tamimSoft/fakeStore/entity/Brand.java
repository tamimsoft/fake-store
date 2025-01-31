package com.tamimSoft.fakeStore.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document("brands")
public class Brand {
    @Id
    private ObjectId id;
    @NonNull
    @Indexed(unique = true)
    private String name;
    private String description;
    private String imageUrl;
}
