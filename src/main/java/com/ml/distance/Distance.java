package com.ml.distance;

import com.google.common.collect.Maps;
import com.ml.LabeledPoint;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * @author liuwenyi
 * @date 2019/6/16
 */
@Slf4j
public class Distance {

    /**
     * 计算欧式距离
     *
     * @param list   历史数据
     * @param target 目标数据
     * @param b      true 数据归一化 false 数据不归一
     * @return
     */
    private static Map<Integer, Double> euclideanDistance(List<LabeledPoint> list, LabeledPoint target, Boolean b) {
        if (list.isEmpty()) {
            log.error("历史数据为空，跳过计算欧式距离");
            return Collections.emptyMap();
        }
        List<LabeledPoint> labeledPointList;
        if (b) {
            list.add(target);
            List<LabeledPoint> labeledPointListZScore = Normalization.zScore(list);
            for (LabeledPoint labeledPoint : labeledPointListZScore) {
                if (labeledPoint.getLabel().equals(target.getLabel())) {
                    target = labeledPoint;
                }
            }
            labeledPointListZScore.remove(target);
            labeledPointList = labeledPointListZScore;
        } else {
            labeledPointList = list;
        }
        double[] targetData = target.getData();
        Map<Integer, Double> similarityMap = Maps.newHashMap();
        labeledPointList.forEach(labeledPoint -> {
            double[] labeledPointData = labeledPoint.getData();
            Integer label = labeledPoint.getLabel();
            Double similarity;
            if (targetData.length != labeledPointData.length) {
                log.error("目标数据长度为{}，标签为{}的数据长度为{}，长度不同，相似度取0", targetData.length,
                        label, labeledPointData.length);
                similarity = 0.0;
            } else {
                Double d = 0.0;
                for (int i = 0; i < labeledPointData.length; i++) {
                    d += Math.pow(targetData[i] - labeledPointData[i], 2);
                }
                similarity = 1 / (1 + Math.sqrt(d));
            }
            similarityMap.put(label, similarity);
        });
        return similarityMap;
    }
}
