package com.shpp.p2p.cs.adobrovolskyi.assignment10;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.lang.Math.pow;

/**
 * class with operators with actions to them. Artem Dobrovolskyi @2018
 */
class ArithmeticOperators {
    /**
     * The function determines whether the current character is an operator, or part of a number.
     */
    static boolean Operators(char c) {
        switch (c) {
            case '-':
            case '+':
            case '*':
            case '/':
            case '^':
            case '%':
                return true;
        }
        return false;
    }

    /**
     * The function determines whether the current character is an operator, or part of a number.
     */
    static boolean OperatorsExt(char c) {
        switch (c) {
            case 's':
            case 'c':
            case 't':
            case 'a':
            case 'l':
            case 'q':
                return true;
        }
        return false;
    }

    /**
     * Returns the priority of the operation
     */
    static byte Priority(char op) {
        switch (op) {
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return 1; // Тут остается + и -
    }

    /**
     * performs an action according to the specified sign
     * @param a specified sign
     * @param dA first number
     * @param dB second number
     * @return return value
     * @throws Exception error output
     */
    static double Calculate(char a, double dA, double dB) throws Exception {
        switch (a) {
            case '+':
                dA += dB;
                break;
            case '-':
                dA -= dB;
                break;
            case '/':
                if (dB == 0) {
                    throw new Exception("Недопустимая операция: деление на ноль");
                }
                dA /= dB;
                break;
            case '*':
                dA *= dB;
                break;
            case '%':
                dA %= dB;
                break;
            case '^':
                if (dA < 0 && dB % 2 == 0) {
                    dA = pow(dA, dB);
                    dA *= -1;
                    break;
                } else if (dA < 0) {
                    double absDA = Math.abs(dA);
                    dA = pow(absDA, dB);
                    dA *= -1;
                    break;
                } else {
                    dA = pow(dA, dB);
                    break;
                }
            default:
                throw new Exception("Недопустимая операция " + a);
        }
        return dA;
    }

    /**
     * performs an action according to the specified sign
     * @param a specified sign
     * @param dA number for action
     * @return return value
     * @throws Exception error output
     */
    static double CalculateExt(char a, double dA) throws Exception {
        switch (a) {
            case 's':
                dA = Math.sin(dA);
                break;
            case 'c':
                dA = Math.cos(dA);
                break;
            case 't':
                dA = Math.tan(dA);
                break;
            case 'a':
                dA = Math.atan(dA);
                break;
            case 'l':
                dA = Math.log10(dA);
                break;
            case 'q':
                dA = Math.sqrt(dA);
                break;
            default:
                throw new Exception("Недопустимая операция " + a);
        }
        return dA;
    }

    /**
     *
     * @param line the string entered by the user
     * @param a regular expression
     * @return return value
     */
    static boolean match(String line, Pattern a) {
        Matcher m = a.matcher(line);
        return m.matches();
    }
}
