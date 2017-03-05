import java.io.*;
import java.nio.channels.Channels;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.model.*;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;


/**
 * Created by schrodinger on 3/4/2017.
 */
public class uploadController extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        ServletFileUpload upload;
        FileItemIterator iterator;
        FileItemStream item;
        InputStream inputStream = null;
        try {
            upload = new ServletFileUpload();
            resp.setContentType("text/plain");

            iterator = upload.getItemIterator(req);
            while (iterator.hasNext()) {
                item = iterator.next();
                if (item.isFormField()) {
                    // input thuong
                } else {
                    String fileName = FilenameUtils.getName(item.getName());
                    InputStream fileContentInputStream = item.openStream();

                    // luu file tam
                    java.io.File newFile = new java.io.File("/tmpfiles/"+fileName);
                    OutputStream outputStream = new FileOutputStream(newFile);
                    IOUtils.copy(fileContentInputStream, outputStream);
                    outputStream.close();

                    // chuyen den google drive
                    Drive driveService = DriveService.getDriveService();
                    File fileMetadata = new File();
                    fileMetadata.setName(fileName);
                    FileContent mediaContent = new FileContent("", newFile);
                    File file = driveService.files().create(fileMetadata, mediaContent)
                            .setFields("id")
                            .execute();
                    System.out.println("File ID: " + file.getId());

                    resp.getWriter().write("{\"success\":\"true\"}");
                    newFile.delete();
                }
            }
        } catch (Exception ex) {
        	 resp.getWriter().write("{\"success\":\"false\"}");
            throw new ServletException(ex);
        }

    }
}
