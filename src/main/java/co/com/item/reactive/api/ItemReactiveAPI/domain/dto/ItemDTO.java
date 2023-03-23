package co.com.item.reactive.api.ItemReactiveAPI.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDTO {

    private String id;

    @NotBlank(message="Empty field error")
    @NotNull(message ="date is required")
    private String category;

    @NotBlank(message="Empty field error")
    @NotNull(message ="date is required")
    private String description;

    @NotBlank(message="Empty field error")
    @NotNull(message ="date is required")
    private String name;

    @NotNull(message ="date is required")
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
