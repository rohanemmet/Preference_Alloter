package com.cultofcthulhu.projectallocation.models.data;

import com.cultofcthulhu.projectallocation.models.StaffMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface StaffMemberDAO extends JpaRepository<StaffMember, Integer> {
    StaffMember findByName(String name);
}
