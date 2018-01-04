package back.db;

/**
 */

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import back.entity.SubscribedEntity;

public interface SubscribeRepository extends JpaRepository<SubscribedEntity, Integer> {

    int countBySubscribeOnId(Integer subscriberOnId);

    int countBySubscriberId(Integer subscriberId);

    SubscribedEntity findBySubscriberIdAndSubscribeOnId(Integer subscriberId, Integer profileId);

    List<SubscribedEntity> findAllByGroupIdAndSubscriberId(Integer groupId, Integer subscriberId);

    Integer countByGroupIdAndSubscriberId(Integer groupId, Integer subscriberId);
}