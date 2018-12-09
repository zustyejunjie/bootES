package com.yejunjie.lambda;

import java.awt.*;
import java.io.Serializable;

public class Apple implements Serializable {
    /** 编号 */
    private Long id;
    /** 颜色 */
    private Color color;
    /** 重量 */
    private Float weight;
    /** 产地 */
    private String origin;

    public Apple() {
    }

    public Apple(Long id, Color color, Float weight, String origin) {
        this.id = id;
        this.color = color;
        this.weight = weight;
        this.origin = origin;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
