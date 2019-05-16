package com.ml.bayes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hankcs.hanlp.seg.common.Term;
import com.ml.common.util.HanlpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.Query;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @auther: liuwenyi
 * @date 2019/5/8 13:28
 */
@Slf4j
public class NaiveBayesModel {

    private static Map<String, Integer> keyWordMap;

    private  List<Category> categoryList;

    public  List<Category> getCategoryList() {
        return categoryList;
    }

    static {
        try {
            keyWordMap = extractKeyWord();
        } catch (IOException e) {
            log.error("加载关键词失败，错误原因：{}", e.getMessage());
        }

    }
    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public Double predict(double[] data) {
        Map<Double, Double> rateMap = Maps.newHashMap();
        Integer predictLen = data.length;
        for (Category category : categoryList) {
            Double probability = category.getProbability();
            List<Map<Double, Double>> featureList = category.getFeature();
            Double result = 1.0;
            if (predictLen != featureList.size()) {
                log.error("预测数据特征空间维数：{}与训练数据特征空间维数：{}不一致",
                        predictLen, featureList.size());
                return null;
            }
            for (int i = 0; i < predictLen; i++) {
                Map<Double, Double> map = featureList.get(i);
                double datum = data[i];
                Double value = map.getOrDefault(datum,1.0);
                result = result * value;
            }
            result = result * probability;
            rateMap.put(category.getIndex(), result);
        }
        List<Map.Entry<Double, Double>> list = Lists.newArrayList(rateMap.entrySet());
        list.sort(Comparator.comparing(Map.Entry::getValue));
        return list.get(0).getKey();
    }

    private static List<LabeledPoint> getTrainDataList() throws IOException {
        List<LabeledPoint> list = Lists.newArrayList();
        for (QuestionsEnum question : QuestionsEnum.values()) {
            List<String> lineList = loadFile(question.getFilePath());
            lineList.forEach(line -> {
                double[] arr = sentencesToArrays(line);
                LabeledPoint trainData = new LabeledPoint(question.getIndex(), arr);
                list.add(trainData);
            });
        }
        return list;
    }

    private static List<String> loadFile(String filePath) throws IOException {
        File file = new File(filePath);
        BufferedReader br = new BufferedReader(new FileReader(file));
        List<String> lineList = Lists.newArrayList();
        String line;
        while ((line = br.readLine()) != null) {
            lineList.add(line);
        }
        br.close();
        return lineList;
    }

    /**
     * 量化分词
     *
     * @param sentence
     * @return
     */
    private static double[] sentencesToArrays(String sentence) {
        //创建一个vector数组，默认值为0.0
        double[] vector = new double[keyWordMap.size()];
        List<Term> termList = HanlpUtil.segment.seg(sentence);
        termList.forEach(term -> {
            String word = term.word;
            //分词工具分词后与关键词做匹配，存在将对应的数组值设置为1.0
            if (keyWordMap.containsKey(word)) {
                int index = keyWordMap.get(word);
                vector[index] = 1.0;
            }
        });
        return vector;
    }

    public static double classifier(String sentence) throws IOException {
        NaiveBayesModel model = NaiveBayes.train(getTrainDataList());
        double[] doubles = sentencesToArrays(sentence);
        return model.predict(doubles);

    }
    private static Map<String, Integer> extractKeyWord() throws IOException {
        Map<String, Integer> vocabulary = Maps.newHashMap();
        List<String> list = Lists.newLinkedList();
        for (QuestionsEnum q : QuestionsEnum.values()) {
            List<String> lineList = loadFile(q.getFilePath());
            lineList.forEach(line -> {
                List<Term> termList = HanlpUtil.segment.seg(line);
                termList.forEach(term -> list.add(term.word));
            });
        }
        List<String> keyWordList = list.stream().distinct().collect(Collectors.toList());
        keyWordList.forEach(keyword ->{
            vocabulary.put(keyword, keyWordList.indexOf(keyword));
        } );
        return vocabulary;
    }

    public static void main(String[] args) {
        List<LabeledPoint> labeledPoints = NaiveBayes.labeledPoints();
        NaiveBayesModel train = NaiveBayes.train(labeledPoints);
        double[] d={1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        Double predict = train.predict(d);
        System.out.println(predict);
//        String sentence="苯中毒做什么检查";
//        double classifier = classifier(sentence);
//        String questionPattern = QuestionsEnum.getQuestionPattern(classifier);
//        System.out.println(questionPattern);
    }
}
