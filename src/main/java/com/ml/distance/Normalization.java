package com.ml.distance;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ml.LabeledPoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author liuwenyi
 * @date 2019/6/16
 */
@Slf4j
public class Normalization {

    /**
     * Z-score将两组或多组数据转换为统一的Z-score，
     * 统一数据标准，提高数据可比性，
     * 削弱了数据解释能力。
     * @param dataList
     * @return List
     */
    public static List<LabeledPoint> zScore(List<LabeledPoint> dataList) {
        if (dataList.isEmpty()) {
            log.error("归一化数据为空");
            return Collections.emptyList();
        }
        List<LabeledPoint> labeledPointList = Lists.newArrayList();
        dataList.forEach(labeledPoint -> {
            Integer maxLen = getMaxLen(dataList);
            double[] dataNew=new double[maxLen];
            for (int i = 0; i < maxLen; i++) {
                double[] dCol = getColumnData(dataList, i);
                Map<String, Double> meanStandardDeviationMap = getMeanStandardDeviation(dCol);
                Double mean = meanStandardDeviationMap.get("mean");
                Double deviation = meanStandardDeviationMap.get("deviation");
                double[] data = labeledPoint.getData();
                if (labeledPoint.getData().length<i){
                    log.info("标签为{}的数据长度为{}，小于{}，跳过zScore归一化",labeledPoint.getLabel(),data.length,i);
                }else {
                    dataNew[i]=(data[i]-mean)/deviation;
                }
            }
            LabeledPoint labeledPointNew=new LabeledPoint();
            labeledPointNew.setLabel(labeledPoint.getLabel());
            labeledPointNew.setData(dataNew);
            labeledPointList.add(labeledPointNew);
        });
        return labeledPointList;
    }

    /**
     * (x-min)/(max-min)
     *
     * @param dataList
     * @return List
     */
    public static List<LabeledPoint> minMax(List<LabeledPoint> dataList) {
        if (dataList.isEmpty()) {
            log.error("归一化数据为空");
            return Collections.emptyList();
        }
        List<LabeledPoint> minMaxList = Lists.newArrayList();
        Integer maxLen = getMaxLen(dataList);
        dataList.forEach(labeledPoint -> {
            double[] dataNew = new double[maxLen];
            for (int i = 0; i < maxLen; i++) {
                double[] dCol = getColumnData(dataList, i);
                Map<String, Double> minMaxMap = getMinMax(dCol);
                Double min = minMaxMap.get("min");
                Double max = minMaxMap.get("max");
                if (min.equals(max)) {
                    log.error("最大值={}，最小值={}，跳过min-max归一化", max, min);
                } else if (labeledPoint.getData().length >= i) {
                    dataNew[i] = (labeledPoint.getData()[i] - min) / (max - min);
                }
            }
            LabeledPoint labeledPointNew = new LabeledPoint();
            labeledPointNew.setLabel(labeledPoint.getLabel());
            labeledPointNew.setData(dataNew);
            minMaxList.add(labeledPointNew);
        });
        return minMaxList;
    }

    /**
     * 获取某一列
     *
     * @param list
     * @param column
     * @return
     */
    private static double[] getColumnData(List<LabeledPoint> list, int column) {
        double[] col = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            double[] data = list.get(i).getData();
            if (column <= data.length) {
                col[i] = data[column];
            } else {
                log.info("标签为{}的数据长度为{}，小于column={}，此处去默认0", list.get(i).getLabel(), data.length, column);
                col[i] = 0.0;
            }
        }
        return col;
    }

    private static Map<String, Double> getMeanStandardDeviation(double[] dData) {
        Mean mean = new Mean();
        StandardDeviation standardDeviation = new StandardDeviation();
        Map<String, Double> meanDeviationMap = Maps.newHashMap();
        meanDeviationMap.put("mean", mean.evaluate(dData));
        double deviation = standardDeviation.evaluate(dData);
        if (deviation == 0) {
            log.info("标准差为零，取默认值1");
            deviation = 1;
        }
        meanDeviationMap.put("deviation", deviation);
        return meanDeviationMap;
    }

    private static Integer getMaxLen(List<LabeledPoint> dataList) {
        return dataList.stream().map(labeledPoint -> labeledPoint.getData().length)
                .max(Integer::compareTo).get();
    }

    private static Map<String, Double> getMinMax(double[] d) {
        double temp;
        int len = d.length;
        for (int i = 0; i < len - 1; i++) {
            for (int j = 0; j < len - 1 - i; j++) {
                if (d[j] > d[j + 1]) {
                    temp = d[j];
                    d[j] = d[j + 1];
                    d[j + 1] = temp;
                }
            }
        }
        Map<String, Double> minMaxMap = Maps.newHashMap();
        minMaxMap.put("min", d[0]);
        minMaxMap.put("max", d[len - 1]);
        return minMaxMap;
    }

    public static void main(String[] args) {
        List<LabeledPoint> labeledPointList = Lists.newArrayList();
        LabeledPoint labeledPoint1=new LabeledPoint();
        labeledPoint1.setLabel(1);
        double[] d1={1,2,3,4};
        labeledPoint1.setData(d1);
        labeledPointList.add(labeledPoint1);

        LabeledPoint labeledPoint2=new LabeledPoint();
        labeledPoint2.setLabel(2);
        double[] d2={2,1,4,1};
        labeledPoint2.setData(d2);
        labeledPointList.add(labeledPoint2);

        LabeledPoint labeledPoint3=new LabeledPoint();
        labeledPoint3.setLabel(3);
        double[] d3={1,3,2,1};
        labeledPoint3.setData(d3);
        labeledPointList.add(labeledPoint3);

        List<LabeledPoint> resultList = minMax(labeledPointList);
        resultList.forEach(labeledPoint -> {
            System.out.println(labeledPoint.getLabel()+"==="+ Arrays.toString(labeledPoint.getData()));
        });
    }
}
