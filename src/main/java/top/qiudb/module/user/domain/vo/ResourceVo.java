package top.qiudb.module.user.domain.vo;

import lombok.Getter;
import lombok.Setter;
import top.qiudb.common.domain.BaseVo;

/**
 * 资源权限信息
 */
@Getter
@Setter
public class ResourceVo extends BaseVo {
    private Long categoryId;

    private String name;

    private String url;

    private String description;
}
