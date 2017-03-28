package com.athena.repository;

import com.athena.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tommy on 2017/3/28.
 */
@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
}