package edu.changda.linn.figurebed.dao;

import edu.changda.linn.figurebed.entity.ImageFail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImageFailRepository extends JpaRepository<ImageFail, Long>, JpaSpecificationExecutor<ImageFail> {

}