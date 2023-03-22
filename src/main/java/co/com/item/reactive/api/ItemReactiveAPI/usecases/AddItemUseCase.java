package co.com.item.reactive.api.ItemReactiveAPI.usecases;

import co.com.item.reactive.api.ItemReactiveAPI.domain.dto.ItemDTO;
import co.com.item.reactive.api.ItemReactiveAPI.repository.IItemRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@AllArgsConstructor
@Service
public class AddItemUseCase implements Function<ItemDTO, Mono<ItemDTO>> {

    private final IItemRepository itemRepository;
    private final ModelMapper mapper;

    @Override
    public Mono<ItemDTO> apply(ItemDTO itemDTO) {
        return this.itemRepository
                .findById(itemDTO.getId())
                .switchIfEmpty(Mono.error(new Throwable("The item with the id " + itemDTO.getId() + " was not found")))
                .flatMap(item -> {
                    return itemRepository.save(item.addItem())
                            .map(itemAdded -> mapper.map(itemAdded, ItemDTO.class));
                })
                .onErrorResume(Mono::error);
    }

}
