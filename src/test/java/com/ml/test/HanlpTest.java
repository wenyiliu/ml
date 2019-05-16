package com.ml.test;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @auther: liuwenyi
 * @date 2019/5/16 14:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class HanlpTest {

    @Test
    public void segmentTest() {
        String sentence = "冠状动脉异常起源主动脉是一个疾病";
        try {
            Segment segment = HanLP.newSegment();
            segment.enableCustomDictionary(true);
            List<Term> termList = segment.seg(sentence);
            termList.forEach(term -> {
                System.out.println(term.word);
                System.out.println(term.nature);
            });
        } catch (Exception e) {
            System.out.println(e.getClass() + "," + e.getMessage());
        }

    }
}
