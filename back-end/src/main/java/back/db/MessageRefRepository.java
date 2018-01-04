package back.db;

/**
 */

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import back.entity.MessageRefEntity;

public interface MessageRefRepository extends JpaRepository<MessageRefEntity, Integer> {


    List<MessageRefEntity> findBySubjIdOrderByCreatedDesc(Integer subjId);

    MessageRefEntity findBySubjIdAndMessageEntityId(Integer subjId, Integer messageId);
}