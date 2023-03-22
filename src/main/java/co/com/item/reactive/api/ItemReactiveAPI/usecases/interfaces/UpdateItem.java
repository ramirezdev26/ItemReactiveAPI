package co.com.item.reactive.api.ItemReactiveAPI.usecases.interfaces;

import co.com.item.reactive.api.ItemReactiveAPI.domain.dto.ItemDTO;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface UpdateItem {

    Mono<ItemDTO> update(String id, ItemDTO itemDTO);

}
