package com.gsm.dissertation.controller;

import com.gsm.dissertation.model.GuideTeacher;
import com.gsm.dissertation.model.Notice;
import com.gsm.dissertation.model.Teacher;
import com.gsm.dissertation.model.Users;
import com.gsm.dissertation.service.GuideTeacherService;
import com.gsm.dissertation.service.NoticeService;
import com.gsm.dissertation.service.TeacherService;
import com.gsm.dissertation.service.UserService;
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
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;

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
            //如果没有使用快捷联系人发送时，需要查询出对应账号的用户的名字
            if("".equals(notice.getGetUserName())){
                Users student = userService.findUsersByAccount(notice.getGetUserAccount());
                if(student.getAccount() == null){
                    Teacher getTeacher = teacherService.findTeacherByAccount(notice.getGetUserAccount());
                    notice.setGetUserName(getTeacher.getName());
                }else {
                    notice.setGetUserName(student.getName());
                }
            }
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

    @GetMapping("/noticesendtoteacher")
    public String noticeSendToTeacher(Model model, HttpSession session){
        Users student = (Users) session.getAttribute("user");
        List<GuideTeacher> guideTeacherList = guideTeacherService.getGuideTeacherByStudentAccount(student.getAccount());
        model.addAttribute("notice",new Notice());
        model.addAttribute("guideTeacherList",guideTeacherList);
        return "noticesendtoteacher";
    }

    @PostMapping("/noticesendtoteacher")
    public String sendToTeacher(Notice notice, HttpSession session, RedirectAttributes attr){
        if (!"".equals(notice.getGetUserAccount())){
            Users student = (Users) session.getAttribute("user");
            //如果没有使用快捷联系人发送时，需要查询出对应账号的用户的名字
            if("".equals(notice.getGetUserName())){
                Users getStudent = userService.findUsersByAccount(notice.getGetUserAccount());
                if(student.getAccount() == null){
                    Teacher getTeacher = teacherService.findTeacherByAccount(notice.getGetUserAccount());
                    notice.setGetUserName(getTeacher.getName());
                }else {
                    notice.setGetUserName(getStudent.getName());
                }
            }
            notice.setSendUserAccount(student.getAccount());
            notice.setSendUserName(student.getName());
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
            notice.setSendTime(dateTime.format(formatter));
            notice.setStatus("0");
            noticeService.save(notice);
            attr.addFlashAttribute("ok","发送成功");
        }
        return "redirect:/noticesendtoteacher";
    }

    @GetMapping("/noticecenterstudent")
    public String noticeCenterStudent(Model model, HttpSession session){
        Users student = (Users)session.getAttribute("user");
        List<Notice> noticeList = noticeService.findAllByGetUserAccount(student.getAccount());
        model.addAttribute("noticeList",noticeList);
        return "noticecenterstudent";
    }

    @GetMapping("/noticecenterteacher")
    public String noticeCenterTeacher(Model model, HttpSession session){
        Teacher teacher = (Teacher)session.getAttribute("user");
        List<Notice> noticeList = noticeService.findAllByGetUserAccount(teacher.getAccount());
        model.addAttribute("noticeList",noticeList);
        return "noticecenterstudent";
    }
}
