package back.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {

    private static final Logger log = Logger.getLogger(FileService.class);
    public static final int TARGET_FILE_COPY_BUFFER = 4 * 1024 * 1024;
    //    private String resolveStorageDir = "/storage";
    @Value("${filestorage.dir}")
    private String storageDir;


    public void store(String originalFilename, long size, InputStream inStream) throws IOException {

        String path = getPathTo(originalFilename);
        try (BufferedOutputStream outStream = new BufferedOutputStream(new FileOutputStream(new File(path)), TARGET_FILE_COPY_BUFFER)) {
            FileCopyUtils.copy(inStream, outStream);
        }
    }

    public File load(String fileName) {
        String path = getPathTo(fileName);
        return new File(path);
    }

    private String getPathTo(String filename) {
        return storageDir + (storageDir.endsWith("/") ? "" : "/") + filename;
    }
}
