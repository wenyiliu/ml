package com.ml.densiontree.id3;
import com.ml.LabeledPoint;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author liuwenyi
 * @date 2019/5/20 14:14
 */
@Slf4j
public class IDThreeTree {

    private static double log(double value) {
        return Math.log(value) / Math.log(2.0);
    }

    public static void train(List<LabeledPoint> list){
        if (list.isEmpty()){
            log.error("训练数据不能为空");
            return;
        }
        Map<Integer, Long> map = list.stream().collect(Collectors.groupingBy(LabeledPoint::getLabel,
                Collectors.counting()));

        list.forEach(labeledPoint -> {

        });
    }
}
