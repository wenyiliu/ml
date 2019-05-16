package com.ml.test;

import com.ml.bayes.NaiveBayesModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @auther: liuwenyi
 * @date 2019/5/16 14:20
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class NaiveBayesModelTest {

    @Test
    public void classifier() throws IOException {
        double classifier = NaiveBayesModel.classifier("鼾症该吃什么药");
        System.out.println(classifier);
    }
}
