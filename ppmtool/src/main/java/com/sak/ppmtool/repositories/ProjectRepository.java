package com.sak.ppmtool.repositories;

import com.sak.ppmtool.model.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Long> {
    @Override
    Iterable<Project> findAll();

    Project findByProjectIdentifier(String projectId);
}