package com.jpblo19.me.coreapp.models;

/**
 * CORE 3
 * Created by jpblo19 on 5/16/16.
 * Updated 8/24/16.
 */

public class HttpResponseObject {
    private boolean success;
    private String info;
    private String command;
    private String data;

    public HttpResponseObject(){
        success = false;
        info = "N/A";
        command = "N/A";
        data = "N/A";
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
