package co.com.item.reactive.api.ItemReactiveAPI.consumer;

import co.com.item.reactive.api.ItemReactiveAPI.config.RabbitConfig;
import co.com.item.reactive.api.ItemReactiveAPI.usecases.AddItemUseCase;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ItemConsumer {

    private final AddItemUseCase addItemUseCase;

    @RabbitListener(queues = RabbitConfig.MENU_QUEUE)
    public void receiveMenuEvent(MenuEvent message) {
        addItemUseCase.apply(message.getItemDTO()).block();
    }

}
