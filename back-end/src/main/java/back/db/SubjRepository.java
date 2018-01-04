package back.db;

/**
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

import back.entity.SubjectEntity;

public interface SubjRepository extends JpaRepository<SubjectEntity, Integer>, PagingAndSortingRepository<SubjectEntity, Integer>
        /*JpaSpecificationExecutor<SubjectEntity> */ {

    String paged = " LIMIT :limit OFFSET :offset";

    String profilesByMessageBookForUser =                       // профайлы с сообщениями в закладках
            "select subj " +
                    "from SubjectEntity subj, MessageBookEntity msg_book, MessageRefEntity msg_ref " +
                    "where    msg_ref.subjId = subj.id" +
                    " AND msg_ref.messageEntity.id = msg_book.message.id " +
                    " AND msg_ref.subjId <> ?1 " +
                    " AND msg_ref.toId is null " +
                    " AND msg_book.owner.id = ?1 ";

    String profilesBySubscribtionsForUser =                     // подписки на профайлы
            "SELECT  subj " +
                    "FROM SubjectEntity subj, SubscribedEntity subj_subscribe " +
                    "WHERE subj_subscribe.subscribeOn.id = subj.id  " +
                    "AND subj_subscribe.subscriber.id = ?1 ";

    String profilesByTagsForUser =                              // подписки на теги
            "select distinct subj_tag.subscribeOn " +
                    "from SubscribedEntity subj_tag, MessageRefEntity message_ref " +
                    " where  message_ref.subjId = subj_tag.subscribeOn.id " +
                    "   and message_ref.subjId <> ?1 " +
                    "   and message_ref.toId is null\n" +
                    "   and subj_tag.subscriber.id = ?1 " +
                    "   and message_ref.messageEntity.tagId = ?2";

/*
select distinct subj.id, subj.name from subj
  join message_ref message_ref on message_ref.subj_id = subj.id
  join message  on message.id = message_ref.msg_id
  join subj_subscribe subj_tag on subj_tag.subscribe_on = message_ref.subj_id
 where message_ref.subj_id <> 1
       and message_ref.to_id is null
       and subj_tag.subscriber = 1 and message.tag_id = 1
*/

    SubjectEntity findByLoginAndPass(String username, String password);

    SubjectEntity findByLogin(String login);

    @Query(profilesByMessageBookForUser)
    List<SubjectEntity> profilesByMessageBookForUser(Integer observerId);

    @Query(profilesBySubscribtionsForUser)
    List<SubjectEntity> profilesBySubscribtionsForUser(Integer observerId);

    @Query(profilesByTagsForUser)
    List<SubjectEntity> profilesByTagsForUser(Integer observerId, Integer tagId);

    //@Query(profilesByTagsForUser + paged)    // todo unexpected token: LIMIT
    @Query(profilesByTagsForUser)
    List<SubjectEntity> profilesByTagsForUser(Integer id, Integer tagId, int limit, int offset);

    @Query("select count(distinct subj) " +
            "from SubjectEntity subj, MessageRefEntity msg_ref " +
            "where msg_ref.subjId = subj.id " +
            "   AND msg_ref.toId is NULL" +
            "   AND msg_ref.messageEntity.tagId = ?1")
    Integer getCountProfilesByTagId(Integer tagEntityId);

}