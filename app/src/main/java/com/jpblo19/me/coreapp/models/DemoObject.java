package com.jpblo19.me.coreapp.models;

/**
 * CORE 3
 * Created by jpblo19 on 5/16/16.
 * Updated 8/24/16.
 */

public class DemoObject {

    private String title;
    private int value;
    private String description;
    private String fecha_creado;

    public DemoObject(){
        title = "";
        value = 0;
        description = "";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFecha_creado() {
        return fecha_creado;
    }

    public void setFecha_creado(String fecha_creado) {
        this.fecha_creado = fecha_creado;
    }
}
