package com.opzpy123.mypeojectdemo.controller;

import com.opzpy123.mypeojectdemo.bean.Question;
import com.opzpy123.mypeojectdemo.bean.User;
import com.opzpy123.mypeojectdemo.dto.QuestionDTO;
import com.opzpy123.mypeojectdemo.mapper.QuestionMapper;
import com.opzpy123.mypeojectdemo.service.QuestionService;
import com.opzpy123.mypeojectdemo.service.UserService;
import com.opzpy123.mypeojectdemo.util.PageAlert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;


    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id")Long id,Model model){

        questionService.edit(id,model);
        return "publish";
    }

    @GetMapping("/publish/delete/{id}")
    public String delete(@PathVariable(name = "id")Long id,HttpServletResponse response){
        questionService.delete(id);
        String returnMsg = "<script>alert('" + "删除成功！" + "');window.location.href='" + "/profile/questions" + "';</script>";
        PageAlert.Alert(returnMsg, response);
        return null;
    }


    @GetMapping("/publish")
    public String publish(HttpServletRequest request, Model model) {

        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "登录之后才可以发博客");
            return "publish";
        }

        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam("id") Long id,
            HttpServletRequest request,
            Model model,
            HttpServletResponse response
    ) {

        User user = (User)request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "登录之后才可以发博客");
            return "publish";
        }
        //拿到前端传来的问题表单
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());

        question.setId(id);

        String publishMsg = questionService.createOrUpdate(question, model);
        model.addAttribute("publishMsg", publishMsg);
        if (publishMsg.isEmpty()) {
            return "redirect:/";
        } else {
            return "publish";
        }
    }

}
