package com.questionnare.services;

import com.questionnare.localization.LocalizationServiceImpl;
import org.springframework.stereotype.Component;


import java.io.PrintStream;
import java.util.Scanner;

@Component
public class IOServiceImpl implements IOService {

    private PrintStream out;
    private Scanner scanner;
    private LocalizationServiceImpl loc;

    public IOServiceImpl(IOStreamFactory streamFactory, LocalizationServiceImpl loc) {
        this.out = streamFactory.getPrintStream();
        this.scanner = new Scanner(streamFactory.getInputStream());
    }


    @Override
    public void printString(String message) {
        out.println(message);
    }

    @Override
    public String[] readUser(String message) {
        String[] str;
        while (true) {
            out.println(message);
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
        while (true) {
            while (!scanner.hasNextInt()) {
                out.println(loc.localize("answer.error.number"));
                scanner.next();
            }
            answer = scanner.nextInt();
            if (answer <= 0 || answer > count) {
                out.println(loc.localize("answer.error"));
            } else {
                break;
            }
        }
        return answer;
    }
}
