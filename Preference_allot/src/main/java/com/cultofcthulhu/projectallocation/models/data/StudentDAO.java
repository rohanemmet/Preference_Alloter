package com.cultofcthulhu.projectallocation.models.data;

import com.cultofcthulhu.projectallocation.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface StudentDAO extends JpaRepository<Student, Integer> {
}
