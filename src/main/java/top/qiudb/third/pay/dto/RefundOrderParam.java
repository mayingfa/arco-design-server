package top.qiudb.third.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 订单退款请求参数
 */
@Getter
@Setter
public class RefundOrderParam {
    @NotEmpty(message = "订单编号不允许为空")
    @ApiModelProperty(value = "订单编号", required = true)
    private String tradeNo;

    @NotEmpty(message = "请填写退款金额")
    @ApiModelProperty(value = "退款金额", required = true)
    private String refundAmount;

    @NotEmpty(message = "请填写退款原因")
    @ApiModelProperty(value = "退款原因", required = true)
    private String refundReason;
}
