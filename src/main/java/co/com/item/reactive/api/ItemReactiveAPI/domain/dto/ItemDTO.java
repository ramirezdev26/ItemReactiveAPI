package co.com.item.reactive.api.ItemReactiveAPI.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private String id;

    private String category;

    private String description;
    private String name;

    private Integer price;
    private Boolean isAdded = false;

    public ItemDTO(String category, String description, String name, Integer price) {
        this.category = category;
        this.description = description;
        this.name = name;
        this.price = price;
        this.isAdded = false;
    }

    public ItemDTO(String id) {
        this.id = id;
    }
}
