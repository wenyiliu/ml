package com.ml.bayes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @auther: liuwenyi
 * @date 2019/5/8 13:28
 */
@Slf4j
public class NaiveBayes {

    /**
     * 拉普拉斯平滑
     */
    private static final Double NI = 1.0;

    /**
     * 训练数据
     *
     * @param data
     * @return
     */
    public static NaiveBayesModel train(List<LabeledPoint> data) {
        List<Category> categoryList= Lists.newArrayList();
        int sampleSpaceLen = data.size();
        int featureSpaceLen = data.get(0).getData().length;
        Map<Double, Long> labelCountMap = data.stream().collect(
                Collectors.groupingBy(LabeledPoint::getLabel, Collectors.counting()));
        int sort = labelCountMap.size();
        labelCountMap.forEach((key, value) -> {
            Category category = new Category();
            category.setIndex(key);
            category.setProbability(((value.doubleValue() + NI) / (sampleSpaceLen*NI + sort)));
            List<double[]> collect = data.stream().filter(labeledPoint -> labeledPoint.getLabel().equals(key))
                    .map(LabeledPoint::getData).collect(Collectors.toList());
            List<Map<Double, Integer>> featureCountList = featureCount(collect, featureSpaceLen);
            category.setFeature(featureRate(featureCountList, value));
            categoryList.add(category);
        });
        NaiveBayesModel model=new NaiveBayesModel();
        model.setCategoryList(categoryList);
        return model ;
    }

    /**
     * 统计每个分类下所以特征取值的出现次数
     *
     * @param collect
     * @param len
     * @return
     */
    private static List<Map<Double, Integer>> featureCount(List<double[]> collect, int len) {
        List<Map<Double, Integer>> list = Lists.newArrayList();
        for (int i = 0; i < len; i++) {
            Map<Double, Integer> map = Maps.newHashMap();
            map.put(0.0, 0);
            list.add(map);
        }
        collect.forEach(doubles -> {
            for (int i = 0; i < len; i++) {
                Map<Double, Integer> countMap = list.get(i);
                double quantification = doubles[i];
                if (countMap.get(quantification) == null) {
                    countMap.put(quantification, 1);
                } else {
                    Integer value = countMap.get(quantification);
                    countMap.put(quantification, value + 1);
                }
                list.set(i, countMap);
            }
        });
        return list;
    }

    /**
     * 统计每个类别中每个特征取值所占的比例
     *
     * @param list
     * @param value
     * @return
     */
    private static List<Map<Double, Double>> featureRate(List<Map<Double, Integer>> list, Long value) {
        List<Map<Double, Double>> featureRateList = Lists.newArrayList();
        list.forEach(map -> {
            Map<Double, Double> featureRateMap = Maps.newHashMap();
            int featureValues = map.size();
            map.forEach((key, countValue) -> {
                Double featureRate = (countValue + NI) / (value.doubleValue() + featureValues);
                featureRateMap.put(key, featureRate);
            });
            featureRateList.add(featureRateMap);
        });
        return featureRateList;
    }

    public static List<LabeledPoint> labeledPoints()  {
        List<LabeledPoint> list = Lists.newArrayList();
        File file = new File("data/test.txt");
        BufferedReader reader;
        String line;
        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("\\|");
                int len = split.length;
                double[] d = new double[len - 1];
                for (int i = 1; i < len; i++) {
                    d[i - 1] = Double.valueOf(split[i]);
                }
                Double label = Double.valueOf(split[0].split("\\.")[0]);
                LabeledPoint labeledPoint = new LabeledPoint(label, d);
                list.add(labeledPoint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

}
