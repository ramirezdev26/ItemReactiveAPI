package co.com.item.reactive.api.ItemReactiveAPI.usecases;

import co.com.item.reactive.api.ItemReactiveAPI.repository.IItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class DeleteItemUseCase implements Function<String, Mono<String>> {

    private final IItemRepository itemRepository;

    @Override
    public Mono<String> apply(String id) {
        return itemRepository.findById(id)
                .flatMap(item -> itemRepository.delete(item).thenReturn(id))
                .switchIfEmpty(Mono.error(new RuntimeException(id)));
    }
}
