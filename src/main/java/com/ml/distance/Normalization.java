package com.ml.distance;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ml.LabeledPoint;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;

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
     * Z-score converts two or more sets of data into an ununitary z-score,
     * unifies data standards, improves data comparability,
     * and weakens data interpretation.
     * @param labeledPoints
     * @return List
     */
    public static List<LabeledPoint> zScore(List<LabeledPoint> labeledPoints) {
        if (labeledPoints.isEmpty()) {
            log.error("the data normalized list cannot be empty");
            return Collections.emptyList();
        }
        List<LabeledPoint> labeledPointList = Lists.newArrayList();
        Integer max = labeledPoints.stream().map(labeledPoint -> labeledPoint.getData().length)
                .max(Integer::compareTo).get();
        Map<Integer, List<Double>> meanDeviationMap = Maps.newHashMap();
        for (int i = 0; i < max; i++) {
            double[] d = new double[labeledPoints.size()];
            for (int j = 0; j < labeledPoints.size(); j++) {
                double[] data = labeledPoints.get(j).getData();
                if (data.length >= i) {
                    d[j] = labeledPoints.get(j).getData()[i];
                }
            }
            List<Double> meanDeviationList = Lists.newArrayList();
            Mean mean = new Mean();
            double dMean = mean.evaluate(d);
            StandardDeviation standardDeviation = new StandardDeviation();
            double deviation = standardDeviation.evaluate(d);
            if (deviation == 0) {
                log.info("标准差为零，取默认值1");
                deviation = 1;
            }
            meanDeviationList.add(dMean);
            meanDeviationList.add(deviation);
            meanDeviationMap.put(i, meanDeviationList);
        }
        labeledPoints.forEach(data -> {
            LabeledPoint labeledPoint = new LabeledPoint();
            labeledPoint.setLabel(data.getLabel());
            double[] d = data.getData();
            double[] unification = new double[d.length];
            for (int i = 0; i < d.length; i++) {
                List<Double> list = meanDeviationMap.get(i);
                unification[i] = (d[i] - list.get(0)) / list.get(1);
            }
            labeledPoint.setData(unification);
            labeledPointList.add(labeledPoint);
        });
        return labeledPointList;
    }

}
