package questionnaire.services;

import org.springframework.stereotype.Component;

import java.io.PrintStream;
import java.util.Scanner;

@Component
public class IOServiceImpl implements IOService {

    private PrintStream out;
    private Scanner scanner;

    public IOServiceImpl(IOStreamFactory streamFactory) {
        this.out = streamFactory.getPrintStream();
        this.scanner = new Scanner(streamFactory.getInputStream());
    }


    @Override
    public void printString(String message) {
        out.println(message);
    }

    @Override
    public String[] readUser() {
        String[] str;
        while (true) {
            out.println("Please write your name and last name with spase");
            str = scanner.nextLine().split(" ");
            if (str.length > 1) {
                break;
            }
        }
        return str;
    }

    @Override
    public int readQuestionAnswer(int count) {
        int answer;
        out.println("Please set correct answer number:");
        while (true) {
            while (!scanner.hasNextInt()) {
                out.println("Input doesn't correct. Input doesn't a number. Please try again.");
                scanner.next();
            }
            answer = scanner.nextInt();
            if (answer <= 0 || answer > count) {
                out.println("Input doesn't correct. Please try again.");
            } else {
                out.println("Answer saved \n ");
                break;
            }
        }
        return answer;
    }
}