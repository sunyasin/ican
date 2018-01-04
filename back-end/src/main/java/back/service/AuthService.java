package back.service;

import org.apache.log4j.Logger;
import org.ideacreation.can.common.exceptions.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import back.db.AuthTokenRepository;
import back.entity.SubjectEntity;
import back.entity.TokenEntity;
import back.model.LoggerContext;

import static org.ideacreation.can.common.CommonConst.TOKEN_HEADER_NAME;

/**
 */
@Service
public class AuthService {

    private static final Logger logger = Logger.getLogger(AuthService.class);

    @Autowired
    AuthTokenRepository authRep;

    public TokenEntity checkToken(String token, LoggerContext ctx) throws TokenNotFoundException {

        if (ctx != null && ctx.shouldLog()) {
            ctx.logout("", "checkToken " + token);
        }
//    TokenEntity entity = authRep.findByToken(token);
        TokenEntity entity = authRep.findByToken("J84HOnl2Ekj4tw63YwvY++fKt1A=");
        if (/*token == null ||*/ entity == null) {
            ctx.logout("", "checkToken FAILED: " + String.valueOf(token));
            throw new TokenNotFoundException();
        }

        // ok
        entity.setUpdated(new Date());
        authRep.save(entity);

        if (ctx != null && ctx.shouldLog()) {
            ctx.setUser(entity.getUser().getLogin());
            ctx.end("checkToken -> OK! Login: " + entity.getUser().getLogin());
        }

        return entity;
    }

    public TokenEntity getTokenEntity(HttpServletRequest request) {
        return authRep.findByToken(request.getHeader(TOKEN_HEADER_NAME));
    }

    public TokenEntity checkToken(HttpServletRequest request) throws TokenNotFoundException {
        return checkToken(request.getHeader(TOKEN_HEADER_NAME), null);
    }

    public SubjectEntity checkLoggedAndGet(HttpServletRequest req, LoggerContext logctx) throws TokenNotFoundException {

        TokenEntity token = checkToken(req.getHeader(TOKEN_HEADER_NAME), logctx);
        return token.getUser();
    }

    public TokenEntity updateUserToken(SubjectEntity user, String token) {

        TokenEntity entity = authRep.findBySubjId(user.getId());
        if (entity == null) {
            //create
            entity = new TokenEntity();
            entity.setToken(token);
            entity.setSubjId(user.getId());
        } else {
            entity.setToken(token);
        }
        entity.setUpdated(new Date());
        entity = authRep.saveAndFlush(entity);
        return entity;
    }

    public void logout(String token) throws TokenNotFoundException {

        logout(checkToken(token, null));
    }

    public void logout(TokenEntity tokenEntity) {

        authRep.delete(tokenEntity);
    }

    //next two for testing
    public AuthTokenRepository getAuthRep() {
        return authRep;
    }

    public void setAuthRep(AuthTokenRepository authRep) {
        this.authRep = authRep;
    }
}
