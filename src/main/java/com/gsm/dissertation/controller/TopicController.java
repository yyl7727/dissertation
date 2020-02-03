package com.gsm.dissertation.controller;


import com.gsm.dissertation.dao.ParameterRepository;
import com.gsm.dissertation.dao.TopicReleaseRepository;
import com.gsm.dissertation.model.Parameter;
import com.gsm.dissertation.model.Teacher;
import com.gsm.dissertation.model.TopicRelease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class TopicController {
    @Autowired
    TopicReleaseRepository topicReleaseRepository;
    @Autowired
    ParameterRepository parameterRepository;

    @GetMapping("/topicrelease")
    public String stuList(Model model){
        List<Parameter> list_Parameter = parameterRepository.getParametersByType("0004");
        model.addAttribute("topicRelease",new TopicRelease());
        model.addAttribute("majorlist",list_Parameter);
        return "topicrelease";
    }

    @PostMapping("/topicrelease")
    public String stuList(TopicRelease topicRelease, HttpSession session, RedirectAttributes attr){
        try{
            //课题名称不为空时
            if (topicRelease.getT_topicname()!=""){
                topicRelease.setT_status(0);
                Teacher teacher = (Teacher) session.getAttribute("user");
                topicRelease.setT_teacher(teacher.getAccount());
                topicRelease.setT_name(teacher.getName());
                topicReleaseRepository.save(topicRelease);
                attr.addFlashAttribute("ok","课题发布成功");
                return "redirect:/topicrelease";
            }else{
                attr.addFlashAttribute("error","课题发布失败，课题名称不能为空");
                return "redirect:/topicrelease";
            }
        }catch (Exception ex){
            attr.addFlashAttribute("error","课题发布失败："+ex.getMessage());
            return "redirect:/topicrelease";
        }
    }
}
