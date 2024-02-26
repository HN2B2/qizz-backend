package tech.qizz.core.entity.constant;

import java.util.Arrays;

public enum QuizState {
    WAITING, STARTED, ENDED;

    public static QuizState validateQuizState(String state) {
        return Arrays.stream(QuizState.values())
            .filter(quizState -> quizState.name().equalsIgnoreCase(state)).findFirst()
            .orElse(QuizState.WAITING);
    }

    public static QuizState of(String state) {
        return Arrays.stream(QuizState.values())
            .filter(quizState -> quizState.name().equalsIgnoreCase(state)).findFirst()
            .orElse(null);
    }
}
