package com.ml;


import lombok.Data;

/**
 * @author liuwenyi
 * @date 2019/5/8
 */
@Data
public class LabeledPoint {

    /**
     * 分类标签
     */
    private Integer label;

    /**
     *
     */
    private double[] data;

    public LabeledPoint() {

    }
    public LabeledPoint(Integer label, double[] data) {
        this.label = label;
        this.data = data;
    }

}
