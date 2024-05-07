package com.zdww.cdyfzx.techcenter.wepaas.controller;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;


@RequestMapping(value = "/redio")
@Controller
public class VideoController {

    /**
     * 21      * video 视频流一
     * 22      *
     * 23      * @param request
     * 24      * @param response
     * 25
     */
    @RequestMapping(value = "/getVido", method = RequestMethod.GET)
    @ResponseBody
    public void getVido(HttpServletRequest request, HttpServletResponse response) {
        String file = "C:/Users/gf/Videos/Captures/f8706c36f8ee4d956c8bd72a19cc2b2b.mp4";
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[inputStream.available()];
            inputStream.read(data);
            String diskfilename = "final.mp4";
            response.setContentType("video/mp4");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + diskfilename + "\"");
            System.out.println("data.length " + data.length);
            response.setContentLength(data.length);
            response.setHeader("Content-Range", "" + Integer.valueOf(data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/\"9767057-1323779115364\"");
            OutputStream os = response.getOutputStream();

            os.write(data);
            //先声明的流后关掉！
            os.flush();
            os.close();
            inputStream.close();

        } catch (Exception e) {

        }
    }


    /**
     * 55      * video 视频流二
     * 56      *
     * 57      * IOUtils is available in Apache commons io
     * 58
     */
    @RequestMapping(value = "/preview2", method = RequestMethod.GET)
    @ResponseBody
    public void getPreview2(HttpServletResponse response) {
        try {
            File file = new File("C:/Users/gf/Videos/Captures/f8706c36f8ee4d956c8bd72a19cc2b2b.mp4");
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName().replace(" ", "_"));
            InputStream iStream = new FileInputStream(file);
            IOUtils.copy(iStream, response.getOutputStream());
            response.flushBuffer();
        } catch (java.nio.file.NoSuchFileException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    //跳转html
    @RequestMapping(value = "/hello")
    public String index() {
        return "video";
    }
}
