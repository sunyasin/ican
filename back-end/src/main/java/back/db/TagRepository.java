package back.db;

/**
 * репозиторий для тагов
 */

import org.springframework.data.jpa.repository.JpaRepository;

import back.entity.TagEntity;

public interface TagRepository extends JpaRepository<TagEntity, Integer> {
}