package com.ml.bayes;


/**
 * @auther: liuwenyi
 * @date 2019/5/15 11:20
 */
public enum QuestionsEnum {

    /**
     * 问题模板索引
     */

    DISEASE_PREVENT(0.0, "ill 预防", "data/question/ill/prevent.txt"),
    DISEASE_CAUSE(1.0, "ill 诱因", "data/question/ill/cause.txt"),
    DISEASE_DESC(2.0, "ill 简介", "data/question/ill/desc.txt"),
    SYMPTOM_CHECK(3.0, "sym 检查", "data/question/sym/check.txt"),
    DISEASE_CUREWAY(4.0, "ill 治疗方式", "data/question/ill/cureway.txt"),
    DISEASE_CHECK(6.0, "ill 诊断检查", "data/question/ill/check.txt"),
    DISEASE_DRUG(7.0, "ill 常用药品", "data/question/ill/drug.txt"),
    DISEASE_SYMPTOM(8.0, "ill 症状", "data/question/ill/sym.txt"),
    DISEASE_DISEASE(9.0, "ill 并发症", "data/question/ill/dis.txt"),
    DISEASE_DEPARTMENT(10.0, "ill 所属科室", "data/question/ill/department.txt"),
    SYMPTOM_DISEASE(11.0, "sym 导致疾病", "data/question/sym/dis.txt"),
    SYMPTOM_DRUG(5.0, "sym 用药", "data/question/sym/drug.txt"),;

    private Double index;


    private String question;


    private String filePath;

    QuestionsEnum(Double index, String question, String filePath) {
        this.index = index;
        this.question = question;
        this.filePath = filePath;
    }

    public static String getQuestionPattern(Double index) {
        for (QuestionsEnum question : QuestionsEnum.values()) {
            if (index.equals(question.index)) {
                return question.question;
            }
        }
        return null;
    }

    public Double getIndex() {
        return index;
    }

    public String getQuestion() {
        return question;
    }

    public String getFilePath() {
        return filePath;
    }

    public static void main(String[] args) {
        System.out.println(getQuestionPattern(1.0));
    }
}
