package in.ashar.spring_security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/secured")
public class TestController {


    @PostMapping("/test")
    public String testUrl(){
        return """
                {
                "response": "Success"
                }
                """;
    }

}
