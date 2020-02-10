package com.gsm.dissertation.controller;


import com.gsm.dissertation.dao.ParameterRepository;
import com.gsm.dissertation.dao.TopicReleaseRepository;
import com.gsm.dissertation.dao.TopicSelectRepository;
import com.gsm.dissertation.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.Convert;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class TopicController {
    @Autowired
    TopicReleaseRepository topicReleaseRepository;
    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    TopicSelectRepository topicSelectRepository;

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
                topicRelease.setT_status("0");
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

    @GetMapping("/topicselect")
    public String topicSelect(Model model, HttpSession session){
        Users student = (Users)session.getAttribute("user");
        List<TopicRelease> topicReleaseList =
                topicReleaseRepository.findTopicReleaseByMajor(student.getMajor());
        model.addAttribute("topicReleaseList",topicReleaseList);
        return "topicselect";
    }

    @GetMapping("/selecttopic/{tid}")
    public String selectTopic(@PathVariable("tid") String tid, HttpSession session, RedirectAttributes attr){
        Users student = (Users) session.getAttribute("user");
        if (topicSelectRepository.findCountByStudent(student.getUid().toString()) == 0){
            TopicRelease topicRelease = topicReleaseRepository.findById(Integer.parseInt(tid)).get();
            TopicSelect topicSelect = new TopicSelect();
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
            topicSelect.setsId(student.getUid().toString());
            topicSelect.setsName(student.getName());
            topicSelect.setStatus("0");
            topicSelect.setsTime(dateTime.format(formatter));
            topicSelect.settId(topicRelease.getTid().toString());
            topicSelect.settName(topicRelease.getT_topicname());
            topicSelect.setT_TId(topicRelease.getT_teacher());
            topicSelect.setT_TName(topicRelease.getT_name());
            topicSelectRepository.save(topicSelect);
            attr.addFlashAttribute("ok","选题成功，等待教师审核！");
            return "redirect:/topicselect";
        }else{
            attr.addFlashAttribute("error","已经选题，不可重复选题，如需修改请联系管理员。");
            return "redirect:/topicselect";
        }
    }



//    @GetMapping("/applyapproval/{id}")
//    public String applyApproval(@PathVariable("id") String id, HttpSession session, RedirectAttributes attr){
//
//    }
}
