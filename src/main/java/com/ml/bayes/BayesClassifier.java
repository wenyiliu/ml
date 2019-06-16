package com.ml.bayes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hankcs.hanlp.seg.common.Term;
import com.ml.LabeledPoint;
import com.ml.common.util.HanlpUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liuwenyi
 * @date 2019/5/16 20:27
 */
@Slf4j
public class BayesClassifier {
    private static Map<String, Integer> keyWordMap;

    static {
        try {
            keyWordMap = extractKeyWord();
        } catch (Exception e) {
            log.error("加载关键词失败，错误原因：{}", e.getMessage());
        }

    }

    /**
     * 加载文件
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    private static List<String> loadFile(String filePath) {
        File file = new File(filePath);
        BufferedReader br;
        List<String> lineList = Lists.newArrayList();
        String line;
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                lineList.add(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


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

    /**
     * 获取训练数据集，LabelPoint接收两个参数：
     * label（Double）分类标签
     * dense （Vector）张量
     *
     * @return
     * @throws IOException
     */
    private static List<LabeledPoint> getTrainDataList() {
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


    /**
     * 预测
     *
     * @return
     */
    public static Integer classifier(String sentence) {
        NaiveBayesModel model = NaiveBayes.train(getTrainDataList());
        double[] arr = sentencesToArrays(sentence);
        return model.predict(arr);
    }

    /**
     * 从抽象的问题模板中提取关键词
     *
     * @return
     */
    private static Map<String, Integer> extractKeyWord() {
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
        keyWordList.forEach(keyword -> vocabulary.put(keyword, keyWordList.indexOf(keyword)));
        return vocabulary;
    }
    private static void deleteDir(String path) {
        File file = new File(path);
        //取得当前目录下所有文件和文件夹
        String[] content = file.list();
        for (String name : Objects.requireNonNull(content)) {
            File temp = new File(path, name);
            //判断是否是目录
            if (temp.isDirectory()) {
                //递归调用，删除目录里的内容
                deleteDir(temp.getAbsolutePath());
                //删除空目录
                temp.delete();
            } else {
                //直接删除文件
                if (!temp.delete()) {
                    log.error("删除文件：{}，失败", name);
                }
            }
        }
    }

}
