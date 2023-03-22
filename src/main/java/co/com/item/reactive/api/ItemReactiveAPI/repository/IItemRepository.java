package co.com.item.reactive.api.ItemReactiveAPI.repository;

import co.com.item.reactive.api.ItemReactiveAPI.domain.collection.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IItemRepository extends ReactiveMongoRepository<Item, String> {
}
