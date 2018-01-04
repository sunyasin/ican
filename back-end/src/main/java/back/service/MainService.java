package back.service;

import org.ideacreation.can.common.model.enums.ErrorDescription;
import org.ideacreation.can.common.model.enums.GroupType;
import org.ideacreation.can.common.model.enums.MessageType;
import org.ideacreation.can.common.model.enums.ProfileType;
import org.ideacreation.can.common.model.json.BoardMessage;
import org.ideacreation.can.common.model.json.BoardMessageInfo;
import org.ideacreation.can.common.model.json.ForeignProfileInfo;
import org.ideacreation.can.common.model.json.GroupInfo;
import org.ideacreation.can.common.model.json.OwnProfileInfo;
import org.ideacreation.can.common.model.json.PageableResponse;
import org.ideacreation.can.common.model.json.ProfileItem;
import org.ideacreation.can.common.model.json.RegistrationInfo;
import org.ideacreation.can.common.model.json.SubjectInfoForUpdate;
import org.ideacreation.can.common.model.json.TagInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import back.db.BonusRepository;
import back.db.HaveReadMessageRepository;
import back.db.MessageBookRepository;
import back.db.MessageRefRepository;
import back.db.MessageRepository;
import back.db.NativeDao;
import back.db.SubjGroupRepository;
import back.db.SubjRepository;
import back.db.SubjTagRepository;
import back.db.SubscribeRepository;
import back.db.TagRepository;
import back.entity.BonusEntity;
import back.entity.HaveReadEntity;
import back.entity.MessageBookEntity;
import back.entity.MessageEntity;
import back.entity.MessageRefEntity;
import back.entity.SubjectEntity;
import back.entity.SubjectGroupEntity;
import back.entity.SubscribedEntity;
import back.entity.SubscribedTagEntity;
import back.entity.TagEntity;
import back.model.LoggerContext;

import static back.model.enums_bak.MessageType.ACTION;
import static back.model.enums_bak.MessageType.EVENT;
import static org.ideacreation.can.common.model.enums.ErrorDescription.E_DUPLICATE_LOGIN;
import static org.ideacreation.can.common.model.enums.ErrorDescription.E_GROUPNAME_ALREADY_EXISTS;
import static org.ideacreation.can.common.model.enums.ErrorDescription.E_MSG_NOT_FOUND;
import static org.ideacreation.can.common.model.enums.ErrorDescription.E_MSG_TAG;
import static org.ideacreation.can.common.model.enums.ErrorDescription.E_MSG_TEXT_REQUIRED;
import static org.ideacreation.can.common.model.enums.ErrorDescription.E_MSG_TYPE_REQUIRED;
import static org.ideacreation.can.common.model.enums.ErrorDescription.E_NULL;
import static org.ideacreation.can.common.model.enums.ErrorDescription.E_PROFILE_NOT_FOUND;

/**
 *
 */
@Service
@Transactional
public class MainService {

    @Autowired
    SubjTagRepository favoriteTagsRepository;

    @Autowired
    SubjGroupRepository subjGroupRepository;

    @Autowired
    SubjRepository subjRepository;

    @Autowired
    MessageRefRepository messageRefRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    SubscribeRepository subscribeRepository;

    @Autowired
    BonusRepository bonusRepository;

    @Autowired
    HaveReadMessageRepository readRepository;

    @Autowired
    SubjGroupRepository groupRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    MessageBookRepository messageBookRepository;

    @Autowired
    NativeDao nativeDao;

    public List<TagInfo> getUserFavoriteTagList(SubjectEntity user) {

        List<SubscribedTagEntity> list = favoriteTagsRepository.findAllBySubjectEntityId(user.getId());
        List<TagInfo> result = list.stream().map(this::convertToCategoryInfo).collect(Collectors.toList());
        return result;
    }

    public ForeignProfileInfo getProfileByUserId(int profileUserId, int observerId) {

        SubjectEntity entity = subjRepository.findOne(profileUserId);
        ForeignProfileInfo result = new ForeignProfileInfo();
        BeanUtils.copyProperties(entity, result);

        if (entity.getType() != ProfileType.PERSON) {
            int bonuses = bonusRepository.countBySubjIdAndBonusProviderId(profileUserId, observerId);
            result.setBonuses(bonuses); // количество бонусов в этой точке у текущего пользователя
            result.setActionCount(messageRepository.countByTypeAndSubjId(ACTION, profileUserId));
            result.setEventCount(messageRepository.countByTypeAndSubjId(EVENT, profileUserId));
            int unreadCount = getUnreadCountInProfileForObserver(observerId, profileUserId);
            result.setUnreadCount(unreadCount);
            BoardMessageInfo lastMessage = getLastBoardMessage(profileUserId);
            result.setLastMessage(lastMessage);
        }

        int subscribersCount = getSubscribersCount(profileUserId);
        result.setSubscriberCount(subscribersCount);

        return result;
    }

