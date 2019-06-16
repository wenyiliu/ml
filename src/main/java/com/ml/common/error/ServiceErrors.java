package com.ml.common.error;

import java.io.Serializable;

/**
 * @author liuwenyi
 * @date 2019/4/30 14:56
 */
public interface ServiceErrors extends Serializable {

    /**
     * 获取错误码
     *
     * @return Integer
     */
    Integer getCode();

    /**
     * 获取错误信息
     *
     * @return String
     */
    String getMessage();
}
