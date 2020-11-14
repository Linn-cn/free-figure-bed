package edu.changda.linn.figurebed.dao;

import edu.changda.linn.figurebed.entity.ImageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImageInfoRepository extends JpaRepository<ImageInfo, Long>, JpaSpecificationExecutor<ImageInfo> {

}