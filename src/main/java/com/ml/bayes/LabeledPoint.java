package com.ml.bayes;


import lombok.Data;

/**
 * @auther: liuwenyi
 * @date 2019/5/8 13:15
 */
@Data
public class LabeledPoint {

    /**
     * 分类标签
     */
    private Double label;

    /**
     *
     */
    private double[] data;

    public LabeledPoint() {

    }
    public LabeledPoint(Double label, double[] data) {
        this.label = label;
        this.data = data;
    }

}
