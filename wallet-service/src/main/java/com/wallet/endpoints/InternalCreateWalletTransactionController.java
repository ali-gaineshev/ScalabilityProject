package com.wallet.endpoints;

import com.wallet.models.entity.WalletTX;
import com.wallet.models.entity.WalletTXDbSequence;
import com.wallet.models.request.NewWalletTransactionRequest;
import com.wallet.models.response.Response;
import com.wallet.mongo.repository.WalletTXRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@RestController
@RequiredArgsConstructor
public class InternalCreateWalletTransactionController {

    private final WalletTXRepository walletTXRepository;
    private final MongoOperations mongoOperations;

    @PostMapping( "/internal/createWalletTransaction")
    public Response createWalletTransaction(@RequestBody NewWalletTransactionRequest req){
        try{

            WalletTX walletTx = new WalletTX(getSequenceNumber(WalletTX.SEQUENCE_NAME),
                    req.getUsername(), req.getStockTXId(), req.isDebit(), req.getAmount());
            walletTXRepository.save(walletTx);
            return Response.ok(walletTx.getWalletTXId());
        }catch(Exception e){
            return Response.error(e.getMessage());
        }
    }


    public Long getSequenceNumber(String sequenceName){
        //get seq number
        Query query = new Query(Criteria.where("id").is(sequenceName));
        //update seq num
        Update update = new Update().inc("seq", 1);
        //modify in doc
        WalletTXDbSequence counter = mongoOperations.findAndModify(query, update, options().returnNew(true).upsert(true), WalletTXDbSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