    private BoardMessageInfo getLastBoardMessage(int profileId) {
        List<BoardMessageInfo> msg = getBoardByUserId(profileId, 0, 1);

        return msg == null || msg.isEmpty() ? null : msg.get(0);
    }

    // кол-во непрочитанных пользователем сообщений на заданной стене
    private int getUnreadCountInProfileForObserver(int observerId, int profileId) {

        int unread = nativeDao.getUnreadInProfileForObserver(observerId, profileId);
        return unread;
    }

    public List<BoardMessageInfo> getBoardByUserId(int userId, int startFromIndex, int count) {

        List<MessageRefEntity> board = messageRefRepository.findBySubjIdOrderByCreatedDesc(userId);
        List<BoardMessageInfo> result = new ArrayList<>(board.size());
        for (MessageRefEntity brdEntity : board) {
            if (count-- <= 0) {
                break;
            }
            BoardMessageInfo msgInfo = convertToMessageInfo(brdEntity);
            result.add(msgInfo);
        }
        return result;
    }

    private BoardMessageInfo convertToMessageInfo(MessageRefEntity brdEntity) {

        BoardMessageInfo msgInfo = new BoardMessageInfo();
        SubjectEntity subj = subjRepository.findOne(brdEntity.getSubjId());
        msgInfo.setSubjName(subj.getName());
        msgInfo.setBonusRead(brdEntity.getMessageEntity().getBonusReadAmount());
        msgInfo.setBonusTell(brdEntity.getMessageEntity().getBonusTellAmount());
        msgInfo.setSubjId(brdEntity.getSubjId());
        msgInfo.setTagId(brdEntity.getMessageEntity().getTagId());
        msgInfo.setLikes(brdEntity.getLikes());
        msgInfo.setCreated(brdEntity.getCreated());
        msgInfo.setBoardId(brdEntity.getId());
        msgInfo.setImageFile(brdEntity.getMessageEntity().getImage());
        msgInfo.setMessage(brdEntity.getMessageEntity().getMessage());
        msgInfo.setMsgId(brdEntity.getMessageEntity().getId());
        msgInfo.setType(brdEntity.getMessageEntity().getType());
        msgInfo.setEventDate(brdEntity.getMessageEntity().getEventDate());
        msgInfo.setEventTime(brdEntity.getMessageEntity().getEventTime());
        return msgInfo;
    }

    public void updateProfile(SubjectEntity subjectEntity, SubjectInfoForUpdate newInfo) {

        E_NULL.throwExceptionIfNull(subjectEntity, newInfo);
        if (newInfo.getLogin() != null) {
            subjectEntity.setLogin(newInfo.getLogin());
        }
        if (newInfo.getAddress() != null) {
            subjectEntity.setAddress(newInfo.getAddress());
        }
        if (newInfo.getContacts() != null) {
            subjectEntity.setContacts(newInfo.getContacts());
        }
        if (newInfo.getDescription() != null) {
            subjectEntity.setDescription(newInfo.getDescription());
        }
        if (newInfo.getDescription() != null) {
            subjectEntity.setDescription(newInfo.getDescription());
        }
        // подписки на теги обновляются отдельно
        //подписки на профайлы пропускаем - они не редактируются напрямую

        subjRepository.save(subjectEntity);
    }

