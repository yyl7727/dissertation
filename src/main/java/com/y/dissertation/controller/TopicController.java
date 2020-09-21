package com.y.dissertation.controller;


import com.y.dissertation.dao.ParameterRepository;
import com.y.dissertation.dao.TopicReleaseRepository;
import com.y.dissertation.model.*;
import com.y.dissertation.service.*;
import com.y.dissertation.model.*;
import com.y.dissertation.service.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TopicController {
    @Autowired
    TopicReleaseRepository topicReleaseRepository;
    @Autowired
    TopicReleaseService topicReleaseService;
    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    com.y.dissertation.service.TopicSelectService TopicSelectService;
    @Autowired
    GuideTeacherService guideTeacherService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    TopicUploadService topicUploadService;
    @Autowired
    DefenceService defenceService;

    @GetMapping("/topicrelease")
    public String stuList(Model model){
        List<Parameter> list_Parameter = parameterRepository.getParametersByType("0004");
        model.addAttribute("topicRelease",new TopicRelease());
        model.addAttribute("majorlist",list_Parameter);
        return "topicrelease";
    }

    @PostMapping("/topicrelease")
    public String stuList(TopicRelease topicRelease, HttpSession session, RedirectAttributes attr){
        //课题名称不为空时
        if (!"".equals(topicRelease.getTopicName())){
            topicRelease.setT_status("0");
            Teacher teacher = (Teacher) session.getAttribute("user");
            topicRelease.setTeacherAccount(teacher.getAccount());
            topicRelease.setTeacherName(teacher.getName());
            topicRelease.setStudentCount(0);
            String addResult = topicReleaseService.save(topicRelease);
            if("0".equals(addResult)){
                attr.addFlashAttribute("ok","课题发布成功");
                return "redirect:/topicrelease";
            }else {
                attr.addFlashAttribute("error","课题发布失败："+addResult);
                return "redirect:/topicrelease";
            }
        }else{
            attr.addFlashAttribute("error","课题发布失败，课题名称不能为空");
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
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

            //审批通过，课题的已选择人数+1
            String result = topicReleaseService.updateCountById(Integer.parseInt(topicSelect.getTopicId()));

            //向学生发送通知
            Notice notice = new Notice();
            notice.setGetUserAccount(topicSelect.getStudentAccount());
            notice.setGetUserName(topicSelect.getStudentName());
            notice.setSendUserAccount(teacher.getAccount());
            notice.setSendUserName(teacher.getName());
            notice.setSendSubject("选题成功");
            notice.setSendContent("教师通过你的论题申请！");
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            notice.setSendTime(dateTime.format(formatter));
            notice.setStatus("0");
            noticeService.save(notice);
        }
        return "redirect:/techmain";
    }

    @GetMapping("/applyrefuse/{id}")
    public String applyRefuse(@PathVariable("id") Integer id, HttpSession session){
        if(id!=null){
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            notice.setSendTime(dateTime.format(formatter));
            notice.setStatus("0");
            noticeService.save(notice);
        }
        return "redirect:/techmain";
    }

    @GetMapping("/topicupload")
    public String topicUpload(){
        return "topicupload";
    }

    @PostMapping("/topicupload")
    public String topicUploads(@RequestParam("file") MultipartFile file, Model model, HttpSession session){
        //判断文件是否为空
        if (file.isEmpty()) {
            return "redirect:/topicupload";
        }
        Users student = (Users) session.getAttribute("user");
        Integer uploadTime = topicUploadService.getUploadCountByStudentAccount(student.getAccount())+1;

        String fileName = file.getOriginalFilename();
        String savePath = "D:\\dissertation\\" + student.getAccount()+"\\"+uploadTime;
        String saveFile = savePath + "\\" + fileName;
        File file1 = new File(saveFile);
        //判断文件父目录是否存在
        if (!file1.getParentFile().exists()) {
            file1.getParentFile().mkdirs();
        }
        try {
            file.transferTo(file1); //保存文件
        } catch (IOException e) {
            model.addAttribute("error", "保存错误，错误信息:"+e.getMessage());
            return "redirect:/topicupload";
        }
        TopicUpload topicUpload = new TopicUpload();
        topicUpload.setStudentAccount(student.getAccount());
        topicUpload.setStudentName(student.getName());
        topicUpload.setUploadPath(saveFile);
        topicUpload.setUploadTime(uploadTime);
        topicUploadService.save(topicUpload);
        model.addAttribute("ok", "保存成功");
        return "redirect:/topicupload";
    }

    @GetMapping("/topicDownload/{id}")
    public void topicDownload(@PathVariable("id") Integer id, HttpSession session, HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream fis = null;

        TopicUpload topicUpload = topicUploadService.findById(id).get();
        try {
            File file = new File(topicUpload.getUploadPath());
            fis = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
            IOUtils.copy(fis,response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @GetMapping("topicmanage")
    public String topicManage(Model model,HttpSession session){
        Teacher teacher = (Teacher) session.getAttribute("user");
        //通过教师账号获取教师发布的所有课题
        List<TopicRelease> topicReleaseList = topicReleaseService.findAllByTeacherAccount(teacher.getAccount());
        if (topicReleaseList != null && topicReleaseList.size() > 0){
            model.addAttribute("topicReleaseList",topicReleaseList);
        }
        return "topicmanage";
    }

    @GetMapping("topicmanage1")
    public String topicManage1(Model model){
        //获取所有教师发布的所有课题
        List<TopicRelease> topicReleaseList = topicReleaseService.findAll();
        if (topicReleaseList != null && topicReleaseList.size() > 0){
            model.addAttribute("topicReleaseList",topicReleaseList);
        }
        return "topicmanage1";
    }

    @GetMapping("/topicClose/{id}")
    public String topicClose(@PathVariable("id") Integer id, Model model){
        String resultFlag = topicReleaseService.updateTopicStatus(id);
        if ("0".equals(resultFlag)){
            return "redirect:/topicmanage";
        }else {
            model.addAttribute("err",resultFlag);
            return "redirect:/topicmanage";
        }
    }

    @GetMapping("/topicClose1/{id}")
    public String topicClose1(@PathVariable("id") Integer id, Model model){
        String resultFlag = topicReleaseService.updateTopicStatus(id);
        if ("0".equals(resultFlag)){
            return "redirect:/topicmanage1";
        }else {
            model.addAttribute("err",resultFlag);
            return "redirect:/topicmanage1";
        }
    }

    @GetMapping("defenceManage")
    public String defenceManage(Model model, HttpSession session){
        Teacher teacher = (Teacher)session.getAttribute("user");
        List<Defence> defenceList = defenceService.findAllByTeacherAccount(teacher.getAccount());
        if (defenceList.size()>0){
            model.addAttribute("defenceList",defenceList);
        }else {
            model.addAttribute("defenceList",new ArrayList<Defence>());
        }
        return "defencemanage";
    }

    @GetMapping("defenceManage1")
    public String defenceManage1(Model model, HttpSession session){
        List<Defence> defenceList = defenceService.findAll();
        if (defenceList.size()>0){
            model.addAttribute("defenceList",defenceList);
        }else {
            model.addAttribute("defenceList",new ArrayList<Defence>());
        }
        return "defencemanage1";
    }

    @GetMapping("/saveDefence/{account}")
    public String saveDefence(@PathVariable("account") String account, Model model, RedirectAttributes attr){
        int uploadCount = topicUploadService.getUploadCountByStudentAccount(account);
        if (uploadCount == 0){
            attr.addFlashAttribute("message","该学生尚未提交过论文，不能参加答辩！");
            return "redirect:/stulist";
        }
        GuideTeacher guideTeacher = guideTeacherService.getGuideTeacherByStudentAccount(account).get(0);
        Defence defence = new Defence();
        defence.setResult("0");
        defence.setStudentAccount(guideTeacher.getStudentAccount());
        defence.setStudentName(guideTeacher.getStudentName());
        defence.setTeacherAccount(guideTeacher.getTeacherAccount());
        defence.setTeacherName(guideTeacher.getTeacherName());
        defence.setTopicId(guideTeacher.getTopicId());
        defence.setTopicName(guideTeacher.getTopicName());
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        defence.setDefenceDate(dateTime.format(formatter));
        String saveFlag = defenceService.save(defence);
        if ("0".equals(saveFlag)){
            Notice notice = new Notice();
            notice.setGetUserAccount(guideTeacher.getStudentAccount());
            notice.setGetUserName(guideTeacher.getStudentName());
            notice.setSendUserAccount(guideTeacher.getTeacherAccount());
            notice.setSendUserName(guideTeacher.getTeacherName());
            notice.setSendSubject("请准备答辩");
            notice.setSendContent("教师在对你提交论文审核后，同意你于"+defence.getDefenceDate()+"参加答辩，请尽快联系指导教师做好答辩相关事宜。");
            dateTime = LocalDateTime.now();
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            notice.setSendTime(dateTime.format(formatter));
            notice.setStatus("0");
            noticeService.save(notice);
            attr.addFlashAttribute("message","已通知该学生参加答辩");
            return "redirect:/stulist";
        }else {
            attr.addFlashAttribute("message",saveFlag);
            return "redirect:/stulist";
        }
    }

    @GetMapping("/defenceinfo/{id}")
    public String updatedefence(@PathVariable("id") Integer id,Model model){
        Defence defence = defenceService.getDefenceById(id);
        model.addAttribute("defence",defence);
        return "defenceinfo";
    }

    @PostMapping("/updatedefence")
    public String updatedefence(@Valid Defence defence, BindingResult result,Model model, RedirectAttributes attr){
        if (result.hasErrors()){
            model.addAttribute("defence",defence);
            return "defenceinfo";
        }

        String noticeSubject;
        String noticeContent;
        int resultTemp = 0;
        if (!"".equals(defence.getResult())){
            resultTemp = Integer.parseInt(defence.getResult());
        }
        if (resultTemp < 0 || resultTemp > 100){
            model.addAttribute("fail","成绩输入不合理，请检查后重新输入。");
            return "redirect:/defenceinfo/{"+ defence.getId() +"}";
        }else {
            if (resultTemp < 60){
                noticeSubject = "很遗憾，答辩未通过";
                noticeContent = "你的答辩结果，经过指导教师认定后，得分"+resultTemp+"，并未通过。";
            }else if(resultTemp < 80){
                noticeSubject = "恭喜你，答辩通过";
                noticeContent = "你的答辩结果，经过指导教师认定后，得分"+resultTemp+"，成绩良好。";
            }else{
                noticeSubject = "恭喜你，答辩通过";
                noticeContent = "你的答辩结果，经过指导教师认定后，得分"+resultTemp+"，成绩优秀。";
            }
        }
        String saveResult = defenceService.updateResult(defence.getStudentAccount(),String.valueOf(resultTemp));
        if ("0".equals(saveResult)){
            model.addAttribute("success","学生成绩修改成功");
            Notice notice = new Notice();
            notice.setGetUserAccount(defence.getStudentAccount());
            notice.setGetUserName(defence.getStudentName());
            notice.setSendUserAccount(defence.getTeacherAccount());
            notice.setSendUserName(defence.getTeacherName());
            notice.setSendSubject(noticeSubject);
            notice.setSendContent(noticeContent);
            LocalDateTime dateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            notice.setSendTime(dateTime.format(formatter));
            notice.setStatus("0");
            noticeService.save(notice);
            model.addAttribute("defence",defence);
            return "defenceinfo";
        }else {
            model.addAttribute("fail","学生成绩修改失败:"+saveResult);
            model.addAttribute("defence",defence);
            return "defenceinfo";
        }
    }
}
