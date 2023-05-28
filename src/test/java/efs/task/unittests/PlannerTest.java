package efs.task.unittests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlannerTest {
    private Planner planner = new Planner();

    @BeforeEach
    void setUp() {
        planner = new Planner();
    }

    @ParameterizedTest
    @EnumSource(ActivityLevel.class)
    void calculateDailyCaloriesDemand_shouldReturnCorrectCaloriesDemand(ActivityLevel activityLevel) {
        // given
        double expectedCaloriesDemand = TestConstants.CALORIES_ON_ACTIVITY_LEVEL.get(activityLevel);

        // when
        double actualCaloriesDemand = planner.calculateDailyCaloriesDemand(TestConstants.TEST_USER, activityLevel);

        // then
        assertEquals(expectedCaloriesDemand, actualCaloriesDemand, 0.01);
    }

    @Test
    void calculateDailyIntake_shouldReturnCorrectDailyIntake() {
        // given
        User expectedDailyIntake = TestConstants.TEST_USER;
        DailyIntake dailyIntake = TestConstants.TEST_USER_DAILY_INTAKE;

        // when
        DailyIntake calculatedDailyIntake = planner.calculateDailyIntake(expectedDailyIntake);

        // then
        Assertions.assertEquals(dailyIntake.getCalories(), calculatedDailyIntake.getCalories());
        Assertions.assertEquals(dailyIntake.getProtein(), calculatedDailyIntake.getProtein());
        Assertions.assertEquals(dailyIntake.getFat(), calculatedDailyIntake.getFat());
        Assertions.assertEquals(dailyIntake.getCarbohydrate(), calculatedDailyIntake.getCarbohydrate());
    }
}
