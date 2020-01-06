package com.gsm.dissertation.controller;


import com.gsm.dissertation.model.GuideTeacher;
import com.gsm.dissertation.model.Teacher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TopicController {
    @RequestMapping("/topicrelease")
    public String stuList(Model model, HttpSession session){
        return "topicrelease";
    }
}
