package back.db;

/**
 * Map to auth_user table
 */

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import back.entity.TokenEntity;

public interface AuthTokenRepository extends JpaRepository<TokenEntity, Long> {

    List<TokenEntity> findAll();

    TokenEntity findByToken(String token);

    TokenEntity findBySubjId(int id);
}