    /**
     * добавить сообщение на стену. Если newMessage.id задано - значит это "поделиться",
     * иначе ожидается текст, картинка и тип сообщения
     *
     * @param subj       - чья стена
     * @param newMessage - данные сообщения. subjId, likes игнорируется, eventDate, eventTime - используются только для событий
     */
    public void addBoardMessage(SubjectEntity subj, BoardMessage newMessage) {

        E_NULL.throwExceptionIfNull(subj, newMessage);

        if (newMessage.getMsgId() != null) {
            MessageEntity msgEntity = messageRepository.findOne(newMessage.getMsgId());
            E_MSG_NOT_FOUND.throwExceptionIfNull(msgEntity);

            MessageRefEntity messageRefEntity = createBoardEntity(subj.getId(), msgEntity);
            messageRefRepository.save(messageRefEntity);
        } else {
            E_MSG_TEXT_REQUIRED.throwExceptionIfNull(newMessage.getMessage());
            E_MSG_TYPE_REQUIRED.throwExceptionIfNull(newMessage.getType());
            E_MSG_TAG.throwExceptionIfNull(newMessage.getTagId());

            MessageEntity msgEntity = new MessageEntity();
            msgEntity.setMessage(newMessage.getMessage());
            msgEntity.setType(newMessage.getType());
            msgEntity.setImage(newMessage.getImageFile());
            msgEntity.setEventDate(newMessage.getEventDate());
            msgEntity.setEventTime(newMessage.getEventTime());
            msgEntity.setTagId(newMessage.getTagId());
            if (newMessage.getImageFile() != null) {
                //todo загрузка картинки
            }
            messageRepository.save(msgEntity);
            MessageRefEntity messageRefEntity = createBoardEntity(subj.getId(), msgEntity);
            messageRefRepository.save(messageRefEntity);
        }
    }

    private MessageRefEntity createBoardEntity(Integer userId, MessageEntity msgEntity) {
        MessageRefEntity messageRefEntity = new MessageRefEntity();
        messageRefEntity.setSubjId(userId);
        messageRefEntity.setCreated(new Date());
        messageRefEntity.setMessageEntity(msgEntity);
        messageRefEntity.setLikes(0);
        return messageRefEntity;
    }

    private TagInfo convertToCategoryInfo(SubscribedTagEntity subjectCategoryEntity) {

        TagInfo info = new TagInfo();
        info.setId(subjectCategoryEntity.getTagEntity().getId());
        info.setName(subjectCategoryEntity.getTagEntity().getName());
        info.setMsgCount(77); //todo real msgCount
        return info;
    }

    public SubjectEntity register(RegistrationInfo info) {
        E_NULL.throwExceptionIfNull(info, info.getAccountType(), info.getLogin(), info.getPassword(), info.getName());

        //todo проверка уникальности логина
        if (null != subjRepository.findByLogin(info.getLogin())) {
            E_DUPLICATE_LOGIN.throwException();
        }

        SubjectEntity newUser = new SubjectEntity();
        newUser.setName(info.getName());
        newUser.setLogin(info.getLogin());
        newUser.setPass(info.getPassword());
        newUser.setCreated(new Date());
        newUser.setUpdated(newUser.getCreated());
        newUser.setType(info.getAccountType());

        newUser = subjRepository.save(newUser);

        return newUser;
    }

    public OwnProfileInfo getOwnProfileInfo(SubjectEntity subjectEntity) {

        E_NULL.throwExceptionIfNull(subjectEntity.getId());

        OwnProfileInfo profileInfo = new OwnProfileInfo();
        profileInfo.setId(subjectEntity.getId());
        profileInfo.setName(subjectEntity.getName());
        profileInfo.setAddress(subjectEntity.getAddress());
        profileInfo.setContacts(subjectEntity.getContacts());
        profileInfo.setDescription(subjectEntity.getDescription());
        profileInfo.setType(subjectEntity.getType());
        profileInfo.setPicture(subjectEntity.getPicture());
        profileInfo.setSubscriberCount(getSubscribersCount(subjectEntity.getId()));
        if (subjectEntity.getType() == ProfileType.PERSON) {
            profileInfo.setBonuses(getUserBonuses(subjectEntity.getId()));
            profileInfo.setBonusesInList(getUserBonusesInList(subjectEntity.getId()));
        }
        return profileInfo;
    }

    private int getUserBonusesInList(int userId) {
        int result = nativeDao.countUserBonusesInUnreadMessages(userId);
        return result;
    }

    private int getUserBonuses(int subjId) {
        Integer result = bonusRepository.getBonusTotalForSubjectId(subjId);
        return result == null ? 0 : result;
    }

    private int getSubscribersCount(Integer subjId) {
        return subscribeRepository.countBySubscribeOnId(subjId);
    }

