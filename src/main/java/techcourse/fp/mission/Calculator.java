package techcourse.fp.mission;

import java.util.List;

public class Calculator {
    public static int sumAll(List<Integer> numbers, Conditional conditional) {
        return numbers.stream()
                .filter(x -> conditional.test(x))
                .mapToInt(x -> x)
                .sum();
    }

    public static int sumAllEven(List<Integer> numbers) {
        int total = 0;
        for (int number : numbers) {
            if (number % 2 == 0) {
                total += number;
            }
        }
        return total;
    }

    public static int sumAllOverThree(List<Integer> numbers) {
        int total = 0;
        numbers.stream()
                .filter(number -> number > 3)
                .mapToInt(x -> x)
                .sum();

        return total;
    }
}
