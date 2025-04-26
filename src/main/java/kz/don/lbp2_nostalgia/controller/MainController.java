package kz.don.lbp2_nostalgia.controller;

import jakarta.servlet.http.HttpSession;
import kz.don.lbp2_nostalgia.model.chatgpt.ChatCompletionRequest;
import kz.don.lbp2_nostalgia.model.chatgpt.ChatCompletionResponse;
import kz.don.lbp2_nostalgia.model.chatgpt.ChatMessage;
import kz.don.lbp2_nostalgia.model.chatgpt.Output;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
public class MainController {

    @org.springframework.beans.factory.annotation.Value("${openai.api.url}")
    private String url;

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

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
    public String searchInfo(@RequestParam("searchText") String searchText, HttpSession session, Model model) {

        List<ChatMessage> history = (List<ChatMessage>) session.getAttribute("history");
        if (history == null) {
            history = new ArrayList<>();

            history.add(new ChatMessage("system", "You are requested to give output in THIS EXACT FORMAT in order for regex to parse it properly, Example of your output: [Bonnie][https://static.wikia.nocookie.net/freddy-fazbears-pizza/images/3/3b/Bonnie_merchandise.jpg/revision/latest?cb=20190526194524][Bonnie is an animatronic rabbit and the guitarist in Freddy's band, positioned at the left side of the stage. Undisclosed to Fazbear Entertainment, Inc. and the public, Bonnie is possessed by the restless spirit of Jeremy – a little boy murdered by William Afton. Due to this, he and the others are now seeking revenge against their killer by attacking any adults in the pizzeria after-hours in blind rage.], just give 3 infos in these square brackets, all info in square brackets only, dont say anything else outside of this requested format, give a working link to a photo so it will work on the frontend display"));
        }

        history.add(new ChatMessage("user", searchText));

        ChatCompletionRequest chatRequest = new ChatCompletionRequest("gpt-3.5-turbo", history);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<ChatCompletionRequest> entity = new HttpEntity<>(chatRequest, headers);

        ResponseEntity<ChatCompletionResponse> response = restTemplate.postForEntity(url, entity, ChatCompletionResponse.class);

        Output output = parseAiResponseToObject(response.getBody(), model);

        model.addAttribute("responseLore", output);
        session.setAttribute("responseLore", output);

//        System.out.println(response.getBody());

        return "lore";
    }

    private Output parseAiResponseToObject(ChatCompletionResponse response, Model model) {
        String regex = "\\[(.*?)\\]\\[(.*?)\\]\\[(.*?)\\]";
        ChatMessage answer = response.getChoices().get(0).getMessage();
        model.addAttribute("answer", answer);
        System.out.println(answer);

        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(answer.getContent().trim());

        Output result = new Output();

        while (matcher.find()) {
            Output extractedData = new Output(
                    matcher.group(1),
                    matcher.group(2),
                    matcher.group(3)
            );
            result = extractedData;
            System.out.println(extractedData.toString());
        }
        return result;
    }

}
