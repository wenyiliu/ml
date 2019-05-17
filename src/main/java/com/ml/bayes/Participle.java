package com.ml.bayes;

import com.google.common.collect.Lists;
import com.hankcs.hanlp.seg.common.Term;
import com.ml.common.util.HanlpUtil;

import java.util.List;

/**
 * @auther: liuwenyi
 * @date 2019/5/16 14:53
 */
public class Participle {

    public static String analyQuery(String queryString) {
        StringBuilder sb = new StringBuilder();
        List<String> list = Lists.newArrayList();
        List<Term> termList = HanlpUtil.segment.seg(queryString);
        for (Term term : termList) {
            String word = term.word;//从term中获取分词
            String pos = term.nature.toString();//从term中获取标签
            if (pos.equals("ill")) { //ill 疾病
                list.add(word);
                sb.append("ill ");
            } else if (pos.equals("sym")) { //sym症状
                list.add(word);
                sb.append("sym ");
            } else {
                sb.append(word).append(" ");
            }
        }
        System.out.println(sb.toString());
        double classifier =BayesClassifier.classifier(sb.toString());
        return QuestionsEnum.getQuestionPattern(classifier);
    }
}
