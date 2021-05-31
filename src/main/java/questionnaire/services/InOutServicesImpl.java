package questionnaire.services;

import java.io.PrintStream;
import java.util.Scanner;

public class InOutServicesImpl implements InOutServices {

    private final PrintStream printStream = new PrintStream(System.out);

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String message) {
        printStream.println(message);
        printStream.flush();
    }

    @Override
    public int readAnswer(int count) {
        int answer;
        print("Please set correct answer number:");
        while (true) {
            while (!scanner.hasNextInt()) {
                print("Input doesn't correct. Input doesn't a number. Please try again.");
                scanner.next();
            }
            answer = scanner.nextInt();
            if (answer <= 0 || answer > count) {
                print("Input doesn't correct. Please try again.");
            } else {
                print("Answer saved \n ");
                break;
            }
        }
        return answer;

    }
}
