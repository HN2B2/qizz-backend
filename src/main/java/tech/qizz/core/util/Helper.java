package tech.qizz.core.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Helper {

    private final String[] adjectives = {"Funny", "Silly", "Goofy", "Wacky", "Whimsical",
        "Zany", "Cheeky",
        "Quirky", "Ludicrous", "Absurd"};
    private final String[] nouns = {"Banana", "Penguin", "Pickle", "Kumquat", "Squid",
        "Toaster", "Noodle",
        "Walrus", "Pancake", "Jellyfish"};

    public String generateUsername() {
        String adjective = adjectives[(int) (Math.random() * adjectives.length)];
        String noun = nouns[(int) (Math.random() * nouns.length)];
        return adjective + " " + noun;
    }
}
