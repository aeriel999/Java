package org.example.dto;

import lombok.Data;
import java.util.Date;
@Data

public class CategoryItemDTO {
    private int id;
    private String name;
    private String image;
    private String description;
    private Date dateCreated;
}
