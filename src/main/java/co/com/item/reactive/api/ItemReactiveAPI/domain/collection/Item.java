package co.com.item.reactive.api.ItemReactiveAPI.domain.collection;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "items")
public class Item {
    @Id
    private String id;

    @NotBlank(message="Empty field error")
    @NotNull(message ="category is required")
    private String category;

    @NotBlank(message="Empty field error")
    @NotNull(message ="description is required")
    private String description;

    @NotBlank(message="Empty field error")
    @NotNull(message ="name is required")
    private String name;

    @NotNull(message ="price is required")
    private Integer price;
    private Boolean isAdded = false;



    public Item(String category, String description, String name, Integer price) {
        this.id = UUID.randomUUID().toString().substring(0, 10);
        this.category = category;
        this.description = description;
        this.name = name;
        this.price = price;
        this.isAdded = false;
    }

    public Item addItem(){
        this.isAdded = true;
        return this;
    }

}
