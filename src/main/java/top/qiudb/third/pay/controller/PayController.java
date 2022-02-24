package top.qiudb.third.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import top.qiudb.common.constant.PublicConstant;
import top.qiudb.common.domain.CommonResult;
import top.qiudb.common.properties.AlipayProperties;
import top.qiudb.third.pay.dto.CreateOrderParam;
import top.qiudb.third.pay.dto.RefundOrderParam;
import top.qiudb.third.pay.service.AlipayService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "第三方支付服务")
@RequestMapping("/third/pay/")
public class PayController {
    @Autowired
    AlipayService alipayService;

    @Autowired
    AlipayProperties alipayProperties;

    @ApiOperation(value = "支付订单")
    @PostMapping(value = "/create")
    public CommonResult<String> createOrder(@Validated @RequestBody CreateOrderParam param) {
        String paymentForm = alipayService.create(param);
        log.info("\n"+paymentForm);
        return CommonResult.success("订单支付成功", paymentForm);
    }

    @ApiOperation(value = "订单退款")
    @PostMapping(value = "/refund")
    public CommonResult<String> refundOrder(@Validated @RequestBody RefundOrderParam param) {
        alipayService.refund(param);
        return CommonResult.success("订单退款成功");
    }

    @ApiIgnore
    @ApiOperation(value = "同步回调")
    @GetMapping(value = "/success")
    public void paySuccess(@RequestParam Map<String, String> param, HttpServletResponse response) throws IOException {
        String tradeNo = param.get(PublicConstant.TRADE_NO);
        String outTradeNo = param.get(PublicConstant.OUT_TRADE_NO);
        String totalAmount = param.get(PublicConstant.TOTAL_AMOUNT);
        String timestamp = param.get(PublicConstant.TIMESTAMP);
        log.info("[支付接口回调成功]\n {交易编号：{}，回调时间：{}，订单编号：{}，订单金额：{}}",
                tradeNo, timestamp, outTradeNo, totalAmount);
        response.sendRedirect(alipayProperties.getRedirectUrl());
    }

    @ApiIgnore
    @ApiOperation("异步回调")
    @PostMapping(value = "/notify")
    public void payNotify(@RequestParam Map<String, String> param) {
        String tradeStatus = param.get(PublicConstant.TRADE_STATUS);
        String tradeNo = param.get(PublicConstant.TRADE_NO);
        String tradeTime = param.get(PublicConstant.GMT_PAYMENT);
        if (this.rsaCheck(param) && PublicConstant.TRADE_SUCCESS.equals(tradeStatus)) {
            String outTradeNo = param.get(PublicConstant.OUT_TRADE_NO);
            String tradeName = param.get(PublicConstant.SUBJECT);
            String payAmount = param.get(PublicConstant.BUYER_PAY_AMOUNT);
            log.info("[支付成功]\n {交易编号：{}，交易时间：{}，订单编号：{}，订单名称：{}，交易金额：{}}",
                    tradeNo, tradeTime, outTradeNo, tradeName, payAmount);
        } else {
            log.error("[支付失败]\n {交易编号：{}，交易时间：{}}", tradeNo, tradeTime);
        }
    }

    /**
     * 校验签名，防止黑客篡改参数
     *
     * @param params 异步回调参数
     * @return 签名是否正确
     */
    private boolean rsaCheck(Map<String, String> params) {
        try {
            return AlipaySignature.rsaCheckV1(params,
                    alipayProperties.getAlipayPublicKey(),
                    alipayProperties.getCharset(),
                    alipayProperties.getSignType());
        } catch (AlipayApiException e) {
            log.error("验证签名错误", e);
            return false;
        }
    }
}
