package com.shpp.p2p.cs.adobrovolskyi.assignment10;

import java.util.ArrayList;
import java.util.regex.Pattern;
import static com.shpp.p2p.cs.adobrovolskyi.assignment10.ArithmeticOperators.*;

/**
 * the class in which the equation is calculated. Artem Dobrovolskyi @2018
 */
class ArithmeticOperations {

    /* regular expression */
    private static final Pattern NUMBERS = Pattern.compile("^[0.0-9.0]+");
    private static final String pattern = "\\s+|,\\s*";

    /**
     * the method in which the equation is calculated
     * @param line the string entered by the user
     * @return return value
     * @throws Exception error output
     */
    static double Operations(StringBuilder line) throws Exception {
        double dA, dB;
        String line1 = String.valueOf(line);

        String[] operands = line1.split(pattern);
        ArrayList<Double> answer = new ArrayList<>();

        for (String tmp : operands) {
            if (Operators(tmp.charAt(0)) && tmp.length() == 1) {                     /* if this sign is a computation */
                if (answer.size() == 1) {
                    throw new Exception("Недопустимая операция: не корректный ввод данных");
                }
                dA = answer.get(answer.size() - 2);
                answer.remove(answer.size() - 2);
                dB = answer.get(answer.size() - 1);
                answer.remove(answer.size() - 1);
                answer.add(Calculate(tmp.charAt(0), dA, dB));
            }                                                            /* if this number adds it to the calculation */
            else if ((Boolean) match(tmp, NUMBERS) ||
                    tmp.charAt(0) == '-' && (Boolean) match(String.valueOf(tmp.charAt(1)), NUMBERS)) {
                double a = Double.parseDouble(tmp);
                answer.add(a);
            } else if (OperatorsExt(tmp.charAt(0))){           /* if it is a sign like "sin", the calculation is made */
                dB = answer.get(answer.size()-1);
                answer.remove(answer.size() - 1);
                answer.add(CalculateExt(tmp.charAt(0),dB));
            }
        }
        return answer.get(0);
    }
}

