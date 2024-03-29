package org.example.dto.category;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CategoryEditDTO {
    private Integer id;
    private String name;
    private MultipartFile file;
    private String description;
}
