package com.wallet.models.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewWalletTransactionRequest {
    private String username;
    private Long stockTXId;
    private boolean isDebit;
    private Long amount;
}
