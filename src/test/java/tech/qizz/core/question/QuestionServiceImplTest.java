package tech.qizz.core.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.qizz.core.entity.Question;
import tech.qizz.core.entity.QuizBank;
import tech.qizz.core.entity.constant.QuestionType;
import tech.qizz.core.question.dto.QuestionResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceImplTest
{
    @InjectMocks
    private QuestionServiceImpl questionService;

    @Mock
    private QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetQuestionById_WhenQuestionExists_ReturnQuestionResponse() {
        // Arrange
        Long questionId = 1L;
//        Question mockedQuestion = new Question();
        Question mockedQuestion = new Question(questionId, "content", 10, 10, QuestionType.MULTIPLE_CHOICE, "answer1", "answer2", "answer3", null, null, 1, true, new QuizBank(), new ArrayList<>());

        when(questionRepository.findById(questionId)).thenReturn(Optional.of(mockedQuestion));

        // Act
        QuestionResponse response = questionService.getQuestionById(questionId);

        // Assert
        assertNotNull(response);
        assertEquals(mockedQuestion.getType(), response.getType());
    }
}
