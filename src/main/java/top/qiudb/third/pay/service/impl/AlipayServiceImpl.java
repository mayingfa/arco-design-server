package top.qiudb.third.pay.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.qiudb.common.exception.Asserts;
import top.qiudb.common.properties.AlipayProperties;
import top.qiudb.third.pay.dto.CreateOrderParam;
import top.qiudb.third.pay.dto.RefundOrderDto;
import top.qiudb.third.pay.dto.RefundOrderParam;
import top.qiudb.third.pay.service.AlipayService;

import javax.annotation.Resource;

@Slf4j
@Service
public class AlipayServiceImpl implements AlipayService {
    private static final String PRODUCT_CODE = "FAST_INSTANT_TRADE_PAY";
    private static final String SUCCESS_CODE = "10000";
    private static final String REFUND_RESPONSE = "alipay_trade_refund_response";

    @Resource
    private AlipayProperties aliPayProperties;

    @Resource
    private AlipayClient alipayClient;

    @Override
    public String create(CreateOrderParam createOrderParam) {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(createOrderParam.getTradeNo());
        model.setSubject(createOrderParam.getTradeName());
        model.setTotalAmount(createOrderParam.getTotalAmount());
        model.setProductCode(PRODUCT_CODE);
        alipayRequest.setBizModel(model);
        alipayRequest.setReturnUrl(aliPayProperties.getReturnUrl());
        alipayRequest.setNotifyUrl(aliPayProperties.getNotifyUrl());
        try {
            return alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            Asserts.fail("创建支付宝订单失败", e);
        }
        return null;
    }

    @Override
    public void refund(RefundOrderParam refundOrderParam) {
        AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
        model.setOutTradeNo(refundOrderParam.getTradeNo());
        model.setRefundAmount(refundOrderParam.getRefundAmount());
        model.setRefundReason(refundOrderParam.getRefundReason());
        alipayRequest.setBizModel(model);
        try {
            String response = alipayClient.execute(alipayRequest).getBody();
            RefundOrderDto refundOrderDto = getRefundOrderDto(response);
            Asserts.checkTrue(SUCCESS_CODE.equals(refundOrderDto.getCode()), "支付宝订单退款失败");
        } catch (AlipayApiException | JsonProcessingException e) {
            Asserts.fail("支付宝订单退款失败", e);
        }
    }

    /**
     * 解析退款信息
     *
     * @param response 响应参数
     * @return 退款信息
     * @throws JsonProcessingException JSON解析异常
     */
    private RefundOrderDto getRefundOrderDto(String response) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        JsonNode jsonNode = objectMapper.readTree(response).get(REFUND_RESPONSE);
        return objectMapper.treeToValue(jsonNode, RefundOrderDto.class);
    }
}
