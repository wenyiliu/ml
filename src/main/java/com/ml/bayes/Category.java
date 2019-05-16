package com.ml.bayes;


import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @auther: liuwenyi
 * @date 2019/5/15 13:15
 */
@Data
public class Category {

    /**
     * 类别索引
     */
    private Double index;

    /**
     * 该类别在样本空间的概率
     */
    private Double probability;

    /**
     * 特征空间对应的特征的取值的概率
     */
    private List<Map<Double, Double>> feature;
}
