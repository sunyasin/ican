package back.db;

/**
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import back.entity.MessageEntity;
import back.entity.MessageRefEntity;
import back.model.enums_bak.MessageType;

public interface MessageRepository extends JpaRepository<MessageEntity, Integer>, JpaSpecificationExecutor<MessageEntity> {

    String messagesByGroupForUser =     // все сообщения в группе для пользователя
            "select message_ref " +
                    " from   MessageRefEntity message_ref, SubscribedEntity subj_group " +
                    " where  message_ref.subjId = subj_group.subscribeOn.id " +
                    " AND message_ref.toId is null " + // сообщение не личное
                    " AND message_ref.subjId <> ?1 " + // чужое
                    " AND subj_group.group.id = ?2 " +
                    " AND subj_group.subscriber.id = ?1 ";

    String countUnreadMessagesByGroupForUser =     // непрочитанные сообщения в группе для пользователя
            "select count(message_ref) " +
                    " from   MessageRefEntity message_ref, SubscribedEntity subj_group " +
                    " where  message_ref.subjId = subj_group.subscribeOn.id " +
                    " AND message_ref.toId is null " + // сообщение не личное
                    " AND message_ref.subjId <> ?1 " + // чужое
                    " AND subj_group.group.id = ?2 " +
                    " AND subj_group.subscriber.id = ?1 " +
                    " AND message_ref.messageEntity.id not in (select msgId from HaveReadEntity where subjId = ?1)";

/*
            "select * from message_ref \n" +
            "  join message on message.id = message_ref.msg_id\n" +
            "  join subj on message_ref.subj_id = subj.id\n" +
            "  join subj_group on subj_group.subj_id = subj.id\n" +
            "where message_ref.to_id is null " +
                    " AND message_ref.subj_id <> ?1 AND subj_group.subj_id = ?1 and subj_group.id = ?2 ";
*/

    String messagesBySubscriptionForUser =  //-- сообщения в профайлах, на которые подписан пользователь\n" +
            "select message_ref " +
                    " from  MessageRefEntity message_ref, " +
                    "       SubscribedEntity subj_subscribe " +
                    "where subj_subscribe.subscribeOn.id = message_ref.subjId " +
                    "AND subj_subscribe.subscriber.id = ?1";

    String countUnreadMessagesBySubscriptionForUser =  //-- непочитанные сообщения в профайлах, на которые подписан пользователь\n" +
            "select count(message_ref) " +
                    " from  MessageRefEntity message_ref, " +
                    "       SubscribedEntity subj_subscribe " +
                    "where subj_subscribe.subscribeOn.id = message_ref.subjId " +
                    "AND subj_subscribe.subscriber.id = ?1" +
                    " AND message_ref.messageEntity.id not in (select msgId from HaveReadEntity where subjId = ?1)";

    String messagesByTagForUser =  //-- сообщения с тегами, на которые подписан пользователь\n" +
            "select message_ref " +
                    "from MessageRefEntity message_ref, " +
                    "     SubscribedTagEntity subj_tag  " +
                    "where message_ref.messageEntity.tagId = subj_tag.tagEntity.id AND " +
                    "      message_ref.toId is null  AND " + //-- сообщение на стене
                    "      message_ref.subjId <> ?1 AND " + // -- не свое
                    "      subj_tag.subjectEntity.id = ?1 AND" + // -- подписан на тэг
                    "      subj_tag.tagEntity.id = ?2"; // нужный тег

    String countUnreadMessagesByTagForUser =  //-- сообщения с тегами, на которые подписан пользователь\n" +
            "select count(message_ref) " +
                    "from MessageRefEntity message_ref, " +
                    "     SubscribedTagEntity subj_tag  " +
                    "where message_ref.messageEntity.tagId = subj_tag.tagEntity.id AND " +
                    "      message_ref.toId is null  AND " + //-- сообщение на стене
                    "      message_ref.subjId <> ?1 AND " + // -- не свое
                    "      subj_tag.subjectEntity.id = ?1 AND" + // -- подписан на тэг
                    "      subj_tag.tagEntity.id = ?2 " + // нужный тег
                    " AND message_ref.messageEntity.id not in (select msgId from HaveReadEntity where subjId = ?1)";
    /*
            "select MessageEntity from MessageRefEntity message_ref " +
                    "  join message on message.id = message_ref.msg_id \n" +
                    "  join subj_tag on message.tag_id = subj_tag.tag_id \n" +
                    "where message_ref.to_id is null  AND -- сообщение на стене \n" +
                    "      message_ref.subj_id <> ?1 AND -- не свое \n" +
                    "      subj_tag.subj_id = ?1";
    */
    String profilesByMessageBookForUser =                       // сообщ в закладках
            "select subj " +
                    "from SubjectEntity subj, MessageBookEntity msg_book, MessageRefEntity msg_ref " +
                    "where    msg_ref.subjId = subj.id" +
                    " AND msg_ref.messageEntity.id = msg_book.message.id " +
                    " AND msg_ref.subjId <> ?1 " +
                    " AND msg_ref.toId is null " +
                    " AND msg_book.owner.id = ?1 ";

    String messagesBookedByUser =  //-- сообщения в закладках у пользователя
            "select message_ref " +
                    " from MessageRefEntity  message_ref, " +
                    "      MessageBookEntity msg_book " +
                    "where msg_book.message.id = message_ref.messageEntity.id \n" + // закладка конкретного подписчика
                    " AND msg_book.owner.id = ?1 \n" + // закладка конкретного подписчика
                    " AND message_ref.toId is null \n" + //--  сообщение всем
                    " AND message_ref.subjId <> ?1 "; //-- сообщение не от подписчика
