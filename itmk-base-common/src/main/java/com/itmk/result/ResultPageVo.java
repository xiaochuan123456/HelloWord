package com.itmk.result;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 返回分页数据实体
 * @param <T>
 */
@Data
@AllArgsConstructor
public class ResultPageVo<T> {
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 返回状态码
     */
    private int code;
    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 页容量
     */
    private int pageSize;
    /**
     * 数据总条数
     */
    private int total;
    /**
     * 返回的数据
     */
    private T data;
}
