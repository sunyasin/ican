package back.web;

import org.apache.log4j.Logger;
import org.ideacreation.can.common.model.enums.ErrorDescription;
import org.ideacreation.can.common.model.json.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;

import back.entity.SubjectEntity;
import back.model.LoggerContext;
import back.model.LoggerImpl;
import back.service.AuthService;
import back.service.FileService;
import back.service.MainService;

import static back.model.LoggerImpl.Level.INFO;
import static org.apache.http.HttpStatus.SC_NO_CONTENT;

/**
 * Контроллер аутентификации.
 */
@RestController
@RequestMapping("/api")
@Produces("application/json")
public class FileController {

    private static final Logger logger = Logger.getLogger(FileController.class);
    private LoggerImpl.Level logLevel = INFO;

    @Autowired
    AuthService authService;

    @Autowired
    MainService mainService;

    @Autowired
    FileService fileService;

    private Logger log = Logger.getLogger(FileController.class);

    @RequestMapping(value = "/file/upload/{profileId}",
            method = RequestMethod.POST,
            produces = "application/json")
    @ResponseBody
    public ApiResponse upload(@RequestParam(name = "file") MultipartFile file,
                              @PathVariable("profileId") int profileId,
                              HttpServletRequest request) {

        String logMsg = String.format("POST /file/upload/%s", profileId);
        LoggerContext logctx = buildLogger().start(logMsg);

        if (file == null || file.isEmpty()) {
            return ApiResponse.error(ErrorDescription.E_FILE_UPLOAD_EMPTY);
        }

        try {
            SubjectEntity user = authService.checkLoggedAndGet(request, logctx);

            fileService.store(file.getOriginalFilename(), file.getSize(), file.getInputStream());

            mainService.updateProfileLogo(user, file.getOriginalFilename());

            return ApiResponse.instance();
        } catch (Exception e) {
            return ApiResponse.error(e);
        } finally {
            logctx.end(logMsg);
        }
    }


    @GetMapping(value = "/file/download")
    @ResponseBody
    public void download(@RequestParam(name = "filename") String fileName,
                         HttpServletRequest request,
                         HttpServletResponse response) throws IOException {

        String logMsg = String.format("GET /file/download?filename=%s", fileName);
        LoggerContext logctx = buildLogger().start(logMsg);

        try {
            authService.checkLoggedAndGet(request, logctx);

            fileName = fileName.replaceAll("(\\.\\.)|\\/|\\\\", "");
            File file = fileService.load(fileName);
            if (!file.exists()) {
                ErrorDescription.E_FILE_NOT_FOUND.throwException();
            }
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);

            /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
                while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
            //  response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
            /* "Content-Disposition : attachment" will be directly download, may provide save as popup, based on your browser setting*/
            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%1$s\"", file.getName()));

            response.setContentLength((int) file.length());

            try (InputStream inputStream = new BufferedInputStream(new FileInputStream(file), FileService.TARGET_FILE_COPY_BUFFER)) {
                FileCopyUtils.copy(inputStream, response.getOutputStream());
            }

        } catch (Exception e) {
            logctx.logout("ERROR", e.toString());
            response.setStatus(SC_NO_CONTENT);
        } finally {
            logctx.end(logMsg);
        }
    }

    private LoggerContext buildLogger() {

        return new LoggerImpl("", logger, logLevel);
    }

}
