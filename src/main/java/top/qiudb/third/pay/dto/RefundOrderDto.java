package top.qiudb.third.pay.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 订单退款结果
 */
@Getter
@Setter
public class RefundOrderDto {
    /**
     * 网关返回码   10000（接口调用成功）   20000（服务不可用）   40001（缺少必选参数）
     */
    private String code;

    /**
     * 网关返回码描述  Success（接口调用成功）   Business Failed（业务处理失败）
     */
    private String msg;

    /**
     * 支付宝交易号
     */
    private String tradeNo;

    /**
     * 商户订单号
     */
    private String outTradeNo;

    /**
     * 退款总金额
     */
    private String refundFee;
}
