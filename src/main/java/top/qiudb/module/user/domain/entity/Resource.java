package top.qiudb.module.user.domain.entity;

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
@Comment("资源权限表")
@Table(name = "resource")
@EqualsAndHashCode(callSuper = true)
public class Resource extends BaseEntity implements Serializable {
    @Comment("资源分类唯一标识")
    @Column(name = "category_id", length = 32)
    private Long categoryId;

    @Comment("资源名称")
    @Column(name = "name", length = 200)
    private String name;

    @Comment("资源URL")
    @Column(name = "url", length = 200)
    private String url;

    @Comment("描述")
    @Column(name = "description", length = 500)
    private String description;
}