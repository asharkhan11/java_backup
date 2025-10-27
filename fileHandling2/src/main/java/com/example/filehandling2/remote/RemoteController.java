package com.example.filehandling2.remote;

import com.example.filehandling2.exception.MyError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/remote")
public class RemoteController {

    @GetMapping("/name")
    public String testing(@RequestParam String name) throws InterruptedException {

        if (name.isBlank()) {
            throw new MyError("name cannot be empty");
        }

        Thread.sleep(2000);

        return "hello, " + name;
    }

}
