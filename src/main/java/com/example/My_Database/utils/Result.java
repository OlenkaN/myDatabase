package com.example.My_Database.utils;

enum Status {
    SUCCESS,
    FAILURE,
}


public class Result {
    Status status;
    String msg;

    private Result(Status status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public boolean isSuccessful() {
        return this.status == Status.SUCCESS;
    }

    public String getMsg() {
        return this.msg;
    }

    public static Result Success() {
        return new Result(Status.SUCCESS, "OK");
    }

    public static Result Fail(String message) {
        return new Result(Status.FAILURE, message);
    }

}
