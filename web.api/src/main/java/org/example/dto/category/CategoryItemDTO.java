package org.example.dto.category;

import lombok.Data;
import java.util.Date;
@Data

public class CategoryItemDTO {
    private Integer id;
    private String name;
    private String image;
    private String description;
    private String dateCreated;
}
