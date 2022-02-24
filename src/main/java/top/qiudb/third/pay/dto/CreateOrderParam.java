package top.qiudb.third.pay.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 创建订单请求参数
 */
@Getter
@Setter
public class CreateOrderParam {
    @NotEmpty(message = "订单编号不允许为空")
    @ApiModelProperty(value = "订单编号", required = true)
    private String tradeNo;

    @NotEmpty(message = "订单名称不允许为空")
    @ApiModelProperty(value = "订单名称", required = true)
    private String tradeName;

    @NotEmpty(message = "请填写订单总金额")
    @ApiModelProperty(value = "总金额", required = true)
    private String totalAmount;
}
