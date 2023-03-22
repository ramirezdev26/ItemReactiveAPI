package co.com.item.reactive.api.ItemReactiveAPI.usecases;

import co.com.item.reactive.api.ItemReactiveAPI.domain.dto.ItemDTO;
import co.com.item.reactive.api.ItemReactiveAPI.repository.IItemRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@Service
@AllArgsConstructor
public class GetAllItemUseCase implements Supplier<Flux<ItemDTO>> {
    private final IItemRepository itemRepository;

    private final ModelMapper mapper;

    @Override
    public Flux<ItemDTO> get() {
        return this.itemRepository
                .findAll()
                .switchIfEmpty(Flux.error(new Throwable()))
                .map(item -> mapper.map(item, ItemDTO.class));
    }
}
