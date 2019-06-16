package com.ml.bayes;

import com.google.common.collect.Lists;
import com.hankcs.hanlp.seg.common.Term;
import com.ml.common.util.HanlpUtil;

import java.util.List;

/**
 * @author liuwenyi
 * @date 2019/5/16 14:53
 */
public class Participle {

    private static final List<String> POS_LIST=Lists.newArrayList("ill","sym");

    public static String analyQuery(String queryString) {
        StringBuilder sb = new StringBuilder();
        List<String> list = Lists.newArrayList();
        List<Term> termList = HanlpUtil.segment.seg(queryString);
        for (Term term : termList) {
            //从term中获取分词
            String word = term.word;
            //从term中获取标签
            String pos = term.nature.toString();
            if (POS_LIST.contains(pos)) {
                //ill 疾病
                list.add(word);
                sb.append(pos+" ");
            } else {
                sb.append(word).append(" ");
            }
        }
        Integer classifier =BayesClassifier.classifier(sb.toString());
        return QuestionsEnum.getQuestionPattern(classifier);
    }
}
