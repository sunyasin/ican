package back.db;

/**
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import back.entity.BonusEntity;

public interface BonusRepository extends JpaRepository<BonusEntity, Integer> {

    @Query("select sum(bonusAmount) as bonus from BonusEntity where subjId = ?1")
    Integer getBonusTotalForSubjectId(Integer subjId);

    Integer countBySubjIdAndBonusProviderId(int profileUserId, int observerId);

    Integer countBySubjId(Integer observerId);
}