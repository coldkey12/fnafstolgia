package kz.don.lbp2_nostalgia.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "main";
    }

    @GetMapping("/ERROR")
    public String ohNoPage() {
        return "game";
    }

    @GetMapping("/1987")
    public String hiddenPage() {
        return "hidden";
    }

    @GetMapping("/lore-page")
    public String lorePage() {
        return "lore";
    }

    @PostMapping("/search-info")
    public String searchInfo(@RequestParam String searchText) {
        return "lore";
    }

}
