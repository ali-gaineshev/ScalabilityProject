package com.user.endpoints;

import authentication.UserService;
import com.user.models.entity.User;
import com.user.models.request.NewWalletRequest;
import com.user.models.request.RegisterRequest;
import com.user.models.response.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
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
                System.out.println("The request body contains empty parameters");
                return Response.error("The request body contains empty parameters");
            }
            User user = new User(req.getUser_name(), req.getPassword(), req.getName());
            userService.saveUser(user);


            //waiting for response because if error happened then can't send Response.ok so it's slower, so should look into this.
            Response walletResponse = webClientBuilder.build().post().uri("http://wallet-service/internal/saveNewWallet")
                    .bodyValue(new NewWalletRequest(req.getUser_name())).retrieve().bodyToMono(Response.class).block();
            if (walletResponse == null || !walletResponse.success()) {
                // Retry logic or handle the error | add a circuit breaker
                log.info("Error creating a new wallet (1). " + walletResponse.data());
                return Response.error(walletResponse.data().toString());
            }

            System.out.println("User registered. ");
            return Response.ok(null);
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }

}