    /**
     * список групп в ленте.
     * 1) лично созданные,
     * 2) сообщения в закладках
     * 3) подписки на профайлы
     * 4) подписанные теги
     *
     * @param subj
     * @return
     */
    public List<GroupInfo> getLentaGroups(SubjectEntity subj) {
        List<GroupInfo> result = new ArrayList<>();
        int position = 0;

        // 1) личные
        List<SubjectGroupEntity> subjGroups = groupRepository.findAllByOwnerId(subj.getId());
        for (SubjectGroupEntity subjGroup : subjGroups) {
            GroupInfo groupInfo = createGroupInfo(subjGroup.getId(), subjGroup.getGroupName(), position++, GroupType.PRIVATE);
            int itemsInGroup = subscribeRepository.countByGroupIdAndSubscriberId(subjGroup.getId(), subj.getId());
            Integer unreadCount = messageRepository.countUnreadInGroupForUser(subjGroup.getId(), subj.getId());
            groupInfo.setUnreadCount(unreadCount);
            groupInfo.setItemCount(itemsInGroup);
            result.add(groupInfo);
        }

        //2) сообщения в закладках если есть
        int bookCount = messageBookRepository.countByOwnerId(subj.getId());
        if (bookCount > 0) {
            GroupInfo groupInfo = createGroupInfo(position++, "Сообщения в закладках", position, GroupType.BOOKMARKED);
            groupInfo.setItemCount(bookCount);
            groupInfo.setUnreadCount(0); // то что в закладках - уже прочитано
            result.add(groupInfo);
        }

        //3) прямые подписки если есть
        int subscriberCount = subscribeRepository.countBySubscriberId(subj.getId());
        if (subscriberCount > 0) {
            GroupInfo groupInfo = createGroupInfo(position++, "Подписки", position, GroupType.SUBSCRIBED);
            groupInfo.setItemCount(subscriberCount);
            Integer unreadCount = messageRepository.countUnreadInSubscribedForUser(subj.getId());
            groupInfo.setUnreadCount(unreadCount);
            result.add(groupInfo);
        }

        //4) подписки на теги
        List<SubscribedTagEntity> tagList = favoriteTagsRepository.findAllBySubjectEntityId(subj.getId());
        for (SubscribedTagEntity tagEntity : tagList) {
            GroupInfo groupInfo = createGroupInfo(tagEntity.getTagEntity().getId(), tagEntity.getTagEntity().getName(), position++, GroupType.TAGGED);

            Integer profilesInTagCount = subjRepository.getCountProfilesByTagId(tagEntity.getId());
            groupInfo.setItemCount(profilesInTagCount);

            Integer unreadCount = messageRepository.countUnreadByTagForUser(tagEntity.getId(), subj.getId());
            groupInfo.setUnreadCount(unreadCount);

            result.add(groupInfo);
        }

        return result;
    }

    /**
     * лента группированная по профайлам с новостями
     *
     * @param subj
     * @return
     */
    public List<ProfileItem> getLentaProfilesByGroup(SubjectEntity subj, Integer groupId) {

        List<SubscribedEntity> groups = subscribeRepository.findAllByGroupIdAndSubscriberId(groupId, subj.getId());
        List<SubjectEntity> profileList = groups.stream().map(SubscribedEntity::getSubscribeOn).collect(Collectors.toList());
        List<ProfileItem> result = createProfileItemList(profileList, subj);
        return result;
    }

    public List<ProfileItem> getLentaProfilesByBookmarks(SubjectEntity subj) {

        List<SubjectEntity> profileList = subjRepository.profilesByMessageBookForUser(subj.getId());
        List<ProfileItem> result = createProfileItemList(profileList, subj);
        return result;
    }

    public List<ProfileItem> getLentaProfilesBySubscribtions(SubjectEntity subj) {

        List<SubjectEntity> profileList = subjRepository.profilesBySubscribtionsForUser(subj.getId());
        List<ProfileItem> result = createProfileItemList(profileList, subj);
        return result;
    }

    public PageableResponse<ProfileItem> getLentaProfilesByTagsForUser(SubjectEntity subj, Integer tagId, Integer lastPage) {
        final int limit = 1;

        PageableResponse<ProfileItem> pageResult = new PageableResponse<>();
        List<SubjectEntity> profileList;
        if (lastPage != null) {
//            profileList = subjRepository.profilesByTagsForUser(subj.getId(), tagId, limit, lastPage*limit);
            profileList = subjRepository.profilesByTagsForUser(subj.getId(), tagId);
            pageResult.pageIndex = lastPage + 1;
            pageResult.pageSize = limit;
        } else {
            profileList = subjRepository.profilesByTagsForUser(subj.getId(), tagId);
        }
        List<ProfileItem> result = createProfileItemList(profileList, subj);
        pageResult.result = result;

        return pageResult;
    }

