package com.cultofcthulhu.projectallocation.models.data;

import com.cultofcthulhu.projectallocation.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ProjectDAO extends JpaRepository<Project, Integer> {
    Optional<Project> findByProjectTitle(String title);
    Optional<Project> findByProposedBy(int proposedBy);
}
