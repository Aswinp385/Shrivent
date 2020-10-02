package com.develop.eventmanagement;

public class SignUpResp {


    /**
     * status : true
     * message : The user has been added successfully.
     * data : 5
     */

    private boolean status;
    private String message;
    private int data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
