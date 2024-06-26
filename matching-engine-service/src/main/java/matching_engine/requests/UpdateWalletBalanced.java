package matching_engine.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWalletBalanced implements Serializable {
    @JsonProperty("username")
    private String username;
    @JsonProperty("amount")
    private Long amount;
    @JsonProperty("is_debit")
    private Boolean isDebit;
}
