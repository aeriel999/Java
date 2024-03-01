package org.example.dto.category;

import lombok.Data;

import java.util.List;

@Data
public class CategorySearchResultDTO {
    private List<CategoryItemDTO> list;
    private int totalCount;
}
