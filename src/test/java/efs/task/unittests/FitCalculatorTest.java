package efs.task.unittests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;


class FitCalculatorTest {

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        // given
        double weight = 89.2;
        double height = 1.72;

        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertTrue(recommended);
    }

    @Test
    void shouldReturnFalse_whenBMIIncorrect() {
        // given
        double weight = 69.5;
        double height = 1.72;

        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertFalse(recommended);
    }

    @Test
    void shouldThrowException_whenHeightIsZero() {
        // given
        double weight = 80.0;
        double height = 0.0;

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            FitCalculator.isBMICorrect(weight, height);
        });
    }

    @ParameterizedTest(name = "weight={0}")
    @ValueSource(doubles = {92.0, 102.0, 99.2})
    void shouldReturnTrue_forDifferentWeights(double weight) {
        // given
        double height = 1.80;

        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertTrue(recommended);
    }

    @ParameterizedTest(name = "height={0}, weight={1}")
    @CsvSource({
            "1.60, 55.0",
            "1.75, 65.0",
            "1.90, 90.0"
    })
    void shouldReturnFalse_forDifferentHeightAndWeightPairs(double height, double weight) {
        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertFalse(recommended);
    }

    @ParameterizedTest(name = "height={0}, weight={1}")
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void shouldReturnFalse_forDifferentHeightAndWeightPairsFromFile(double height, double weight) {
        // when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        // then
        assertFalse(recommended);
    }

    @Test
    void shouldReturnUserWithWorstBMI_whenUsersListNotEmpty() {
        // given
        List<User> userList = TestConstants.TEST_USERS_LIST;

        double height = 1.79;
        double weight = 97.3;

        // when
        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(userList);

        // then
        assertEquals(height, userWithWorstBMI.getHeight());
        assertEquals(weight, userWithWorstBMI.getWeight());
    }

    @Test
    void shouldReturnNull_whenUsersListEmpty() {
        // given
        List<User> emptyList = Collections.emptyList();

        // when
        User userWithWorstBMI = FitCalculator.findUserWithTheWorstBMI(emptyList);

        // then
        assertNull(userWithWorstBMI);
    }

    @Test
    void shouldReturnCorrectBMIScoreList_whenUsersListNotEmpty() {
        // given
        List<User> userList = TestConstants.TEST_USERS_LIST;
        double[] expectedBMIScoreArray = TestConstants.TEST_USERS_BMI_SCORE;

        // when
        double[] actualBMIScoreArray = FitCalculator.calculateBMIScore(userList);

        // then
        assertArrayEquals(expectedBMIScoreArray, actualBMIScoreArray);
    }
}