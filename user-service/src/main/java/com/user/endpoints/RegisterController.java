package com.user.endpoints;

import com.user.request.NewWalletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import authentication.UserService;
import com.user.entity.User;
//import com.user.models.entity.Wallet;
import com.user.request.RegisterRequest;
import com.user.response.Response;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
//import com.user.repositories.WalletRepository;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegisterController {
    private final UserService userService;

    private final WebClient.Builder webClientBuilder;

    @PostMapping(value = "/register")
    public Response registerUser(@RequestBody RegisterRequest req) {
        try {
            if(req.getUser_name().isEmpty() || req.getPassword().isEmpty() || req.getName().isEmpty()){
                return Response.error("The request body contains empty parameters");
            }
            User user = new User(req.getUser_name(), req.getPassword(), req.getName());
            userService.saveUser(user);

            //post request to save new wallet
            /*
            Mono<Response> walletResponseMono = webClientBuilder.build().post().uri("http://wallet-service/saveNewWallet")
                    .bodyValue(new NewWalletRequest(req.getUser_name())).retrieve().bodyToMono(Response.class);
            walletResponseMono.subscribe(walletResponse -> {
                if (walletResponse == null || !walletResponse.success()) {
                    // Retry logic or handle the error
                    log.info("Error creating a new wallet (1). " + walletResponse.data());

                }else{
                    log.info("New wallet is created succesfully for " + req.getUser_name());
                }
            }, error ->  log.info("Error creating a new wallet (2). " + error));
            */

            //waiting for response because if error happened then can't send Response.ok so it's slower, so should look into this.
            Response walletResponse = webClientBuilder.build().post().uri("http://wallet-service/saveNewWallet")
                    .bodyValue(new NewWalletRequest(req.getUser_name())).retrieve().bodyToMono(Response.class).block();
            if (walletResponse == null || !walletResponse.success()) {
                // Retry logic or handle the error
                log.info("Error creating a new wallet (1). " + walletResponse.data());
                return Response.error(walletResponse.data().toString());
            }

            log.info("User {} created", req.getUser_name());
            return Response.ok(null);
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }
}