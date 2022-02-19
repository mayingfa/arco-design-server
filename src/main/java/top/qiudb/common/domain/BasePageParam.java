package top.qiudb.common.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BasePageParam {
    @ApiModelProperty(value = "当前页", example="1")
    Integer pageNum = 1;

    @ApiModelProperty(value = "每页大小", example="20")
    Integer pageSize = 20;
}
