package com.gsm.dissertation.controller;


import com.gsm.dissertation.dao.ParameterRepository;
import com.gsm.dissertation.dao.TopicReleaseRepository;
import com.gsm.dissertation.model.*;
import com.gsm.dissertation.service.GuideTeacherService;
import com.gsm.dissertation.service.NoticeService;
import com.gsm.dissertation.service.TopicSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    TopicSelectService TopicSelectService;
    @Autowired
    GuideTeacherService guideTeacherService;
    @Autowired
    NoticeService noticeService;

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
            if (!"".equals(topicRelease.getTopicName())){
                topicRelease.setT_status("0");
                Teacher teacher = (Teacher) session.getAttribute("user");
                topicRelease.setTeacherAccount(teacher.getAccount());
                topicRelease.setTeacherName(teacher.getName());
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
        if (TopicSelectService.findCountByStudent(student.getAccount()) == 0){
            TopicRelease topicRelease = topicReleaseRepository.findById(Integer.parseInt(tid)).get();
            TopicSelect topicSelect = new TopicSelect();
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
            topicSelect.setStudentAccount(student.getAccount());
            topicSelect.setStudentName(student.getName());
            topicSelect.setStatus("0");
            topicSelect.setApplyTime(dateTime.format(formatter));
            topicSelect.setTopicid(tid);
            topicSelect.setTopicName(topicRelease.getTopicName());
            topicSelect.setTeacherAccount(topicRelease.getTeacherAccount());
            topicSelect.setTeacherName(topicRelease.getTeacherName());
            TopicSelectService.save(topicSelect);
            attr.addFlashAttribute("ok","选题成功，等待教师审核！");
            return "redirect:/topicselect";
        }else{
            attr.addFlashAttribute("error","已经选题，不可重复选题，如需修改请联系管理员。");
            return "redirect:/topicselect";
        }
    }

    @GetMapping("/applyapproval/{id}")
    public String applyApproval(@PathVariable("id") Integer id, HttpSession session){
        if(id!=null){
            Teacher teacher = (Teacher) session.getAttribute("user");
            TopicSelectService.updateStatusById(id);
            TopicSelect topicSelect = TopicSelectService.findById(id);
            //同意审批后添加学生的选题信息到指导教师表
            GuideTeacher guideTeacher=new GuideTeacher();
            guideTeacher.setTeacherAccount(teacher.getAccount());
            guideTeacher.setTeacherName(teacher.getName());
            guideTeacher.setStudentAccount(topicSelect.getStudentAccount());
            guideTeacher.setStudentName(topicSelect.getStudentName());
            guideTeacher.setTopicId(topicSelect.getTopicId());
            guideTeacher.setTopicName(topicSelect.getTopicName());
            guideTeacher.setStatus(0);
            guideTeacherService.save(guideTeacher);
            //向学生发送通知
            Notice notice = new Notice();
            notice.setGetUserAccount(topicSelect.getStudentAccount());
            notice.setGetUserName(topicSelect.getStudentName());
            notice.setSendUserAccount(teacher.getAccount());
            notice.setSendUserName(teacher.getName());
            notice.setSendSubject("选题成功");
            notice.setSendContent("教师通过你的论题申请！");
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
            notice.setSendTime(dateTime.format(formatter));
            notice.setStatus("0");
            noticeService.save(notice);
        }
        return "redirect:/techmain";
    }

    @GetMapping("/applyrefuse/{id}")
    public String applyRefuse(@PathVariable("id") Integer id, HttpSession session){
        if(id!=null && !"".equals(id)){
            Teacher teacher = (Teacher) session.getAttribute("user");
            TopicSelectService.updateStatusById1(id);
            TopicSelect topicSelect = TopicSelectService.findById(id);
            //拒绝学生申请后向学生发送通知
            Notice notice = new Notice();
            notice.setGetUserAccount(topicSelect.getStudentAccount());
            notice.setGetUserName(topicSelect.getStudentName());
            notice.setSendUserAccount(teacher.getAccount());
            notice.setSendUserName(teacher.getName());
            notice.setSendSubject("选题失败");
            notice.setSendContent("教师拒绝了你的论题申请！");
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
            notice.setSendTime(dateTime.format(formatter));
            notice.setStatus("0");
            noticeService.save(notice);
        }
        return "redirect:/techmain";
    }
}
