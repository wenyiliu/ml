package com.ml.bayes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @auther: liuwenyi
 * @date 2019/5/8 13:28
 */
@Slf4j
public class NaiveBayesModel {


    private static List<Category> categoryList;

    public void setCategoryList(List<Category> categoryList) {
        NaiveBayesModel.categoryList = categoryList;
    }

    public Double predict(double[] data) {
        Map<Double, Double> rateMap = Maps.newHashMap();
        Integer predictLen = data.length;
        for (Category category : categoryList) {
            Double probability = category.getProbability();
            List<Map<Double, Double>> featureList = category.getFeature();
            Double result = 1.0;
            if (predictLen != featureList.size()) {
                log.error("预测数据特征空间维数：{}与训练数据特征空间维数：{}不一致",
                        predictLen, featureList.size());
                return null;
            }
            for (int i = 0; i < predictLen; i++) {
                Map<Double, Double> map = featureList.get(i);
                double datum = data[i];
                Double value = map.getOrDefault(datum, (double) (1 / map.size()));
                result = result * value;
            }
            result = result * probability;
            rateMap.put(category.getIndex(), result);
        }
        List<Map.Entry<Double, Double>> list = Lists.newArrayList(rateMap.entrySet());
        list.sort((o1, o2) -> (o2.getValue()).compareTo(o1.getValue()));
        return list.get(0).getKey();
    }

}
