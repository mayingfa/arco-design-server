package top.qiudb.module.user.domain.vo;

import lombok.Getter;
import lombok.Setter;
import top.qiudb.common.constant.LockedEnum;
import top.qiudb.common.domain.BaseVo;

/**
 * 角色信息
 */
@Getter
@Setter
public class RoleVo extends BaseVo {
    private String name;

    private String description;

    private Integer sort;

    private LockedEnum locked;
}
