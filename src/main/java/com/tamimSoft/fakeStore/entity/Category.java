package com.tamimSoft.fakeStore.entity;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("categories")
public class Category {
    @Id
    private Object id;
    @NonNull
    @Indexed(unique = true)
    private String name;
    private String description;
    private String imageUrl;

}
