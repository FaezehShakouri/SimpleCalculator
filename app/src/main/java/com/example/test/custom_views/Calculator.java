package com.example.test.custom_views;

public class Calculator {
    double oldNumber;
    boolean newOperation;
    String history;
    int isEqual;
    int duplicatedOp;

    public Calculator() {
        oldNumber = 0.0;
        newOperation = true;
        history = "";
        isEqual = 0;
        duplicatedOp = 0;
    }

    public void setOldNumber(double oldNumber) {
        this.oldNumber = oldNumber;
    }

    public double getOldNumber() {
        return oldNumber;
    }

    public void setNewOperation(boolean newOperation) {
        this.newOperation = newOperation;
    }

    public boolean getNewOperation() {
        return this.newOperation;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String newHistory() { return this.history + Double.toString(this.oldNumber); }

    public void setEqual() {
        isEqual = 0;
    }

    public int getEqual() {
        return isEqual;
    }

    public void addEqual() {
        isEqual++;
    }

    public void setDuplicatedOp() { this.duplicatedOp = 0; }

    public int getDuplicatedOp() { return duplicatedOp; }

    public void addDuplicatedOp() { this.duplicatedOp++; }

    /* Calculate infix expression */
    public static double calculate(String data) {
        // Split the main expression
        String[] expr = data.split(" ");

        int i = 0;
        double operLeft = Double.valueOf(expr[i++]);

        while (i < expr.length) {
            String operator = expr[i++];
            double operRight = Double.valueOf(expr[i++]);

            switch (operator) {
                case "*":
                    operLeft *= operRight;
                    break;

                case "/":
                    operLeft /= operRight;
                    break;

                case "+":
                case "-":
                    while (i < expr.length) {
                        String operator2 = expr[i++];
                        if (operator2.equals("+") || operator2.equals("-")) {
                            i--;
                            break;
                        }
                        if (operator2.equals("*")) {
                            operRight = operRight * Double.valueOf(expr[i++]);
                        }
                        if (operator2.equals("/")) {
                            operRight = operRight / Double.valueOf(expr[i++]);
                        }
                    }
                    if (operator.equals("+")) {
                        operLeft += operRight;

                    } else {
                        operLeft -= operRight;
                    }
                    break;
            }
        }
        return operLeft;
    }
}
