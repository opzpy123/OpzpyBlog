package com.opzpy123.mypeojectdemo.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class PageAlert {

    public static void Alert(String msg, HttpServletResponse response) throws IOException {
//        response.reset();
//        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-type", "text/html;charset=UTF-8");
//        PrintWriter out = null;
//        try {
//            out = response.getWriter();
//            out.print(msg);
//           out.flush();
//        } catch (IOException e) {
//            System.out.println("IO在pageAlert里报错啦");
//        } finally {
//            IOUtils.closeQuietly(out);
//        }
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        ServletOutputStream outs = response.getOutputStream();
        OutputStreamWriter ow = new OutputStreamWriter(outs, "UTF-8");
        ow.write(msg);
        ow.flush();
        ow.close();


    }
}
