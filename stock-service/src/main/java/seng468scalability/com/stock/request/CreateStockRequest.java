package seng468scalability.com.stock.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateStockRequest {
    private String stockName;


    public CreateStockRequest(@JsonProperty("stock_name") String stockName) {
        this.stockName = stockName;
    }

    public String getStockName() {
        return this.stockName;
    }
}
