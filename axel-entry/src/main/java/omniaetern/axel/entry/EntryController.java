package omniaetern.axel.entry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/hello")
public class EntryController {
    @GetMapping
    public String hello() {
        return LocalDateTime.now().toString()+": Greetings from Axel!\n";
    }
}
