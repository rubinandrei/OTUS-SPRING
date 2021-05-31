package questionnaire.services;

import java.io.PrintStream;
import java.util.Scanner;

public class InOutServicesImpl implements InOutServices{

    private PrintStream printStream = new PrintStream(System.out);

    private Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String message) {
       printStream.println(message);
       printStream.flush();
    }

    @Override
    public int readAnswer(int count) {
        int answer;
        print("Please set correct answer number:");
        while (true){
            while(!scanner.hasNextInt()) {
                print("Input doesn't correct. Inpud doesn't a number. Please try again.");
                scanner.next();
            }
           answer = scanner.nextInt();
           if(answer <= 0 || answer > count){
               print("Input doesn't correct. Please try again.");
           }else{
               print("Set answer");
               break;
           }
        }
           return answer;

    }
}
