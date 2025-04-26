package kz.don.lbp2_nostalgia.controller;

import jakarta.servlet.http.HttpSession;
import kz.don.lbp2_nostalgia.model.chatgpt.ChatCompletionRequest;
import kz.don.lbp2_nostalgia.model.chatgpt.ChatCompletionResponse;
import kz.don.lbp2_nostalgia.model.chatgpt.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class GoldenFreddyController {

    @Value("${openai.api.url}")
    private String url;

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/speak")
    public String speak(@RequestParam("text") String text, HttpSession session) {

        List<ChatMessage> history = (List<ChatMessage>) session.getAttribute("history");
        if (history == null) {
            history = new ArrayList<>();

            history.add(new ChatMessage("system", "You are Golden Freddy from FNAF 1. Give VERY short plain text answers (max 15 words). The player is the security guard and son of Afton. Act and answer questions according to the lore as if Golden Freddy spoke to the security guard."));
        }

        history.add(new ChatMessage("user", text));

        ChatCompletionRequest chatRequest = new ChatCompletionRequest("gpt-3.5-turbo", history);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<ChatCompletionRequest> entity = new HttpEntity<>(chatRequest, headers);

        ResponseEntity<ChatCompletionResponse> response = restTemplate.postForEntity(url, entity, ChatCompletionResponse.class);

        String reply = response.getBody().getChoices().get(0).getMessage().getContent();

        history.add(new ChatMessage("assistant", reply));

        session.setAttribute("history", history);
        session.setAttribute("response", reply);
        session.setAttribute("messageCount", history.size() / 2);

        if ((int) session.getAttribute("messageCount") >= 6) {
            session.setAttribute("trigger1987", true);
        } else {
            session.setAttribute("trigger1987", false);
        }

        return "game";
    }
}
