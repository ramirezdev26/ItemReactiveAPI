package co.com.item.reactive.api.ItemReactiveAPI.usecases;

import co.com.item.reactive.api.ItemReactiveAPI.domain.collection.Item;
import co.com.item.reactive.api.ItemReactiveAPI.repository.IItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteItemUseCaseTest {

    @Mock
    IItemRepository repoMock;

    @InjectMocks
    DeleteItemUseCase deleteItemUseCase;

    @Test
    @DisplayName("deleteItemTest_Success")
    void deleteItem() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Item newItem = new Item("burger", "delicious", "hawaii", 10000);
        newItem.setId(id);
        var monoItem = Mono.just(newItem);

        when(repoMock.findById(id)).thenReturn(monoItem);
        when(repoMock.delete(newItem)).thenReturn(Mono.empty());


        Mono<String> response = deleteItemUseCase.apply(id);

        StepVerifier.create(response)
                .expectNext("6413683efa74e77204d881f0")
                .verifyComplete();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock).delete(ArgumentMatchers.any(Item.class));

    }

    @Test
    @DisplayName("testDeleteItemNotFound")
    public void testDeleteItemNotFound() {
        String id = "6413683efa74e77204d881f0";
        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<String> result = deleteItemUseCase.apply(id);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock, never()).delete(ArgumentMatchers.any(Item.class));
    }

}