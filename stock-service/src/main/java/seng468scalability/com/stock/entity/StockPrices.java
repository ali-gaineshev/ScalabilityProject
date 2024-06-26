package seng468scalability.com.stock.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class StockPrices {
    String stockName;
    @JsonProperty("stock_id")
    Long stockId;
    @JsonProperty("price")
    Long price;
}
