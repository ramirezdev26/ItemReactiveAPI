package co.com.item.reactive.api.ItemReactiveAPI.consumer;

import co.com.item.reactive.api.ItemReactiveAPI.domain.dto.ItemDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuEvent {

    private ItemDTO itemDTO;
    private String eventType;

}
