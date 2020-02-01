package ua.nure.brovenko.SummaryTask4.resources;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import ua.nure.brovenko.SummaryTask4.exception.NotImageException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Command for uploading certificate scan
 * @author Ivan Brovenko
 */
public class FileUploader {

    /**
     * Logger
     */
    private final Logger logger = Logger.getRootLogger();

    /**
     * Method for uploading a file
     * @param request request for parsing
     * @return file name
     * @throws NotImageException if file is not an image
     */
    public String upload(HttpServletRequest request) throws NotImageException {
        FileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
        String fileName = null;

        try {
            List<FileItem> items = servletFileUpload.parseRequest(request);

            for(FileItem f:items){
                String contextType = f.getContentType();
                if(contextType.startsWith("image/")){
                    String absoluteFilePath = PathManager.INSTANCE.getString("upload.location");
                    File uploadDir = new File(absoluteFilePath);
                    File file = File.createTempFile("img",".png",uploadDir);
                    fileName = file.getName();
                    f.write(file);
                } else {
                    throw new NotImageException();
                }

            }
        } catch (FileUploadException e) {
            logger.error(e.getMessage(),e);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        } catch (Exception e) {
            throw new NotImageException();
        }
        return fileName;
    }
}
