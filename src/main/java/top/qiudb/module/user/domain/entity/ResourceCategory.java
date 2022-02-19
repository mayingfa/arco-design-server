package top.qiudb.module.user.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import top.qiudb.common.annotation.Comment;
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
@Comment("资源分类表")
@Table(name = "resource_category")
@EqualsAndHashCode(callSuper = true)
public class ResourceCategory extends BaseEntity implements Serializable {
    @Comment("分类名称")
    @Column(name = "name", length = 200)
    private String name;

    @Comment("权重")
    @Column(name = "sort", length = 10)
    @TableField(fill = FieldFill.INSERT)
    private Integer sort;
}