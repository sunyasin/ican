package back.db;

/**
 */

import org.springframework.data.jpa.repository.JpaRepository;

import back.entity.HaveReadEntity;

public interface HaveReadMessageRepository extends JpaRepository<HaveReadEntity, Integer> {

    int countBySubjIdAndMsgId(Integer subgId, Integer msgId);

}