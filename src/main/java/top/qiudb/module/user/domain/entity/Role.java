package top.qiudb.module.user.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import top.qiudb.common.annotation.Comment;
import top.qiudb.common.constant.LockedEnum;
import top.qiudb.common.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Comment("角色表")
@Table(name = "role")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity implements Serializable {
    @Comment("角色名称")
    @Column(name = "name", length = 200)
    private String name;

    @Comment("描述")
    @Column(name = "description", length = 500)
    private String description;

    @Comment("权重")
    @Column(name = "sort", length = 10)
    @TableField(fill = FieldFill.INSERT)
    private Integer sort;

    @Comment("启用状态：0->未锁定；1->已锁定")
    @Column(name = "locked", length = 1)
    @TableField(fill = FieldFill.INSERT)
    private LockedEnum locked;
}