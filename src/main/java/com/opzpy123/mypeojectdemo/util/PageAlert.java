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

    public static void Alert(String msg, HttpServletResponse response) {

        OutputStreamWriter ow=null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            ServletOutputStream outs = response.getOutputStream();
            ow = new OutputStreamWriter(outs, "UTF-8");
            ow.write(msg);
            ow.flush();
            ow.close();
        }catch (Exception e){
            System.out.println("alert方法异常"+e.getMessage());
        }



    }
}
