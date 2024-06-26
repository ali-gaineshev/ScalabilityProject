package execution_service.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import execution_service.requests.InternalUpdateUserStockRequest;
import execution_service.requests.NewStockTransactionRequest;
import execution_service.requests.NewWalletTransactionRequest;
import lombok.*;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderExecutionMessage implements Serializable {
    @JsonProperty("stock_tx_request")
    private NewStockTransactionRequest newStockTransaction;
    @JsonProperty("wallet_tx_request")
    private NewWalletTransactionRequest newWalletTransaction;
    @JsonProperty("add_stock_to_user_request")
    private InternalUpdateUserStockRequest updateUserStockRequest;    
    @JsonProperty("expired")
    private boolean expired;
}
