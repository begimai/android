package amantaeva.begimai.calculator;

import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Stack;

public class Calculator {

    //https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
    enum Operation {
        REMAINDER,
        DIVISION,
        MULTIPLICATION,
        ADDITION,
        SUBTRACTION,
        NONE
    }

    //https://docs.oracle.com/javase/7/docs/api/java/util/Stack.html
    private Stack<BigDecimal> operands;
    private Operation operator; //create an instance of the enum Operation

    Calculator() {
        clear();
    } //constructor

    BigDecimal getResult() {
        return operands.peek(); //gets the top of the stack as its the result
    }

    final void clear() { //final here means it can't override, clear
        if (operands == null) {
            operands = new Stack<>();
        }
        operands.clear();
        operands.push(BigDecimal.ZERO);
        operator = Operation.NONE;
    }

    void negate() { //remove it negate it and put it back in stack
        /*BigDecimal temp = operands.pop();
        temp = temp.negate();
        operands.push();
        */
        //shorten form
        operands.push(operands.pop().negate());
    }

    //0 pressing 1 => it must show 1 not 01
    //1 pressing 2 => it must show 12
    //10 pressing 3 => it must show 103
    //-1 pressing 2 => it must show -12
    //-10 pressing 3 => it must show -103

    void addDigit(int digit) { //digits touched on screen from the keypad, are all positive
        if (operands.size() == 1 && operator != Operation.NONE) {
            operands.push(new BigDecimal(String.valueOf(digit)));
        } else {

            BigDecimal value = operands.pop();

            if (value.compareTo(BigDecimal.ZERO) < 0) {
                value = value.multiply(BigDecimal.TEN).subtract(
                        new BigDecimal(String.valueOf(digit))
                );
            } else {
                value = value.multiply(BigDecimal.TEN).add(
                        new BigDecimal(String.valueOf(digit))
                );
            }
            operands.push(value);
        }
    }

    private void remainder() {
        if (operands.size() == 1) {
            operator = Operation.REMAINDER;
        } else {
            BigDecimal secondOperand = operands.pop();
            BigDecimal firstOperand = operands.pop();
            try {
                operands.push(firstOperand.divideAndRemainder(secondOperand)[1]);
            } catch (ArithmeticException e) {
                operands.push(BigDecimal.ZERO);

                throw e;
            }
            operator = Operation.NONE;
        }
    }

    private void divide() {
        if (operands.size() == 1) {
            operator = Operation.DIVISION;
        } else {
            BigDecimal secondOperand = operands.pop();
            BigDecimal firstOperand = operands.pop();

            try {
                operands.push(firstOperand.divide(secondOperand));
            } catch (ArithmeticException e) {
                operands.push(BigDecimal.ZERO)
                throw e;
            }
            operator = Operation.NONE;
        }
    }

    private void multiply() {
        if (operands.size() == 1) {
            operator = Operation.MULTIPLICATION;
        } else {
            BigDecimal secondOperand = operands.pop();
            BigDecimal firstOperand = operands.pop();
            operands.push(firstOperand.multiply(secondOperand));
            //after adding is done we assign None, forgeting this operation right after
            operator = Operation.NONE;
        }
    }

    private void add() {
        if (operands.size() == 1) {
            operator = Operation.ADDITION;
        } else {
            BigDecimal secondOperand = operands.pop();
            BigDecimal firstOperand = operands.pop();
            operands.push(firstOperand.add(secondOperand));
            //after adding is done we assign None, forgeting this operation right after
            operator = Operation.NONE;
        }
    }

    private void subtract() {
        if (operands.size() == 1) {
            operator = Operation.SUBTRACTION;
        } else {
            BigDecimal secondOperand = operands.pop();
            BigDecimal firstOperand = operands.pop();
            operands.push(firstOperand.subtract(secondOperand));
            //after adding is done we assign None, forgeting this operation right after
            operator = Operation.NONE;
        }
    }

    void performBinaryOperation(Operation operation) {
        switch (operation) {
            case REMAINDER:
                remainder();
                break;
            case DIVISION:
                divide();
                break;
            case MULTIPLICATION:
                multiply();
                break;
            case ADDITION:
                add();
                break;
            case SUBTRACTION:
                subtract();
                break;
        }
    }

    public void calculateResult() {
        performBinaryOperation(operator);
    }
}


