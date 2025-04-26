package kz.don.lbp2_nostalgia.model.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessage {
    private String role;
    private String content;
}
