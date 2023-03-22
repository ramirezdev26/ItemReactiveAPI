package co.com.item.reactive.api.ItemReactiveAPI.usecases;

import co.com.item.reactive.api.ItemReactiveAPI.domain.dto.ItemDTO;
import co.com.item.reactive.api.ItemReactiveAPI.repository.IItemRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;


@Service
@AllArgsConstructor
public class GetItemByIdUseCase implements Function<String, Mono<ItemDTO>> {
    private final IItemRepository itemRepository;

    private final ModelMapper mapper;
    @Override
    public Mono<ItemDTO> apply(String id) {
        return this.itemRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable()))
                .map(menu-> mapper.map(menu, ItemDTO.class));
    }
}
