package com.ml.common.error;

import lombok.Getter;

/**
 * @author liuwenyi
 * @date 2019/5/5 14:13
 */
public enum MIErrors implements ServiceErrors {

    /**
     * 错误码
     */
    TRAIN_DATA_IS_NOT_EMPTY(30000, "疾病不存在或输入错误"),
    DISEASE_IS_NOT_EXIST_OR_SYMNAMT_ERROR(30001, "与症状相关疾病不存在或症状输入错误"),
    DISEASE_AND_DRUG_NOT_EXIST_OR_SYMNAMT_ERROR(30002, "与症状相关疾病和药品不存在或症状输入错误"),
    DISEASE_CAUSE_IS_NOT_EXIST(30003, "与疾病相关的诱因不存在"),
    DISEASE_PREVENT_IS_NOT_EXIST(30004, "与疾病相关的防御措施不存在"),
    DISEASE_NOT_EXIST_OR_SYMNAMT_ERROR(30005, "与症状相关疾病不存在或症状输入错误"),
    DISEASE_CUREWAY_IS_NOT_EXIST(30006, "查询疾病治疗方式不存在"),
    DISEASE_DESC_IS_NOT_EXIST(30007, "查询疾病描述不存在"),
    CHECK_IS_NOT_EXIST(30009, "查询疾病相关检查不存在"),
    DRUG_IS_NOT_EXIST(30010, "查询疾病相关药物不存在"),
    SYMPTOM_IS_NOT_EXIST(30011, "查询疾病相关药物不存在"),
    SYNDROME_IS_NOT_EXIST(30012, "查询疾病相关并发症不存在"),
    DEPARTMENT_IS_NOT_EXIST(30013, "查询疾病所属科室不存在"),
    BAYES_MODEL_TRAIN_ERROR(30014, "naive bayes分类模型训练失败"),
    HOSPITAL_IS_NOT_EXIST(30014, "查询的医院不存在或输入错误"),
    SYMPTOM_AND_DISEASE_AND_CUREWAY_IS_NOT_EXIST(30014, "查询与症状相关的疾病以及治疗方式不存在"),
    ;


    @Getter
    private Integer code;

    @Getter
    private String message;

    MIErrors(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
