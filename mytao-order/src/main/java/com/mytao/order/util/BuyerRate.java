package com.mytao.order.util;

public enum BuyerRate {
    NOT_RATED(0, "未评价"), RATED(1, "已评价");

    private int status;
    private String stateInfo;

    BuyerRate(int status, String stateInfo) {
        this.status = status;
        this.stateInfo = stateInfo;
    }

    public int getStatus() {
        return status;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}

