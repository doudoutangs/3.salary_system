package com.salary.modules.salary.entity;

/**
 * Created by sugar on 2020/5/5.
 */
public class WorkInfo {
    private int lateNum;//迟到次数
    private int leaveNum;//早退次数
    private int preLeaveNum;//请假次数
    private int overtimeNum;//加班次数

    public int getLateNum() {
        return lateNum;
    }

    public void setLateNum(int lateNum) {
        this.lateNum = lateNum;
    }

    public int getLeaveNum() {
        return leaveNum;
    }

    public void setLeaveNum(int leaveNum) {
        this.leaveNum = leaveNum;
    }

    public int getOvertimeNum() {
        return overtimeNum;
    }

    public void setOvertimeNum(int overtimeNum) {
        this.overtimeNum = overtimeNum;
    }

    public int getPreLeaveNum() {
        return preLeaveNum;
    }

    public void setPreLeaveNum(int preLeaveNum) {
        this.preLeaveNum = preLeaveNum;
    }
}
