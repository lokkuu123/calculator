package com.calulator;

public enum Operate {

    ADD("add","+","加法"),
    SUB("sub","-","减法"),
    MULTIPLY("multiply","*","乘法"),
    DIVIDE("divide","/","除法"),
    UNDO("undo","undo","后退一步"),
    REDO("redo","redo","前进一步"),
    CLEAR("clear","clear","清空"),
    ;

    private String operateName;

    private String operate;

    private String des;

    Operate(String operateName, String operate, String des) {
        this.operateName = operateName;
        this.operate = operate;
        this.des = des;
    }

    public String getOperateName() {
        return operateName;
    }

    public String getOperate() {
        return operate;
    }

    public String getDes() {
        return des;
    }
}
