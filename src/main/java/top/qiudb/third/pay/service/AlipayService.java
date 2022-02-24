package top.qiudb.third.pay.service;

import top.qiudb.third.pay.dto.CreateOrderParam;
import top.qiudb.third.pay.dto.RefundOrderParam;

public interface AlipayService {
    /**
     * 创建订单
     *
     * @param createOrderParam 创建订单参数
     * @return 支付表单
     */
    String create(CreateOrderParam createOrderParam);

    /**
     * 订单退款
     *
     * @param refundOrderParam 订单退款参数
     */
    void refund(RefundOrderParam refundOrderParam);
}