package back.db;

/**
 */

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import back.entity.SubjectGroupEntity;

public interface SubjGroupRepository extends JpaRepository<SubjectGroupEntity, Integer> {

    List<SubjectGroupEntity> findAllByOwnerId(Integer subjId);

    SubjectGroupEntity findByGroupNameAndOwnerId(String groupName, Integer subjId);
}