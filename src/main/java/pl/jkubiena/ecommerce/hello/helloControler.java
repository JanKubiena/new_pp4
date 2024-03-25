package pl.jkubiena.ecommerce.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloControler {
    @GetMapping("/hello")
    String hello(){
        return "Hello Jan";
    }
}
