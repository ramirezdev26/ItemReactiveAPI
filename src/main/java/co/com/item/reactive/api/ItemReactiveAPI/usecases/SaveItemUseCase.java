package co.com.item.reactive.api.ItemReactiveAPI.usecases;

import co.com.item.reactive.api.ItemReactiveAPI.domain.collection.Item;
import co.com.item.reactive.api.ItemReactiveAPI.domain.dto.ItemDTO;
import co.com.item.reactive.api.ItemReactiveAPI.repository.IItemRepository;
import co.com.item.reactive.api.ItemReactiveAPI.usecases.interfaces.SaveItem;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class SaveItemUseCase implements SaveItem {

    private final IItemRepository itemRepository;

    private final ModelMapper mapper;

    @Override
    public Mono<ItemDTO> save(ItemDTO menuDTO) {

        return this.itemRepository.save(mapper.map(menuDTO, Item.class))
                .switchIfEmpty(Mono.error(new Throwable("Something went wrong with the request")))
                .map(item -> mapper.map(item, ItemDTO.class));
    }

}
