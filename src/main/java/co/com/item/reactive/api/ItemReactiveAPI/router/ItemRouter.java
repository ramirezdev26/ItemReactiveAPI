package co.com.item.reactive.api.ItemReactiveAPI.router;

import co.com.item.reactive.api.ItemReactiveAPI.domain.dto.ItemDTO;
import co.com.item.reactive.api.ItemReactiveAPI.usecases.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ItemRouter {
    @Bean
    public RouterFunction<ServerResponse> getAllItems(GetAllItemUseCase getAllItemUseCase){
        return route(GET("/items"),
                request -> getAllItemUseCase.get()
                .collectList()
                .flatMap(menuDTOS -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllItemUseCase.get(), ItemDTO.class)))
                .onErrorResume(throwable -> ServerResponse.noContent().build()));

    }

    @Bean
    public RouterFunction<ServerResponse> getItemById(GetItemByIdUseCase getItemByIdUseCase){
        return route(GET("/items/{id}"),
                request -> getItemByIdUseCase.apply(request.pathVariable("id"))
                        .flatMap(itemDTO -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(itemDTO))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> saveItem(SaveItemUseCase saveItemUseCase){
            return route(POST("/items").and(accept(MediaType.APPLICATION_JSON)),
                    request -> request.bodyToMono(ItemDTO.class)
                            .flatMap(itemDTO -> saveItemUseCase.save(itemDTO)
                                    .flatMap(result -> ServerResponse.status(201)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .bodyValue(result))

                                    .onErrorResume(throwable -> ServerResponse.badRequest().bodyValue("Invalid request data: " + throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> updateItem(UpdateItemUseCase updateItemUseCase){
        return route(PUT("/items/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ItemDTO.class)
                        .switchIfEmpty(Mono.error(new Throwable()))
                        .flatMap(itemDTO -> updateItemUseCase.update(request.pathVariable("id"), itemDTO))
                        .flatMap(result -> ServerResponse.status(200)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.badRequest().bodyValue("Invalid request data: " + throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteItem(DeleteItemUseCase deleteItemUseCase){
        return route(DELETE("/items/{id}"),
                request -> deleteItemUseCase.apply(request.pathVariable("id"))
                        .flatMap(string -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue("The item with the fabulous id: " + string + " was deleted"))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }



}
