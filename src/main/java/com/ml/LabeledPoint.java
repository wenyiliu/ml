package com.ml;


import lombok.Builder;
import lombok.Data;

/**
 * @author liuwenyi
 * @date 2019/5/8
 */
@Data
@Builder
public class LabeledPoint {

    /**
     * 分类标签
     */
    private Integer label;

    /**
     *标签对应的数据
     */
    private double[] data;

    public LabeledPoint() {

    }
    public LabeledPoint(Integer label, double[] data) {
        this.label = label;
        this.data = data;
    }

}