    private List<ProfileItem> createProfileItemList(List<SubjectEntity> profileList, SubjectEntity subj) {
        List<ProfileItem> result = new ArrayList<>(profileList.size());
        for (SubjectEntity subjectEntity : profileList) {
            ProfileItem item = new ProfileItem();
            result.add(item);
            item.setProfileId(subjectEntity.getId());
            item.setName(subjectEntity.getName());

            int unread = nativeDao.getUnreadInProfileForObserver(subj.getId(), subjectEntity.getId());
            item.setUnreadCount(unread);

            SubscribedEntity subscription = subscribeRepository.findBySubscriberIdAndSubscribeOnId(subj.getId(), subjectEntity.getId());
            item.setFavorite(subscription != null);

            item.setImageFile(subjectEntity.getPicture());
        }
        return result;
    }

    private GroupInfo createGroupInfo(Integer id, String groupName, int position, GroupType type) {
        GroupInfo groupInfo = new GroupInfo();
        groupInfo.setId(id);
        groupInfo.setName(groupName);
        groupInfo.setPosition(position++);
        groupInfo.setType(type);
        return groupInfo;
    }

    public List<BoardMessageInfo> getMessagesByGroup(SubjectEntity subj, Integer groupId) {
        List<MessageRefEntity> messages = messageRepository.getMessagesByGroupForUser(groupId, subj.getId());
        List<BoardMessageInfo> result = convertToBoardMessageInfo(messages);
        return result;
    }

    public List<BoardMessageInfo> getMessagesBookedByUser(SubjectEntity subj) {
        List<MessageRefEntity> messages = messageRepository.getMessagesBookedByUser(subj.getId());
        List<BoardMessageInfo> result = convertToBoardMessageInfo(messages);
        return result;
    }

    public List<BoardMessageInfo> getMessagesBySubscriptions(SubjectEntity subj) {
        List<MessageRefEntity> messages = messageRepository.getMessagesBySubscriptionForUser(subj.getId());
        List<BoardMessageInfo> result = convertToBoardMessageInfo(messages);
        return result;
    }

    public List<BoardMessageInfo> getMessagesByTagsForUser(SubjectEntity subj, int tagId) {
        List<MessageRefEntity> messages = messageRepository.getMessagesByTagForUser(subj.getId(), tagId);
        List<BoardMessageInfo> result = convertToBoardMessageInfo(messages);
        return result;
    }

    private List<BoardMessageInfo> convertToBoardMessageInfo(List<MessageRefEntity> messages) {
        List<BoardMessageInfo> result = messages.stream().map(this::convertToMessageInfo).collect(Collectors.toList());
        return result;
    }

    /**
     * определить профайл в группу.
     *
     * @param subj      - пользователь группы
     * @param groupId   если < 0 - отвязка от всех групп
     * @param profileId - какой профайл привязать
     */
    public void setGroupForProfile(SubjectEntity subj, Integer groupId, Integer profileId) {

        E_NULL.throwExceptionIfNull(subj, groupId, profileId);

        SubjectEntity otherProfile = subjRepository.getOne(profileId);
        ErrorDescription.E_PROFILE_NOT_FOUND.throwExceptionIfNull(otherProfile);

        SubjectGroupEntity group = null;
        if (groupId > 0) {
            group = groupRepository.findOne(groupId);
            ErrorDescription.E_GROUP_NOT_FOUND.throwExceptionIfNull(group);
            if (subj.getId() != group.getOwner().getId()) {
                ErrorDescription.E_WRONG_GROUP_OWNER.throwException();
            }
        }

        SubscribedEntity subscribedEntity = subscribeRepository.findBySubscriberIdAndSubscribeOnId(subj.getId(), profileId);
        if (subscribedEntity == null) {
            subscribedEntity = new SubscribedEntity();
            subscribedEntity.setGroup(group);
            subscribedEntity.setSubscribeOn(otherProfile);
            subscribedEntity.setSubscriber(subj);
        } else {
            subscribedEntity.setGroup(group);
        }
        subscribeRepository.save(subscribedEntity);
    }

