package top.qiudb.module.user.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import top.qiudb.common.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Comment("管理员角色关系表")
@Table(name = "admin_role_relation")
public class AdminRoleRelation implements Serializable {
    @Id
    @Comment("唯一标识")
    @TableId(type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 32, nullable = false)
    private Long id;

    @Comment("管理员唯一标识")
    @Column(name = "admin_id", length = 32)
    private Long adminId;

    @Comment("角色唯一标识")
    @Column(name = "role_id", length = 32)
    private Long roleId;
}