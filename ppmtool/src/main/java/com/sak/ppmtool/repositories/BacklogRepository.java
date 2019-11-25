package com.sak.ppmtool.repositories;

import com.sak.ppmtool.model.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  BacklogRepository extends CrudRepository<Backlog,Long> {
    Backlog findByProjectIdentifier(String id);
}
