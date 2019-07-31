package com.ml.bayes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ml.LabeledPoint;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuwenyi
 * @date 2019/5/8 13:28
 */
@Slf4j
public class NaiveBayes {

    /**
     * Laplace smoothing.In the actual production environment,
     * the dimension of the feature space is generally large.
     * Even if each feature is binary data, there are many possible values
     * in the sample space, while the training data is difficult to meet
     * such a large amount of data, which will result in the phenomenon
     * that the probability of some samples is zero.
     * But "probability zero" and "unobserved" are two completely different concepts,
     * which should not be confused.So we're going to use the Laplace smoothing here to avoid that.
     */
    private static final Double NI = 1.0;

    /**
     * train naive bayes model.The model we trained is a polynomial model,
     * which is mostly used to process discrete data. Therefore,
     * each sample in the training data is an array of the same length.
     * In this process, we need to calculate the probability of each category
     * in the sample space. In addition, we need to calculate the probability
     * of different values of each feature under each category.
     *
     * @param data train data
     * @return
     */
    static NaiveBayesModel train(List<LabeledPoint> data) {
        List<Category> categoryList = Lists.newArrayList();
        int sampleSpaceLen = data.size();
        int featureSpaceLen = data.get(0).getData().length;
        data.stream().collect(Collectors.groupingBy(LabeledPoint::getLabel, Collectors.counting()))
                .forEach((key, value) -> {
                    List<double[]> valueList = data.stream().filter(labeledPoint ->
                            labeledPoint.getLabel().equals(key)).map(LabeledPoint::getData)
                            .collect(Collectors.toList());
                    List<Map<Double, Double>> feature = dimensionalCount(valueList, featureSpaceLen);
                    categoryList.add(Category.builder()
                            .index(key)
                            .probability(value.doubleValue() / sampleSpaceLen)
                            .feature(feature)
                            .build());
                });
        NaiveBayesModel model = new NaiveBayesModel();
        model.setCategoryList(categoryList);
        return model;
    }

    private static List<Map<Double, Double>> dimensionalCount(List<double[]> datas, int len) {
        List<Map<Double, Integer>> countList = Lists.newArrayList();
        for (int i = 0; i < len; i++) {
            Map<Double, Integer> countMap = Maps.newHashMap();
            countMap.put(0.0, 0);
            countMap.put(1.0, 0);
            int finalI = i;
            datas.forEach(d -> {
                double v = d[finalI];
                Integer value = 1;
                if (countMap.get(v) != null) {
                    value += countMap.get(v);
                }
                countMap.put(v, value);
            });
            countList.add(countMap);
        }
        List<Map<Double, Double>> rateList = Lists.newArrayList();
        countList.forEach(map -> {
            Map<Double, Double> rateMap = Maps.newHashMap();
            map.forEach((key, value) -> {
                double rate = (value + NI) / (datas.size() + NI * map.size());
                rateMap.put(key, rate);
            });
            rateList.add(rateMap);
        });
        return rateList;
    }
}
