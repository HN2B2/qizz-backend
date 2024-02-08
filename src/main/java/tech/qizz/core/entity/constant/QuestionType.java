package tech.qizz.core.entity.constant;

import java.util.Arrays;

public enum QuestionType {
    MULTIPLE_CHOICE,
    FILL_IN_THE_BLANK;

    public static QuestionType validateQuestionType(String type) {
        return Arrays.stream(QuestionType.values())
            .filter(questionType -> questionType.name().equalsIgnoreCase(type)).findFirst()
            .orElse(QuestionType.MULTIPLE_CHOICE);
    }
}
