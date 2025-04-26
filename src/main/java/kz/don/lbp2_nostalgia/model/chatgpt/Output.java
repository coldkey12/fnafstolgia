package kz.don.lbp2_nostalgia.model.chatgpt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Output {
    private String name;
    private String photoLink;
    private String description;
}
