package co.com.item.reactive.api.ItemReactiveAPI.usecases;

import co.com.item.reactive.api.ItemReactiveAPI.domain.collection.Item;
import co.com.item.reactive.api.ItemReactiveAPI.domain.dto.ItemDTO;
import co.com.item.reactive.api.ItemReactiveAPI.repository.IItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetItemByIdUseCaseTest {

    @Mock
    IItemRepository repoMock;
    ModelMapper mapper;
    GetItemByIdUseCase getItemByIdUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        getItemByIdUseCase = new GetItemByIdUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("getItemById_successfully")
    void getItemId() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Item newItem = new Item("burger", "delicious", "hawaii", 10000);
        newItem.setId(id);
        var monoItem = Mono.just(newItem);
        ItemDTO newItemDTO = new ItemDTO("burger", "delicious", "hawaii", 10000);
        newItemDTO.setId(id);


        when(repoMock.findById(id)).thenReturn(monoItem);


        Mono<ItemDTO> response = getItemByIdUseCase.apply(id);

        StepVerifier.create(response)
                .expectNext(newItemDTO)
                .verifyComplete();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());

    }

    @Test
    @DisplayName("testItemNotFound")
    public void testItemNotFound() {
        String id = "6413683efa74e77204d881f0";
        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<ItemDTO> result = getItemByIdUseCase.apply(id);

        StepVerifier.create(result)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
    }

}