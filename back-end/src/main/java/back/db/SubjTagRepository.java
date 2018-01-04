package back.db;

/**
 * репозиторий для подписок на таги
 */

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

import back.entity.SubscribedTagEntity;

public interface SubjTagRepository extends JpaRepository<SubscribedTagEntity, Integer> {

    List<SubscribedTagEntity> findAllBySubjectEntityId(Integer subjId);

    void deleteAllByIdIn(Set<Integer> subjIds);
}