/*
        "select * from message_ref \n" +
                "  join message  on message.id = message_ref.msg_id\n" +
                "  join msg_book on msg_book.msg_id = message.id\n" +
                "where msg_book.subj_id = ?1 \n" + // конкретный подписчик
                "      AND message_ref.to_id is null \n" + //--  сообщение всем
                "      AND message_ref.subj_id <> ?1 "; //-- сообщение не подписчика
*/
/*
            "select sum(allmsg.bonus_read) \n"+
            "from ( \n"+
            "      --сообщения во всех группах \n" +
            "      select message.id, message.bonus_read from message \n"+
            "          join message_ref on message.id = message_ref.msg_id \n"+
            "          join subj on message_ref.subj_id = subj.id \n"+
            "          join subj_group on subj_group.subj_id = subj.id \n"+
            "      where message_ref.to_id is null \n"+
            "            AND message_ref.subj_id <> ?1 AND subj_group.subj_id = ?1 \n"+
            " UNION\n"+
                messagesBookedByUser +
            " UNION\n"+
                messagesBySubscriptionForUser +
            " UNION\n"+
                messagesByTagForUser +
            "     ) allmsg\n"+
            "     left join subj_sees_msg ssm on allmsg.id = ssm.msg_id \n"+
            " where ssm.id is null";
*/

    @Query("select count(msg_ref) " +
            "from MessageRefEntity msg_ref " +
            "where  msg_ref.messageEntity.type = ?1 AND msg_ref.subjId = ?2")
    Integer countByTypeAndSubjId(MessageType messageType, int profileId);

    @Query(messagesByGroupForUser)
    List<MessageRefEntity> getMessagesByGroupForUser(Integer userId, Integer groupId);

    @Query(messagesBySubscriptionForUser)
    List<MessageRefEntity> getMessagesBySubscriptionForUser(Integer userId);

    @Query(messagesByTagForUser)
    List<MessageRefEntity> getMessagesByTagForUser(Integer userId, Integer tagId);

    @Query(messagesBookedByUser)
    List<MessageRefEntity> getMessagesBookedByUser(Integer userId);

    @Query(countUnreadMessagesByGroupForUser)
    Integer countUnreadInGroupForUser(int groupId, int profileId);

    @Query(countUnreadMessagesBySubscriptionForUser)
    Integer countUnreadInSubscribedForUser(Integer subjId);

    @Query(countUnreadMessagesByTagForUser)
    Integer countUnreadByTagForUser(Integer tagEntityId, Integer subjId);
}