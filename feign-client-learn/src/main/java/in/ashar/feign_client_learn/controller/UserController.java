package in.ashar.feign_client_learn.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @PostMapping
    public void operation1(){
        System.out.println("operation 1 completed");
    }


    @GetMapping
    public void operation2(){
        System.out.println("operation 2 completed");
    }


}
