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


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveItemUseCaseTest {

    @Mock
    IItemRepository repoMock;
    ModelMapper mapper;
    SaveItemUseCase saveItemUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        saveItemUseCase = new SaveItemUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("saveItem_successfully")
    void saveItem() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Item newItem = new Item("burger", "delicious", "hawaii", 10000);
        newItem.setId(id);
        ItemDTO newItemDTO = new ItemDTO("burger", "delicious", "hawaii", 10000);
        newItemDTO.setId(id);


        when(repoMock.save(newItem)).thenReturn(Mono.just(newItem));


        Mono<ItemDTO> response = saveItemUseCase.save(newItemDTO);

        StepVerifier.create(response)
                .expectNext(newItemDTO)
                .verifyComplete();

        Mockito.verify(repoMock).save(ArgumentMatchers.any(Item.class));
    }

    @Test
    @DisplayName("testSaveItemError")
    public void testSaveItemError() {
        String id = "6413683efa74e77204d881f0";
        Item newItem = new Item("burger", "delicious", "hawaii", 10000);
        newItem.setId(id);
        ItemDTO newItemDTO = new ItemDTO("burger", "delicious", "hawaii", 10000);
        newItemDTO.setId(id);

        when(repoMock.save(newItem)).thenReturn(Mono.empty());

        Mono<ItemDTO> result = saveItemUseCase.save(newItemDTO);

        StepVerifier.create(result)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock).save(ArgumentMatchers.any(Item.class));
    }

}