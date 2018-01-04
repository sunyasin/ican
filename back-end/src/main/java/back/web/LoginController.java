package back.web;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import org.apache.log4j.Logger;
import org.ideacreation.can.common.model.json.ApiResponse;
import org.ideacreation.can.common.model.json.AuthPrincipal;
import org.ideacreation.can.common.model.json.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import back.db.SubjRepository;
import back.entity.SubjectEntity;
import back.entity.TokenEntity;
import back.service.AuthService;

import static javax.servlet.http.HttpServletResponse.SC_MOVED_PERMANENTLY;
import static org.ideacreation.can.common.CommonConst.TOKEN_HEADER_NAME;
import static org.ideacreation.can.common.model.enums.ErrorDescription.E_AUTH;

/**
 * Контроллер аутентификации.
 */
@RestController
@RequestMapping("/api")
public class LoginController {

    private Logger log = Logger.getLogger(LoginController.class);

    private ShaPasswordEncoder tokenEncoder;
    private JsonNodeFactory jsonNodeFactory;

    @Autowired
    AuthService authService;

    @Autowired
    SubjRepository subjRepository;

    public LoginController() {
        tokenEncoder = new ShaPasswordEncoder();
        tokenEncoder.setEncodeHashAsBase64(true);
        jsonNodeFactory = JsonNodeFactory.instance;
    }

    @RequestMapping(value = "/login",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    @ResponseBody
    public ApiResponse<Token> login(@RequestBody AuthPrincipal json, HttpServletRequest request, HttpServletResponse response) {
        log.info("--->  LOGIN username=[" + json.getUsername() + "]");
        try {
            SubjectEntity user = subjRepository.findByLoginAndPass(json.getUsername(), json.getPassword());
            if (user == null) {
                response.setStatus(SC_MOVED_PERMANENTLY);
                E_AUTH.throwExceptionWithMessage("Access denied");
            }

            String token = tokenEncoder.encodePassword(getStringForHash(user), null);

            authService.updateUserToken(user, token);

            log.debug("<--- updated token " + token);
            Token tok = new Token();
            tok.setToken(token);
            return ApiResponse.instance(tok);
        } catch (Exception e) {
            log.error("!!!--- Login error: " + e.getMessage(), e);
            response.setStatus(SC_MOVED_PERMANENTLY);
            return ApiResponse.error(e);
        }
    }


    /**
     * Выйти из системы.
     *
     * @param rq {@link HttpServletRequest}
     * @param rs {@link HttpServletResponse}
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<?> logout(HttpServletRequest rq, HttpServletResponse rs) {

        log.info("---> logout token=" + rq.getHeader(TOKEN_HEADER_NAME));

        try {
            TokenEntity entity = authService.checkToken(rq.getHeader(TOKEN_HEADER_NAME), null);

            authService.logout(entity);
            return ApiResponse.instance();
        } catch (Exception e) {
            log.error("!!!--- logout error: " + e.getMessage(), e);
            return ApiResponse.error(e);
        }
    }

    private static String getStringForHash(SubjectEntity user) {
        return user.getId() + user.getLogin() + user.getPass();//todo add later:  + LocalDate.now().toString();
    }

}
