package com.calulator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Calculator {

    /**
     * 当前计算结果
     */
    private BigDecimal currentNum;

    /**
     * 初始值
     */
    private BigDecimal initialNum;

    /**
     * 每一步计算结果
     */
    private List<BigDecimal> resultList;

    /**
     * 操作符记录
     */
    private List<Operate> operateList;

    /**
     * 操作数记录
     */
    private List<BigDecimal> numList;

    /**
     * 最后一步操作指针
     */
    private int lastIndex;

    public Calculator() {
        this.initialNum = BigDecimal.ZERO;
        this.currentNum = BigDecimal.ZERO;
        this.resultList = new ArrayList<>();
        this.operateList = new ArrayList<>();
        this.numList = new ArrayList<>();
        this.lastIndex = 0;
    }

    public Calculator(BigDecimal initialNum) {
        this.initialNum = initialNum;
        this.currentNum = initialNum;
        this.resultList = new ArrayList<>();
        this.operateList = new ArrayList<>();
        this.numList = new ArrayList<>();
        this.lastIndex = 0;
    }

    public Calculator(int initialNum) {
        this.initialNum = new BigDecimal(initialNum);
        this.currentNum = new BigDecimal(initialNum);
        this.resultList = new ArrayList<>();
        this.operateList = new ArrayList<>();
        this.numList = new ArrayList<>();
        this.lastIndex = 0;
    }

    public Calculator add(BigDecimal num) {
        return this.calHandler(num, Operate.ADD);
    }

    public Calculator add(int num) {
        return add(new BigDecimal(num));
    }

    public Calculator subtract(BigDecimal num) {
        return this.calHandler(num, Operate.SUB);
    }

    public Calculator subtract(int num) {
        return subtract(new BigDecimal(num));
    }

    public Calculator multiply(BigDecimal num) {
        return this.calHandler(num, Operate.MULTIPLY);
    }

    public Calculator multiply(int num) {
        return multiply(new BigDecimal(num));
    }

    public Calculator divide(BigDecimal num) {
        return this.calHandler(num, Operate.DIVIDE);
    }

    public Calculator divide(int num) {
        return divide(new BigDecimal(num));
    }

    public Calculator init(BigDecimal initialNum) {
        this.initialNum = initialNum;
        this.currentNum = initialNum;
        this.resultList = new ArrayList<>();
        this.operateList = new ArrayList<>();
        this.numList = new ArrayList<>();
        this.lastIndex = 0;
        return this;
    }

    /**
     * 初始化一个值，重头计算
     * @param num 初始值
     * @return
     */
    public Calculator init(int num) {
        return init(new BigDecimal(num));
    }

    public Calculator undo() {
        if (resultList.size() > 0 && lastIndex > 1) {
            lastIndex--;
            this.currentNum = resultList.get(lastIndex - 1);
        }
        return seek(Operate.UNDO);
    }

    public Calculator redo() {
        if (lastIndex < (resultList.size())) {
            lastIndex++;
            this.currentNum = resultList.get(lastIndex - 1);
        }
        return seek(Operate.REDO);
    }

    /**
     * 输出计算过程
     *
     * @param operate 操作类型
     * @return
     */
    public Calculator seek(Operate operate) {
        StringBuffer exp = new StringBuffer();
        exp.append(operate.getDes()).append("：");
        if (lastIndex > 0) {
            for (int i = 0; i < lastIndex; i++) {
                String opVal = numList.get(i).toString();
                String operateVal = operateList.get(i).getOperate();
                if (i == 0) {
                    exp.append(this.initialNum);
                }
                exp.append(operateVal).append(opVal);

                if (i == lastIndex - 1) {
                    exp.append("=").append(this.currentNum);
                }
            }
        } else {
            exp.append("0");
        }

        System.out.println(exp);
        return this;
    }

    /**
     * 清空操作
     *
     * @return
     */
    public Calculator clear() {
        this.currentNum = BigDecimal.ZERO;
        this.initialNum = BigDecimal.ZERO;
        this.resultList = new ArrayList<>();
        this.operateList = new ArrayList<>();
        this.numList = new ArrayList<>();
        this.lastIndex = 0;
        return seek(Operate.CLEAR);
    }

    /**
     * 加减乘除运算
     *
     * @param num     参与计算值
     * @param operate 操作类型
     * @return
     */
    private Calculator calHandler(BigDecimal num, Operate operate) {
        switch (operate) {
            case ADD:
                this.currentNum = this.currentNum.add(num);
                break;
            case SUB:
                this.currentNum = this.currentNum.subtract(num);
                break;
            case MULTIPLY:
                this.currentNum = this.currentNum.multiply(num);
                break;
            case DIVIDE:
                this.currentNum = this.currentNum.divide(num, 2, RoundingMode.CEILING);
                break;
            default:
                throw new IllegalArgumentException("非法参数");
        }

        BigDecimal res = this.currentNum;

        //之前做过回滚操作
        if (lastIndex < resultList.size()) {
            lastIndex++;
            resultList.set(lastIndex - 1, res);
            operateList.set(lastIndex - 1, operate);
            numList.set(lastIndex - 1, num);
        } else {
            lastIndex++;
            resultList.add(res);
            operateList.add(operate);
            numList.add(num);
        }
        return seek(operate);
    }

    public static void main(String[] args) {
        Calculator cal = new Calculator(5);
        cal.add(10).subtract(2).undo().add(999).redo().undo().multiply(3).divide(4).undo().redo().undo().clear().init(6).add(7).subtract(1);
    }
}
