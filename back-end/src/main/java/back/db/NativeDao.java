package back.db;

/**
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

@Repository
public class NativeDao {

    private final static String countUserBonusesInUnreadMessages = //    --сумма бонусов в непрочитанных сообщениях в ленте
            "select sum(allmsg.bonus_read) \n" +
                    "from ( \n" +
                    "   select distinct * FROM" +
                    // сообщ в закладках
                    "( select message.id, message.bonus_read from message  \n" +
                    "  join message_ref  on message.id = message_ref.msg_id \n" +
                    "  join msg_book on msg_book.msg_id = message.id \n" +
                    "where msg_book.subj_id = ?1 \n" + // конкретный подписчик
                    "      AND message_ref.to_id is null \n" + //--  сообщение всем
                    "      AND message_ref.subj_id <> ?1 " + //-- сообщение не подписчика
                    " UNION \n" + // сообщения из подписок, включает все группы
                    "select message.id, message.bonus_read from message \n" +
                    " join message_ref  on message.id = message_ref.msg_id \n" +
                    " join subj_subscribe on subj_subscribe.subscribe_on = message_ref.subj_id \n" +
                    " where subj_subscribe.subscriber = ?1 \n" +
                    " UNION \n" + // сообщения по тагам
                    "select message.id, message.bonus_read from message  " +
                    "  join message_ref on message.id = message_ref.msg_id \n" +
                    "  join subj_tag on message.tag_id = subj_tag.tag_id \n" +
                    "where message_ref.to_id is null  AND -- сообщение на стене \n" +
                    "      message_ref.subj_id <> ?1 AND -- не свое \n" +
                    "      subj_tag.subj_id = ?1 " +
                    ") nondistinct" +
                    ") allmsg \n" +
                    "     left join subj_sees_msg ssm on allmsg.id = ssm.msg_id \n" +
                    " where ssm.id is null";

    private final static String countUnreadMessagesInProfile =
            "select total1 - read1 as unread \n" +
                    "from (select count(*) as read1 from subj_sees_msg ssm " +
                    "       join message bm on bm.id = ssm.msg_id " +
                    "       where ssm.subj_id = ?1" +
                    "     ) read, " +
                    "     (select count(*) total1 from message_ref where subj_id = ?2 and to_id is null) total_in_profile";

    @Autowired
    EntityManagerFactory entityManagerFactory;

    public int countUserBonusesInUnreadMessages(int userId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createNativeQuery(countUserBonusesInUnreadMessages);
        query.setParameter(1, userId);
        BigInteger intResult = (BigInteger) query.getSingleResult();
        return intResult == null ? 0 : intResult.intValue();
    }

    public int getUnreadInProfileForObserver(int observerId, int whereProfileId) {
        EntityManager em = entityManagerFactory.createEntityManager();
        Query query = em.createNativeQuery(countUnreadMessagesInProfile);
        query.setParameter(1, observerId);
        query.setParameter(2, whereProfileId);
        BigInteger intResult = (BigInteger) query.getSingleResult();
        return intResult == null ? 0 : intResult.intValue();
    }
}