    /**
     * добавление группы
     *
     * @param subj
     * @param groupName
     */
    public void addGroup(SubjectEntity subj, String groupName) {

        E_NULL.throwExceptionIfNull(subj, groupName);

        SubjectGroupEntity group = groupRepository.findByGroupNameAndOwnerId(groupName, subj.getId());
        if (group != null) {
            E_GROUPNAME_ALREADY_EXISTS.throwException();
        }
        group = new SubjectGroupEntity();
        group.setGroupName(groupName);
        group.setOwner(subj);
        groupRepository.save(group);
    }

    public void renameGroup(SubjectEntity subj, Integer groupId, String groupName) {
        E_NULL.throwExceptionIfNull(subj, groupId, groupName);

        SubjectGroupEntity group = groupRepository.findByGroupNameAndOwnerId(groupName, subj.getId());
        ErrorDescription.E_GROUP_NOT_FOUND.throwExceptionIfNull(group);
        if (subj.getId() != group.getOwner().getId()) {
            ErrorDescription.E_WRONG_GROUP_OWNER.throwException();
        }
        group.setGroupName(groupName);
        groupRepository.save(group);
    }

    public void deleteGroup(SubjectEntity subj, Integer groupId) {
        E_NULL.throwExceptionIfNull(subj, groupId);

        SubjectGroupEntity group = groupRepository.findOne(groupId);
        ErrorDescription.E_GROUP_NOT_FOUND.throwExceptionIfNull(group);
        if (subj.getId() != group.getOwner().getId()) {
            ErrorDescription.E_WRONG_GROUP_OWNER.throwException();
        }
        groupRepository.delete(group);
    }

    public List<GroupInfo> listGroup(SubjectEntity subj) {
        E_NULL.throwExceptionIfNull(subj);

        List<SubjectGroupEntity> groupList = groupRepository.findAllByOwnerId(subj.getId());
        List<GroupInfo> result = new ArrayList<>(groupList.size());
        for (SubjectGroupEntity subjGroup : groupList) {
            GroupInfo groupInfo = createGroupInfo(subjGroup.getId(), subjGroup.getGroupName(), result.size(), GroupType.PRIVATE);
            groupInfo.setItemCount(77);//todo
            result.add(groupInfo);
        }
        return result;
    }

    public void subscribeToggle(SubjectEntity subj, Integer profileId) {
        E_NULL.throwExceptionIfNull(subj, profileId);
        SubscribedEntity record = subscribeRepository.findBySubscriberIdAndSubscribeOnId(subj.getId(), profileId);
        SubjectEntity subscribeOn = subjRepository.findOne(profileId);
        E_PROFILE_NOT_FOUND.throwExceptionIfNull(subscribeOn);

        if (record == null) {
            record = new SubscribedEntity();
            record.setSubscribeOn(subscribeOn);
            record.setSubscriber(subj);
            subscribeRepository.save(record);
        } else {
            subscribeRepository.delete(record);
        }
    }

    public void subscribeOnTag(SubjectEntity user, int tagId) {

        E_NULL.throwExceptionIfNull(user);

        TagEntity tag = tagRepository.findOne(tagId);
        ErrorDescription.E_TAG_NOT_FOUND.throwExceptionIfNull(tag);

        SubscribedTagEntity newTag = new SubscribedTagEntity();
        newTag.setTagEntity(tag);
        newTag.setSubjectEntity(user);
        user.getTagList().add(newTag);

        favoriteTagsRepository.save(newTag);
        subjRepository.save(user);
    }

    public void unsubscribeOnTag(SubjectEntity user, int tagId) {

        E_NULL.throwExceptionIfNull(user);

        for (SubscribedTagEntity tag : user.getTagList()) {
            if (tag.getTagEntity().getId() == tagId) {
                user.getTagList().remove(tag);
                favoriteTagsRepository.deleteAllByIdIn(Collections.singleton(tag.getId()));
                subjRepository.save(user);
                break;
            }
        }
    }

    public void markMessageAsRead(LoggerContext logctx, SubjectEntity subj, Integer boardMessageId) {

        E_NULL.throwExceptionIfNull(subj, boardMessageId);
        MessageRefEntity messageEntity = messageRefRepository.findOne(boardMessageId);
        E_MSG_NOT_FOUND.throwExceptionIfNull(messageEntity);

        int exists = readRepository.countBySubjIdAndMsgId(subj.getId(), messageEntity.getId());
        if (exists == 0) {
            HaveReadEntity haveReadEntity = new HaveReadEntity();
            haveReadEntity.setSubjId(subj.getId());
            haveReadEntity.setMsgId(messageEntity.getId());
            readRepository.save(haveReadEntity);

            BonusEntity bonus = new BonusEntity();
            bonus.setCreated(new Date());
            bonus.setSubjId(subj.getId());
            bonus.setBonusAmount(messageEntity.getMessageEntity().getBonusReadAmount());
            bonus.setBonusProviderId(messageEntity.getSubjId());
            bonusRepository.save(bonus);
        }
    }

