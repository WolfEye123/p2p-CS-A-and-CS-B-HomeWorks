package com.shpp.p2p.cs.adobrovolskyi.assignment11;

import com.shpp.p2p.cs.adobrovolskyi.assignment16.MyArrayList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import static com.shpp.p2p.cs.adobrovolskyi.assignment11.ArithmeticOperationsExt.Operations;
import static com.shpp.p2p.cs.adobrovolskyi.assignment11.CreateRPNExt.CreateLineWithOutVariables;
import static com.shpp.p2p.cs.adobrovolskyi.assignment11.CreateRPNExt.RPN;

/**
 * This program is a calculator. Artem Dobrovolskyi @2018
 */
public class CalcExt {

    /**
     * the method in which the main actions occur
     *
     * @param args Arguments taken when starting the program
     * @throws Exception error output
     */
    public static void main(String[] args) throws Exception {
        MyArrayList<String> line = new MyArrayList<>();
        double answer;
        boolean exit = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null){
                line.add(args[i]);
            }
        }
        String str = line.get(0);
        StringBuilder rpnLineB = RPN(str);
        String rpnLine = String.valueOf(rpnLineB);

//        a+b-c/b+c*b+c^b+b*b^b/s a=-1 b=2 c=3
//        java -cp out\production\Projects com.shpp.p2p.cs.adobrovolskyi.assignment11.CalcExt 2"^"2"^"3
//        (w+b-x)/(d+e*f)+g+(h^r^g)+-y w=6 b=10 x=4 d=1 e=1 f=2 g=1 h=2 r=2 g=3 y=1
//        262
//        1+(2+3*(4+5-sin(45*cos(1))))/7
//        5.45595305054
        System.out.println("Выражение для расчета. Поддерживаются цифры, операции +,-,*,/,^,%,sin,cos,tan,atan,log10,sqrt"
                + '\n' + "и приоритеты в виде скобок ( и ):");
        for (String aLine : line) {
            System.out.print(aLine + "  ");
        }

        if (line.size() != 1) {                                              /* if variables are used in the equation */
            System.out.println('\n' + "Строка с переменными");
            StringBuilder rpnLine1 = new StringBuilder();
            rpnLine1.append(rpnLine);
            answer = Operations(CreateLineWithOutVariables(rpnLine1, line));
            System.out.println('\n' + "Ответ:" + '\n' + answer + '\n');
            while (true) {                                    /* cycle to change variables at the request of the user */
                System.out.println("Чтобы изменить переменные не изменяя уравнение нажмите 'Y' или 'y'"
                        + '\n' + "Для выхода 'N' или 'n':");
                Scanner scan = new Scanner(System.in);
                String question = scan.nextLine();
                if (question.equals("Y") || question.equals("y")) {
                    for (int i = line.size() - 1; i > 0; i--) {
                        System.out.println("Ввежите новое число для переменной " + line.get(i).charAt(0));
                        Scanner scan2 = new Scanner(System.in);
                        double newVariableNumber = scan2.nextDouble();
                        String newVariable = line.get(i).substring(0, 2) + newVariableNumber;
                        line.remove(i);
                        line.add(newVariable);
                    }
                    StringBuilder rpnLine2 = new StringBuilder();
                    rpnLine2.append(rpnLine);
                    double answer1 = Operations(CreateLineWithOutVariables(rpnLine2, line));
                    System.out.println('\n' + "Ответ:" + '\n' + answer1 + '\n');
                } else if (question.equals("N") || question.equals("n")) {
                    System.out.println('\n' + "Выход.");
                    exit = true;
                }
                if (exit) {
                    break;
                }
            }
        } else {                                                         /* if variables are not used in the equation */
            answer = Operations(rpnLineB);
            System.out.println('\n' + "Ответ:" + '\n' + answer);
        }
    }
}
