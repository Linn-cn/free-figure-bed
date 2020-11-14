package edu.changda.linn.figurebed.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "image_info")
@Data
public class ImageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 图片id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * 图片名字
     */
    @Column(name = "image_name", nullable = false)
    private String imageName;

    /**
     * jsDelivr访问url
     */
    @Column(name = "jsDelivr_url", nullable = false)
    private String jsdelivrUrl;

    /**
     * github访问url
     */
    @Column(name = "github_url", nullable = false)
    private String githubUrl;

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
