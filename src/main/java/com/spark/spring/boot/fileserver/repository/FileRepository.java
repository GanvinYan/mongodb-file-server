package com.spark.spring.boot.fileserver.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.spark.spring.boot.fileserver.domain.File;
 

/**
 * File 存储库.
 * 
 * @since 1.0.0 2018年3月28日
 * @author
 */
public interface FileRepository extends MongoRepository<File, String> {
}
