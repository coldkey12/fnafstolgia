package kz.don.lbp2_nostalgia.model.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatCompletionResponse {

    private List<Choice> choices;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Choice{

        private int index;
        private ChatMessage message;

    }
}
