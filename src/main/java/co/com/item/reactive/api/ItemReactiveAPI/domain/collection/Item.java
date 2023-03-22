package co.com.item.reactive.api.ItemReactiveAPI.domain.collection;

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

    private String category;

    private String description;
    private String name;

    private Integer price;


    public Item(String category, String description, String name, Integer price) {
        this.id = UUID.randomUUID().toString().substring(0, 10);
        this.category = category;
        this.description = description;
        this.name = name;
        this.price = price;
    }
}
