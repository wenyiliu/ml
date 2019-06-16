package com.ml.bayes;


import lombok.Getter;

/**
 * @author liuwenyi
 * @date 2019/5/15 11:20
 */
public enum QuestionsEnum {

    /**
     * 问题模板索引
     */

    DISEASE_PREVENT(0, "ill 预防", "data/question/ill/prevent.txt"),
    DISEASE_CAUSE(1, "ill 诱因", "data/question/ill/cause.txt"),
    DISEASE_DESC(2, "ill 简介", "data/question/ill/desc.txt"),
    SYMPTOM_CHECK(3, "sym 检查", "data/question/sym/check.txt"),
    DISEASE_CUREWAY(4, "ill 治疗方式", "data/question/ill/cureway.txt"),
    DISEASE_CHECK(6, "ill 诊断检查", "data/question/ill/check.txt"),
    DISEASE_DRUG(7, "ill 常用药品", "data/question/ill/drug.txt"),
    DISEASE_SYMPTOM(8, "ill 症状", "data/question/ill/sym.txt"),
    DISEASE_DISEASE(9, "ill 并发症", "data/question/ill/dis.txt"),
    DISEASE_DEPARTMENT(10, "ill 所属科室", "data/question/ill/department.txt"),
    SYMPTOM_DISEASE(11, "sym 导致疾病", "data/question/sym/dis.txt"),
    SYMPTOM_DRUG(5, "sym 用药", "data/question/sym/drug.txt"),;

    @Getter
    private Integer index;

    @Getter
    private String question;

    @Getter
    private String filePath;

    QuestionsEnum(Integer index, String question, String filePath) {
        this.index = index;
        this.question = question;
        this.filePath = filePath;
    }

    public static String getQuestionPattern(Integer index) {
        for (QuestionsEnum question : QuestionsEnum.values()) {
            if (index.equals(question.index)) {
                return question.question;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getQuestionPattern(1));
    }
}
