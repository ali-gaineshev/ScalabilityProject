package com.user.endpoints;

import com.user.models.request.NewWalletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import authentication.UserService;
import com.user.models.entity.User;
//import com.user.models.entity.Wallet;
import com.user.models.request.RegisterRequest;
import com.user.models.response.Response;
import org.springframework.web.client.RestTemplate;
//import com.user.repositories.WalletRepository;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RegisterController {
    private final UserService userService;

    private final RestTemplate restTemplate;

    @PostMapping(value = "/register", consumes = {"application/json"})
    public Response registerUser(@RequestBody RegisterRequest req) {
        try {
            User user = new User(req.getUser_name(), req.getPassword(), req.getName());
            userService.saveUser(user);
            NewWalletRequest newWalletRequest = new NewWalletRequest(req.getUser_name());
            // Use postForObject to send a POST request to the saveNewWallet endpoint, should change this to webClient
            Response walletResponse = restTemplate.postForObject(
                    "http://localhost:8082/saveNewWallet", newWalletRequest, Response.class
            );
            if(!walletResponse.success()){
                ;//retry?
                System.out.println(walletResponse.data());
            }
            log.info("User {} created", req.getUser_name());
            return Response.ok(null);
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }
}