    public void bookmarkMessage(LoggerContext logctx, SubjectEntity subj, Integer messageId) {

        E_NULL.throwExceptionIfNull(subj, messageId);

        MessageEntity msg = messageRepository.getOne(messageId);
        E_MSG_NOT_FOUND.throwExceptionIfNull(msg);

        MessageBookEntity bookmark = messageBookRepository.findByOwnerIdAndMessageId(subj.getId(), messageId);
        if (bookmark == null) {
            bookmark = new MessageBookEntity();
            bookmark.setMessage(msg);
            bookmark.setOwner(subj);
            messageBookRepository.save(bookmark);
        }
    }

    public void unbookmarkMessage(LoggerContext logctx, SubjectEntity subj, Integer messageId) {

        E_NULL.throwExceptionIfNull(subj, messageId);

        MessageEntity msg = messageRepository.getOne(messageId);
        E_MSG_NOT_FOUND.throwExceptionIfNull(msg);

        MessageBookEntity bookmark = messageBookRepository.findByOwnerIdAndMessageId(subj.getId(), messageId);
        if (bookmark != null) {
            messageBookRepository.delete(bookmark);
        }
    }

    public void updateProfileLogo(SubjectEntity user, String filename) {
        user.setPicture(filename);
        subjRepository.save(user);
    }

    public BoardMessageInfo getMessageById(LoggerContext logctx, SubjectEntity subj, Integer boardMessageId) {
        E_NULL.throwExceptionIfNull(subj, boardMessageId);

        MessageRefEntity msg = messageRefRepository.getOne(boardMessageId);
        E_MSG_NOT_FOUND.throwExceptionIfNull(msg);
        BoardMessageInfo msgInfo = convertToMessageInfo(msg);

        MessageBookEntity bookmark = messageBookRepository.findByOwnerIdAndMessageId(subj.getId(), msg.getId());
        msgInfo.setBookmarked(bookmark != null);

        SubscribedEntity subscription = subscribeRepository.findBySubscriberIdAndSubscribeOnId(subj.getId(), msg.getSubjId());
        msgInfo.setSubscribed(subscription != null);

        MessageRefEntity shared = messageRefRepository.findBySubjIdAndMessageEntityId(subj.getId(), msg.getId());
        msgInfo.setShared(shared != null);

        int exists = readRepository.countBySubjIdAndMsgId(subj.getId(), msg.getId());
        msgInfo.setWasRead(exists > 0);

        Integer bonusesTotal = bonusRepository.countBySubjId(subj.getId());
        msgInfo.setViewerBonuses(bonusesTotal);

        return msgInfo;
    }

    public void shareMessage(LoggerContext logctx, SubjectEntity subj, Integer boardMessageId) {
        E_NULL.throwExceptionIfNull(subj, boardMessageId);

        MessageRefEntity msg = messageRefRepository.getOne(boardMessageId);
        E_MSG_NOT_FOUND.throwExceptionIfNull(msg);

        MessageRefEntity ref = messageRefRepository.findBySubjIdAndMessageEntityId(subj.getId(), msg.getId());
        if (ref == null) {
            MessageRefEntity newEntry = new MessageRefEntity();
            newEntry.setSubjId(subj.getId());
            newEntry.setMessageEntity(msg.getMessageEntity());
            newEntry.setCreated(new Date());
            messageRefRepository.save(newEntry);
        }
    }

    public void notifyMessage(LoggerContext logctx, SubjectEntity subj, Integer boardMessageId) {
        E_NULL.throwExceptionIfNull(subj, boardMessageId);

        MessageRefEntity msg = messageRefRepository.getOne(boardMessageId);
        E_MSG_NOT_FOUND.throwExceptionIfNull(msg);
        if (msg.getMessageEntity().getType() != MessageType.EVENT) {
            ErrorDescription.E_EVENT_TYPE_REQUIRED.throwException();
        }
        //todo notify logic

    }
}
