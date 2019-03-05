package com.shpp.p2p.cs.adobrovolskyi.assignment11;

import com.shpp.p2p.cs.adobrovolskyi.assignment16.MyArrayList;

import java.util.ArrayList;

import static com.shpp.p2p.cs.adobrovolskyi.assignment11.ArithmeticOperatorsExt.*;

/**
 * creation of reverse Polish notation. Artem Dobrovolskyi @2018
 */
class CreateRPNExt {

    /**
     * This method parses the string entered by the user to create reverse Polish notation from it.
     *
     * @param line the string entered by the user
     * @return return value
     * @throws Exception error output
     */
    static StringBuilder RPN(String line) throws Exception {
        if (line.length() == 0 || line.replaceAll(" ", "").length() == 0) {
            throw new Exception("Пришла пустая строка для вычисления");
        }

        StringBuilder outLine = new StringBuilder();
        StringBuilder stack = new StringBuilder();

        char symbol, tmp;
        int i = 0;

        if (line.charAt(0) == '-') {                  /* check for a negative number at the beginning of the equation */
            outLine.append(line.charAt(0));
            i = 1;
        }
        for (; i < line.length(); i++) {
            symbol = line.charAt(i);
            if (Operators(symbol)) {                                                                /* operator check */
                if (Operators(symbol) && Operators(line.charAt(i - 1)) || line.charAt(i - 1) == '(') {
                    outLine.append(symbol);
                    continue;
                }
                while (stack.length() > 0) {
                    tmp = stack.charAt(stack.length() - 1);
                    if (tmp == '(') {
                        break;
                    }
                    if (Priority(symbol) <= Priority(tmp)) {                       /* checking operators for priority */
                        outLine.append(" ").append(tmp).append(" ");
                        stack = new StringBuilder(stack.substring(0, stack.length() - 1));
                    } else {
                        outLine.append(" ");
                        break;
                    }
                }
                outLine.append(" ");
                stack.append(symbol);
            } else if ('(' == symbol) {                                            /* brace check and adding to stack */
                stack.append(symbol);
            } else if (')' == symbol) {                                                                /* brace check */
                tmp = stack.substring(stack.length() - 1).charAt(0);
                try {
                    while ('(' != tmp) {
                        outLine.append(" ").append(tmp).append(" ");
                        stack = new StringBuilder(stack.substring(0, stack.length() - 1));
                        tmp = stack.substring(stack.length() - 1).charAt(0);
                    }
                } catch (Exception e) {
                    throw new Exception("Ошибка разбора скобок. Проверьте правильность выражения.");
                }

                stack = new StringBuilder(stack.substring(0, stack.length() - 1));
            } else if (OperatorsExt(symbol) && i < line.length() - 4) {
                if (symbol == 's' && line.charAt(i + 1) == 'i' && line.charAt(i + 2) == 'n' && line.charAt(i + 3) == '(' ||
                        symbol == 'c' && line.charAt(i + 1) == 'o' && line.charAt(i + 2) == 's' && line.charAt(i + 3) == '(' ||
                        symbol == 't' && line.charAt(i + 1) == 'a' && line.charAt(i + 2) == 'n' && line.charAt(i + 3) == '(' ||
                        symbol == 'a' && line.charAt(i + 1) == 't' && line.charAt(i + 2) == 'a' && line.charAt(i + 3) == 'n' && line.charAt(i + 4) == '(' ||
                        symbol == 'l' && line.charAt(i + 1) == 'o' && line.charAt(i + 2) == 'g' && line.charAt(i + 3) == '(' && line.charAt(i + 4) == '1' && line.charAt(i + 5) == '0' ||
                        symbol == 's' && line.charAt(i + 1) == 'q' && line.charAt(i + 2) == 'r' && line.charAt(i + 3) == 't' && line.charAt(i + 4) == '(') {
                    if (symbol == 's' && line.charAt(i + 1) == 'q') {
                        symbol = 'q';
                    }
                    stack.append(symbol);
                    for (int j = i; j < line.length(); j++) {
                        if (line.charAt(j) == '(') {
                            i = j - 1;
                            break;
                        }
                    }
                } else {
                    throw new Exception("Ошибка разбора скобок. Проверьте правильность выражения.");
                }
            } else {                                                                          /* add number to string */
                outLine.append(symbol);
            }
        }

        while (stack.length() > 0) {                                            /* add remaining characters to string */
            outLine.append(" ").append(stack.substring(stack.length() - 1));
            stack = new StringBuilder(stack.substring(0, stack.length() - 1));
        }

        /* action for the correct calculation of the expression type 2 ^ 2 ^ 3 */
        for (int j = 0; j < outLine.length(); j++) {
            if (outLine.charAt(j) == '^') {
                for (int k = j + 1; k < outLine.length(); k++) {
                    if (outLine.charAt(k) == '^') {
                        char str1 = outLine.charAt(j);
                        char str2 = outLine.charAt(k - 2);
                        outLine.setCharAt(j, str2);
                        outLine.setCharAt(k - 2, str1);
                        j = k;
                        break;
                    }
                }
            }
        }

        System.out.println(outLine);
        return outLine;
    }

    /**
     * method for replacing variables in the equation by numbers
     *
     * @param line      the string entered by the user
     * @param variables variables in the equation
     * @return return value
     * @throws Exception error output
     */
    static StringBuilder CreateLineWithOutVariables(StringBuilder line, MyArrayList<String> variables) throws Exception {
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
