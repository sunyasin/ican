package back.db;

/**
 */

import org.springframework.data.jpa.repository.JpaRepository;

import back.entity.MessageBookEntity;

public interface MessageBookRepository extends JpaRepository<MessageBookEntity, Integer> {

//    List<MessageBookEntity> findAllByOwnerId(Integer ownerId);

    int countByOwnerId(Integer ownerId);

    MessageBookEntity findByOwnerIdAndMessageId(Integer subjId, Integer messageId);
}