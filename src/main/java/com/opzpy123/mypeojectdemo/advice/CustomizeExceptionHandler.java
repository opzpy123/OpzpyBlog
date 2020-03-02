package com.opzpy123.mypeojectdemo.advice;

import com.opzpy123.mypeojectdemo.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handle( Throwable e, Model model){
        if(e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());
        }else {
            model.addAttribute("message", "访问的页面不存在！");
        }
        return new ModelAndView("error");
    }


}
