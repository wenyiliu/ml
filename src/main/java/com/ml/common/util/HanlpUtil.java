package com.ml.common.util;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;

/**
 * @author liuwenyi
 * @date 2019/5/16 10:41
 */
public class HanlpUtil {

    public static Segment segment;

    /**
     * 初始化Segment
     * @return
     */
    static {
        try {
            segment = HanLP.newSegment();
            //开启自定义词典功能
            segment.enableCustomDictionary(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
