package seng468.scalability.matchingEngine.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import seng468.scalability.matchingEngine.MatchingEngineOrdersRepository;
import seng468.scalability.models.Entity.Stock;
import seng468.scalability.models.Entity.StockOrder;

public class config {
    @Bean
    CommandLineRunner commandLineRunner(MatchingEngineOrdersRepository repository)
    {
        Stock st = new Stock("test");
        return args -> {
            StockOrder order = new StockOrder(st,false, StockOrder.OrderType.LIMIT,  150, 20);
        };
    }
}
