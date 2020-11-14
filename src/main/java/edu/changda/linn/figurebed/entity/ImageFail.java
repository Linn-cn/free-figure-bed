package edu.changda.linn.figurebed.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "image_fail")
public class ImageFail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 图片名字
     */
    @Column(name = "image_name", nullable = false)
    private String imageName;

    /**
     * 错误信息
     */
    @Column(name = "error_info", nullable = false)
    private String errorInfo;

    /**
     * 创建时间
     */
    @Column(name = "create_time", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time", nullable = false)
    @UpdateTimestamp
    private Timestamp updateTime;

}
