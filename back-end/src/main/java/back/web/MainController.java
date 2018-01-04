package back.web;

import org.apache.log4j.Logger;
import org.ideacreation.can.common.model.json.ApiResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import back.entity.SubjectEntity;
import back.model.LoggerContext;
import back.model.LoggerImpl;
import back.service.AuthService;
import back.service.MainService;

import static back.model.LoggerImpl.Level.INFO;

@RestController
@RequestMapping(value = "/api")
@Produces("application/json")
@Consumes("application/json")
public class MainController {

    @Autowired
    MainService service;
    @Autowired
    AuthService authService;

    private static final Logger logger = Logger.getLogger(MainController.class);
    private LoggerImpl.Level logLevel = INFO;

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("Alive");
    }

    /**
     * регистрация нового профиля
     *
     * @param info
     * @return данные нового профиля
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<OwnProfileInfo> register(@RequestBody RegistrationInfo info) {

        String logMsg = "GET /register " + info;
        LoggerContext logctx = buildLogger().start(logMsg);
        try {

            SubjectEntity newUser = service.register(info);
            OwnProfileInfo profile = service.getOwnProfileInfo(newUser);

            return ApiResponse.instance(profile);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * список подписок на таги для текущего пользователя
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<TagInfo>> getMyTagList(HttpServletRequest request) {

        String logMsg = "GET /tag";
        LoggerContext logctx = buildLogger().start(logMsg);
        try {
            SubjectEntity user = authService.checkLoggedAndGet(request, logctx);

            List<TagInfo> result = service.getUserFavoriteTagList(user);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * подписка на таг для текущего пользователя
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag/{tagId}/subscribe", method = RequestMethod.PUT)
    public ApiResponse<?> subscribeOnTag(@PathVariable("tagId") int tagId,
                                         HttpServletRequest request) {

        String logMsg = String.format("---> PUT /tag/%s/subscribe", tagId);
        LoggerContext logctx = buildLogger().start(logMsg);
        try {
            SubjectEntity user = authService.checkLoggedAndGet(request, logctx);

            service.subscribeOnTag(user, tagId);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * отписка на таг для текущего пользователя
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/tag/{tagId}/unsubscribe", method = RequestMethod.PUT)
    public ApiResponse<?> unsubscribeOnTag(@PathVariable("tagId") int tagId,
                                           HttpServletRequest request) {
        String logMsg = String.format("---> PUT /tag/%s/UNsubscribe", tagId);
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity user = authService.checkLoggedAndGet(request, logctx);

            service.unsubscribeOnTag(user, tagId);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * запрос чужого профиля по id
     *
     * @param profileId
     * @param request
     * @return
     */
    @RequestMapping(value = "/profile/{profileId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<ForeignProfileInfo> getProfile(@PathVariable("profileId") int profileId, HttpServletRequest request) {

        String logMsg = "GET /profile/" + profileId;
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity caller = authService.checkLoggedAndGet(request, logctx);

            ForeignProfileInfo result = service.getProfileByUserId(profileId, caller.getId());

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * запрос данных профиля текущего пользователя
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<OwnProfileInfo> getMyProfile(HttpServletRequest request) {

        String logMsg = "GET /profile";
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity user = authService.checkLoggedAndGet(request, logctx);
            OwnProfileInfo result = service.getOwnProfileInfo(user);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }

    }

    /**
     * обновление профиля текущего пользователя
     *
     * @param info
     * @param request
     */
    @RequestMapping(value = "/profile/update", method = RequestMethod.PUT)
    public ApiResponse<?> updateProfile(@RequestBody SubjectInfoForUpdate info, HttpServletRequest request) {

        String logMsg = "PUT /profile/update";
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity user = authService.checkLoggedAndGet(request, logctx);
            service.updateProfile(user, info);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }

    }

    /**
     * список сообщений со стены заданного пользователя
     *
     * @param userId
     * @param request
     * @return
     */
    @RequestMapping(value = "/board/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<BoardMessageInfo>> getBoard(@PathVariable("userId") int userId, HttpServletRequest request) {

        String logMsg = "GET /board/" + userId;
        LoggerContext logctx = buildLogger().start(logMsg);
        try {
            authService.checkToken(request);

            List<BoardMessageInfo> result = service.getBoardByUserId(userId, 0, Integer.MAX_VALUE);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * список сообщений со стены текущего пользователя
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/board", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<?> getMyBoard(HttpServletRequest request) {

        String logMsg = "GET /board";
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            List<BoardMessageInfo> result = service.getBoardByUserId(subj.getId(), 0, Integer.MAX_VALUE);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * добавить сообщение на стену текущего пользователя
     *
     * @param newMessage
     * @param request
     */
    @RequestMapping(value = "/board/update", method = RequestMethod.POST)
    public ApiResponse<?> updateBoard(@RequestBody BoardMessage newMessage, HttpServletRequest request) {

        String logMsg = "POST /board/update";
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            service.addBoardMessage(subj, newMessage);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * группированная лента пользователя (первый экран ленты).
     * В списке:
     * 1) личные группы пользователя, (если есть)
     * 2) группа "подписки" (если есть)
     * 3) группа "в закладках" (если есть)
     * 4) группа для каждого подписанного тега (если есть, а если нет подписок, то все подряд начиная с последнего сообщения)
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/grouped", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<GroupInfo>> getLentaGroups(HttpServletRequest request) {

        String logMsg = "GET /grouped";
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            List<GroupInfo> result = service.getLentaGroups(subj);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }

    }

    /**
     * список профилей в конкретной группе
     *
     * @param groupId
     * @param request
     * @return
     */
    @RequestMapping(value = "/profiles/group/{groupId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<ProfileItem>> getLentaProfilesByGroup(@PathVariable("groupId") int groupId, HttpServletRequest request) {

        String logMsg = "GET /profiles/group/" + groupId;
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            List<ProfileItem> result = service.getLentaProfilesByGroup(subj, groupId);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * список профилей, чьи сообщения в закладках
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/profiles/bookmarked", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<ProfileItem>> getLentaProfilesByBookmarks(HttpServletRequest request) {

        String logMsg = "GET /profiles/bookmarked";
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            List<ProfileItem> result = service.getLentaProfilesByBookmarks(subj);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * список профилей, на которые подписан юзер
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/profiles/subscribed", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<ProfileItem>> getLentaProfilesBySubscribed(HttpServletRequest request) {

        String logMsg = "---> GET /profiles/subscribed";
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            List<ProfileItem> result = service.getLentaProfilesBySubscribtions(subj);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * список профилей по тагу
     *
     * @param tagId
     * @param request
     * @return
     */
    @RequestMapping(value = "/profiles/tag/{tagId}",
            //params = {"page"},
            method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<PageableResponse<ProfileItem>> getLentaProfilesByTags(@PathVariable("tagId") int tagId,
                                                                             //@RequestParam ("page") Integer lastPage,
                                                                             HttpServletRequest request) {

        String logMsg = "GET /profiles/tag/" + tagId;
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);
            PageableResponse<ProfileItem> result = service.getLentaProfilesByTagsForUser(subj, tagId, 0);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * сообщения в группе
     *
     * @param groupId
     * @param request
     * @return
     */
    @RequestMapping(value = "/messages/group/{groupId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<BoardMessageInfo>> getMessagesByGroup(@PathVariable("groupId") int groupId,
                                                                  HttpServletRequest request) {

        String logMsg = "GET /messages/group/" + groupId;
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            List<BoardMessageInfo> result = service.getMessagesByGroup(subj, groupId);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * сообщения в закладках
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/messages/bookmarked", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<BoardMessageInfo>> getMessagesByBookmarks(HttpServletRequest request) {

        String logMsg = "GET /messages/bookmarked";
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            List<BoardMessageInfo> result = service.getMessagesBookedByUser(subj);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * cooбщения из профилей, на которые подписался пользователь
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/messages/subscribed", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<BoardMessageInfo>> getMessagesBySubscribed(HttpServletRequest request) {

        String logMsg = "GET /messages/subscribed";
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            List<BoardMessageInfo> result = service.getMessagesBySubscriptions(subj);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * сообщения для тага
     *
     * @param tagId
     * @param request
     * @return
     */
    @RequestMapping(value = "/messages/tag/{tagId}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<BoardMessageInfo>> getMessagesByTags(@PathVariable("tagId") int tagId, HttpServletRequest request) {

        String logMsg = "GET /messages/tag/" + tagId;
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            List<BoardMessageInfo> result = service.getMessagesByTagsForUser(subj, tagId);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * приписать профайл к группе
     *
     * @param groupId
     * @param profileId
     * @param request
     */
    @RequestMapping(value = "/group/{groupId}/add/{profileId}", method = RequestMethod.PUT)
    @ResponseBody
    public ApiResponse<?> setGroupForProfile(@PathVariable("groupId") int groupId,
                                             @PathVariable("profileId") int profileId,
                                             HttpServletRequest request) {

        String logMsg = String.format("PUT /group/{%s}/add/{%s}", String.valueOf(groupId), String.valueOf(profileId));
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            service.setGroupForProfile(subj, groupId, profileId);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /*
    //CRUD for groups
     */

    /**
     * добавить новую группу для текущего пользователя
     *
     * @param groupName
     * @param request
     */
    @RequestMapping(value = "/group/add/{name}", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<?> addGroup(@PathVariable("name") String groupName,
                                   HttpServletRequest request) {

        String logMsg = String.format("POST /group/add/{%s}", String.valueOf(groupName));
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            service.addGroup(subj, groupName);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * преименование группы для текущего пользователя. если владелец группы не текущий пользователь - будет exсeption
     *
     * @param groupId
     * @param groupName
     * @param request
     */
    @RequestMapping(value = "/group/{groupId}/rename/{name}", method = RequestMethod.PUT)
    @ResponseBody
    public ApiResponse<?> renameGroup(@PathVariable("groupId") Integer groupId,
                                      @PathVariable("name") String groupName,
                                      HttpServletRequest request) {

        String logMsg = String.format("PUT /group/%s/rename/{%s}", String.valueOf(groupId), String.valueOf(groupName));
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            service.renameGroup(subj, groupId, groupName);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }

    }

    /**
     * удаление группы.
     * если владелец группы не текущий пользователь - будет exсeption
     *
     * @param groupId
     * @param request
     */
    @RequestMapping(value = "/group/{groupId}/delete", method = RequestMethod.PUT)
    @ResponseBody
    public ApiResponse<?> deleteGroup(@PathVariable("groupId") Integer groupId,
                                      HttpServletRequest request) {

        String logMsg = String.format("PUT /group/%s/delete", String.valueOf(groupId));
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            service.deleteGroup(subj, groupId);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * список групп текущего пользователя
     *
     * @param request - запрос с токеном
     * @return список описаний групп
     */
    @RequestMapping(value = "/group", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<GroupInfo>> listGroup(HttpServletRequest request) {

        String logMsg = "GET /group";
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            List<GroupInfo> result = service.listGroup(subj);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * подписка/отписка на сообщения профиля (инверсия подписки)
     *
     * @param profileId - id профиля
     * @param request   - запрос с токеном
     */
    @RequestMapping(value = "/profile/{id}/subscribe-toggle", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<?> subscribeProfile(@PathVariable("id") Integer profileId,
                                           HttpServletRequest request) {

        String logMsg = String.format("POST /profile/%s/subscribeToggle", profileId);
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            service.subscribeToggle(subj, profileId);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * отметить сообщение как прочитанное
     *
     * @param messageId
     * @param request
     */
    @RequestMapping(value = "/message/{id}/read", method = RequestMethod.PUT)
    @ResponseBody
    public ApiResponse<?> markMessageAsRead(@PathVariable("id") Integer messageId,
                                            HttpServletRequest request) {

        String logMsg = String.format("PUT /message/%s/markread", messageId);
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            service.markMessageAsRead(logctx, subj, messageId);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * сообщение в закладки
     *
     * @param messageId
     * @param request
     */
    @RequestMapping(value = "/message/{id}/bookmark", method = RequestMethod.PUT)
    @ResponseBody
    public ApiResponse<?> bookmarkMessage(@PathVariable("id") Integer messageId,
                                          HttpServletRequest request) {

        String logMsg = String.format("PUT /message/%s/bookmark", messageId);
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            service.bookmarkMessage(logctx, subj, messageId);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * удалить сообщение из закладок
     *
     * @param messageId
     * @param request
     */
    @RequestMapping(value = "/message/{id}/unbookmark", method = RequestMethod.PUT)
    @ResponseBody
    public ApiResponse<?> unbookmarkMessage(@PathVariable("id") Integer messageId,
                                            HttpServletRequest request) {

        String logMsg = String.format("PUT /message/%s/unbookmark", messageId);
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            service.unbookmarkMessage(logctx, subj, messageId);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * получить одно сообщение по id
     *
     * @param messageId
     * @param request
     */
    @RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<?> getMessage(@PathVariable("id") Integer messageId,
                                     HttpServletRequest request) {

        String logMsg = String.format("GET /message/%s", messageId);
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            BoardMessageInfo result = service.getMessageById(logctx, subj, messageId);

            return ApiResponse.instance(result);
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * поделиться сообщением
     *
     * @param boardMessageId
     * @param request
     */
    @RequestMapping(value = "/message/{id}/share", method = RequestMethod.PUT)
    @ResponseBody
    public ApiResponse<?> shareMessage(@PathVariable("id") Integer boardMessageId,
                                       HttpServletRequest request) {

        String logMsg = String.format("GET /message/%s/share", boardMessageId);
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            service.shareMessage(logctx, subj, boardMessageId);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    /**
     * notify на сообщения с типом EVENT
     *
     * @param boardMessageId
     * @param request
     */
    @RequestMapping(value = "/message/{boardId}/notify", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<?> notifyMessage(@PathVariable("boardId") Integer boardMessageId,
                                        HttpServletRequest request) {

        String logMsg = String.format("GET /message/%s/notify", boardMessageId);
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            SubjectEntity subj = authService.checkLoggedAndGet(request, logctx);

            service.notifyMessage(logctx, subj, boardMessageId);

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }

    private LoggerContext buildLogger() {

        return new LoggerImpl("", logger, logLevel);
    }

    //todo картинки загрузка-отгрузка
    // todo фильтры
    // todo поиск
    // todo "кто рядом" - кнопка в ленте
}
