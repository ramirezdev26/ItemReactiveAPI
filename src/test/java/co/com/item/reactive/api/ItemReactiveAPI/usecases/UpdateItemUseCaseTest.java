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
class UpdateItemUseCaseTest {

    @Mock
    IItemRepository repoMock;
    ModelMapper mapper;
    UpdateItemUseCase updateItemUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        updateItemUseCase = new UpdateItemUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("updateItem_successfully")
    void updateItem() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Item newItem = new Item("burger", "delicious", "hawaii", 10000);
        newItem.setId(id);
        Item itemUpdated = new Item("hot dog", "delicious", "cobato", 10000);
        itemUpdated.setId(id);
        ItemDTO newItemDTO = new ItemDTO("hot dog", "delicious", "cobato", 10000);
        newItemDTO.setId(id);


        when(repoMock.findById(id)).thenReturn(Mono.just(newItem));
        when(repoMock.save(itemUpdated)).thenReturn(Mono.just(itemUpdated));



        Mono<ItemDTO> response = updateItemUseCase.update(id, newItemDTO);

        StepVerifier.create(response)
                .expectNext(newItemDTO)
                .verifyComplete();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock).save(ArgumentMatchers.any(Item.class));
    }

    @Test
    @DisplayName("testUpdateBookError")
    public void testUpdateBookError() {
        String id = "6413683efa74e77204d881f0";
        Item newItem = new Item("burger", "delicious", "hawaii", 10000);
        newItem.setId(id);
        Item itemUpdated = new Item("hot dog", "delicious", "cobato", 10000);
        itemUpdated.setId(id);
        ItemDTO newItemDTO = new ItemDTO("hot dog", "delicious", "cobato", 10000);
        newItemDTO.setId(id);

        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<ItemDTO> result = updateItemUseCase.update(id, newItemDTO);

        StepVerifier.create(result)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
    }

}