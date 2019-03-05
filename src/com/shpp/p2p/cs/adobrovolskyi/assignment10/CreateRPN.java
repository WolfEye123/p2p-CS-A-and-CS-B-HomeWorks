package com.shpp.p2p.cs.adobrovolskyi.assignment10;

import java.util.ArrayList;
import static com.shpp.p2p.cs.adobrovolskyi.assignment10.ArithmeticOperators.*;

/**
 * creation of reverse Polish notation. Artem Dobrovolskyi @2018
 */
class CreateRPN {

    /**
     * This method parses the string entered by the user to create reverse Polish notation from it.
     * @param line the string entered by the user
     * @return return value
     * @throws Exception error output
     */
    static StringBuilder RPN(String line) throws Exception {
        if (line.length() == 0 || line.replaceAll(" ", "").length() == 0) {
            throw new Exception("Пришла пустая строка для вычисления");
        }

        StringBuilder OutLine = new StringBuilder();
        StringBuilder Stack = new StringBuilder();

        char symbol, tmp;
        int i = 0;

        if (line.charAt(0) == '-') {                  /* check for a negative number at the beginning of the equation */
            OutLine.append(line.charAt(0));
            i = 1;
        }
        for (; i < line.length(); i++) {
            symbol = line.charAt(i);
            if (Operators(symbol)) {                                                                /* operator check */
                if (Operators(symbol) && Operators(line.charAt(i - 1)) || line.charAt(i - 1) == '(') {
                    OutLine.append(symbol);
                    continue;
                }
                while (Stack.length() > 0) {
                    tmp = Stack.charAt(Stack.length() - 1);
                    if (tmp == '(') {
                        break;
                    }
                    if (Priority(symbol) <= Priority(tmp)) {                       /* checking operators for priority */
                        OutLine.append(" ").append(tmp).append(" ");
                        Stack = new StringBuilder(Stack.substring(0, Stack.length() - 1));
                    } else {
                        OutLine.append(" ");
                        break;
                    }
                }
                OutLine.append(" ");
                Stack.append(symbol);
            } else if ('(' == symbol) {                                            /* brace check and adding to stack */
                Stack.append(symbol);
            } else if (')' == symbol) {                                                                /* brace check */
                tmp = Stack.substring(Stack.length() - 1).charAt(0);
                try {
                    while ('(' != tmp) {
                        OutLine.append(" ").append(tmp).append(" ");
                        Stack = new StringBuilder(Stack.substring(0, Stack.length() - 1));
                        tmp = Stack.substring(Stack.length() - 1).charAt(0);
                    }
                } catch (Exception e) {
                    throw new Exception("Ошибка разбора скобок. Проверьте правильность выражения.");
                }

                Stack = new StringBuilder(Stack.substring(0, Stack.length() - 1));
            } else if (OperatorsExt(symbol)) {
                if (symbol == 's' && line.charAt(i + 1) == 'q') {
                    symbol = 'q';
                }
                Stack.append(symbol);
                for (int j = i; j < line.length(); j++) {
                    if (line.charAt(j) == '(') {
                        i = j - 1;
                        break;
                    }
                }
            } else {                                                                          /* add number to string */
                OutLine.append(symbol);
            }
        }

        while (Stack.length() > 0) {                                            /* add remaining characters to string */
            OutLine.append(" ").append(Stack.substring(Stack.length() - 1));
            Stack = new StringBuilder(Stack.substring(0, Stack.length() - 1));
        }

        /* action for the correct calculation of the expression type 2 ^ 2 ^ 3 */
        for (int j = 0; j < OutLine.length(); j++) {
            if (OutLine.charAt(j) == '^') {
                for (int k = j + 1; k < OutLine.length(); k++) {
                    if (OutLine.charAt(k) == '^') {
                        char str1 = OutLine.charAt(j);
                        char str2 = OutLine.charAt(k - 2);
                        OutLine.setCharAt(j, str2);
                        OutLine.setCharAt(k - 2, str1);
                        j = k;
                        break;
                    }
                }
            }
        }

        System.out.println(OutLine);
        return OutLine;
    }

    /**
     * method for replacing variables in the equation by numbers
     * @param line the string entered by the user
     * @param variables variables in the equation
     * @return return value
     * @throws Exception error output
     */
    static StringBuilder CreateLineWithOutVariables(StringBuilder line, ArrayList<String> variables) throws Exception {
        for (int i = 0, j = 1; j < variables.size(); ) {
            if (variables.get(j).charAt(0) == line.charAt(i)) {
                line = line.replace(i, i + 1, variables.get(j).substring(2));
                j++;
                i = 0;
                continue;
            }
            i++;
        }
        return line;
    }
}
