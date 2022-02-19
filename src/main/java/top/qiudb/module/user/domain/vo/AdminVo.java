package top.qiudb.module.user.domain.vo;

import lombok.Getter;
import lombok.Setter;
import top.qiudb.common.constant.LockedEnum;
import top.qiudb.common.constant.SexEnum;
import top.qiudb.common.domain.BaseVo;

import java.time.LocalDateTime;

/**
 * 管理员信息
 */
@Getter
@Setter
public class AdminVo extends BaseVo {
    private String userName;

    private String password;

    private String nickName;

    private Integer age;

    private SexEnum gender;

    private String email;

    private String phone;

    private String identityCard;

    private String avatar;

    private String note;

    private LockedEnum locked;

    private LocalDateTime loginTime;
}
