package co.com.item.reactive.api.ItemReactiveAPI.usecases;

import co.com.item.reactive.api.ItemReactiveAPI.domain.collection.Item;
import co.com.item.reactive.api.ItemReactiveAPI.repository.IItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GetAllItemUseCaseTest {

    @Mock
    IItemRepository repoMock;

    ModelMapper modelMapper;

    GetAllItemUseCase service;

    @BeforeEach
    void init(){
        modelMapper = new ModelMapper();
        service = new GetAllItemUseCase(repoMock, modelMapper);
    }

    @Test
    @DisplayName("getAllItems")
    void getAllItems() {
        //Build the scenario you need
        var fluxItems = Flux.just(new Item("burger", "delicious", "hawaii", 10000), new Item("bear", "delicious", "hawaii", 10000));

        Mockito.when(repoMock.findAll()).thenReturn(fluxItems);

        var response = service.get();

        StepVerifier.create(response)
                .expectNextCount(2)
                .verifyComplete();

        Mockito.verify(repoMock).findAll();
    }

}