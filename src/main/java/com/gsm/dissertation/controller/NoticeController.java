package com.gsm.dissertation.controller;

import com.gsm.dissertation.model.GuideTeacher;
import com.gsm.dissertation.model.Notice;
import com.gsm.dissertation.model.Teacher;
import com.gsm.dissertation.service.GuideTeacherService;
import com.gsm.dissertation.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class NoticeController {
    @Autowired
    private GuideTeacherService guideTeacherService;
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/noticesendtostudent")
    public String noticeSendToStudent(Model model, HttpSession session){
        Teacher teacher = (Teacher) session.getAttribute("user");
        List<GuideTeacher> guideTeacherList = guideTeacherService.getGuideTeacherByAccount(teacher.getAccount());
        model.addAttribute("notice",new Notice());
        model.addAttribute("guideTeacherList",guideTeacherList);
        return "noticesendtostudent";
    }

    @PostMapping("/noticesendtostudent")
    public String sendToStudent(Notice notice, HttpSession session, RedirectAttributes attr){
        if (!"".equals(notice.getGetUserAccount())){
            Teacher teacher = (Teacher) session.getAttribute("user");
            notice.setSendUserAccount(teacher.getAccount());
            notice.setSendUserName(teacher.getName());
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
            notice.setSendTime(dateTime.format(formatter));
            notice.setStatus("0");
            noticeService.save(notice);
            attr.addFlashAttribute("ok","发送成功");
        }
        return "redirect:/noticesendtostudent";
    }
}
