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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddItemUseCaseTest {

    @Mock
    IItemRepository repoMock;

    ModelMapper mapper;

    AddItemUseCase addItemUseCase;

    @BeforeEach
    void init(){
        mapper = new ModelMapper();
        addItemUseCase = new AddItemUseCase(repoMock, mapper);
    }

    @Test
    @DisplayName("addItem_successfully")
    void addItem() {
        //Build the scenario you need
        String id = "6413683efa74e77204d881f0";
        Item itemToUpdated = new Item("hot dog", "delicious", "cobato", 10000);
        itemToUpdated.setId(id);
        ItemDTO newItemDTO = new ItemDTO("hot dog", "delicious", "cobato", 10000);
        newItemDTO.setId(id);
        ItemDTO itemDTOAdded = new ItemDTO("hot dog", "delicious", "cobato", 10000);
        itemDTOAdded.setId(id);
        itemDTOAdded.setIsAdded(true);


        when(repoMock.findById(id)).thenReturn(Mono.just(itemToUpdated));
        when(repoMock.save(itemToUpdated)).thenReturn(Mono.just(itemToUpdated.addItem()));



        Mono<ItemDTO> response = addItemUseCase.apply(newItemDTO);

        StepVerifier.create(response)
                .expectNext(itemDTOAdded)
                .verifyComplete();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
        Mockito.verify(repoMock).save(ArgumentMatchers.any(Item.class));
    }

    @Test
    @DisplayName("testAddItemError")
    public void testAddItemError() {
        String id = "6413683efa74e77204d881f0";
        Item itemToUpdated = new Item("hot dog", "delicious", "cobato", 10000);
        itemToUpdated.setId(id);
        ItemDTO newItemDTO = new ItemDTO("hot dog", "delicious", "cobato", 10000);
        newItemDTO.setId(id);

        when(repoMock.findById(id)).thenReturn(Mono.empty());

        Mono<ItemDTO> result = addItemUseCase.apply(newItemDTO);

        StepVerifier.create(result)
                .expectError(Throwable.class)
                .verify();

        Mockito.verify(repoMock).findById(ArgumentMatchers.anyString());
    }

}