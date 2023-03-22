package co.com.item.reactive.api.ItemReactiveAPI.usecases;

import co.com.item.reactive.api.ItemReactiveAPI.domain.collection.Item;
import co.com.item.reactive.api.ItemReactiveAPI.domain.dto.ItemDTO;
import co.com.item.reactive.api.ItemReactiveAPI.repository.IItemRepository;
import co.com.item.reactive.api.ItemReactiveAPI.usecases.interfaces.UpdateItem;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class UpdateItemUseCase implements UpdateItem {

    private final IItemRepository itemRepository;

    private final ModelMapper mapper;



    @Override
    public Mono<ItemDTO> update(String id, ItemDTO itemDTO) {

        return this.itemRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("The item with the id " + id + " was not found")))
                .flatMap(item -> {
                    itemDTO.setId(item.getId());
                    return itemRepository.save(mapper.map(itemDTO, Item.class));
                })
                .map(item -> mapper.map(item, ItemDTO.class));
    }
}
