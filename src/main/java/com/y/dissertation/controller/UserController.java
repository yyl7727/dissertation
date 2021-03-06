package com.y.dissertation.controller;

import com.y.dissertation.model.*;
import com.y.dissertation.service.*;
import com.y.dissertation.model.*;
import com.y.dissertation.service.*;
import com.y.dissertation.util.ParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ParameterService parameterService;
    @Autowired
    private GuideTeacherService guideTeacherService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private TopicSelectService topicSelectService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private TopicUploadService topicUploadService;
    @Autowired
    private IntendantService intendantService;

    ParameterUtil parameterUtil = new ParameterUtil();

    //登录类型常量
    /**
     * 登录类型为学生
     */
    private final String TYPESTUDENT = "0001";
    /**
     * 登录类型为教师
     */
    private final String TYPETEACHER = "0002";
    /**
     * 登录类型为管理员
     */
    private final String TYPEADMIN = "0003";

    private final List<Parameter> userTypeList = parameterUtil.getAllUserType();

    @RequestMapping("/stulist")
    public String stuList(Model model,HttpSession session){
        //获取教师管理的学生的学号姓名以及选题状态
        Teacher teacher = (Teacher)session.getAttribute("user");
        List<GuideTeacher> guideTeacherList = guideTeacherService.getGuideTeacherByAccount(teacher.getAccount());
        List<TopicSelect> topicSelectList = topicSelectService.findByTeacher(teacher.getAccount());
        ArrayList<TopicUpload> topicUploadList = new ArrayList<TopicUpload>();
        ArrayList<String> stuAccountList = new ArrayList<String>();
        for(GuideTeacher stuAccount : guideTeacherList){
            int tmpCount = topicUploadService.getUploadCountByStudentAccount(stuAccount.getStudentAccount());
            if (tmpCount > 0){
                //如果学生没有提交过论文则不添加到显示列表中
                stuAccountList.add(stuAccount.getStudentAccount());
            }
        }
        for (String student : stuAccountList){
            topicUploadList.add(topicUploadService.findByTeacher(student).get(0));
        }
        model.addAttribute("guideTeacherList", guideTeacherList);
        model.addAttribute("topicSelectList", topicSelectList);
        model.addAttribute("topicUploadList",topicUploadList);
        return "stulist";
    }

    @RequestMapping("/stulist1")
    public String stuList1(Model model){
        //获取所有教师管理的学生的学号姓名以及选题状态
        List<GuideTeacher> guideTeacherList = guideTeacherService.findAll();
        List<TopicSelect> topicSelectList = topicSelectService.findAll();
        ArrayList<TopicUpload> topicUploadList = new ArrayList<TopicUpload>();
        ArrayList<String> stuAccountList = new ArrayList<String>();
        for(GuideTeacher stuAccount : guideTeacherList){
            int tmpCount = topicUploadService.getUploadCountByStudentAccount(stuAccount.getStudentAccount());
            if (tmpCount > 0){
                //如果学生没有提交过论文则不添加到显示列表中
                stuAccountList.add(stuAccount.getStudentAccount());
            }
        }
        for (String student : stuAccountList){
            topicUploadList.add(topicUploadService.findByTeacher(student).get(0));
        }
        model.addAttribute("guideTeacherList", guideTeacherList);
        model.addAttribute("topicSelectList", topicSelectList);
        model.addAttribute("topicUploadList",topicUploadList);
        return "stulist1";
    }

    @GetMapping("/regist")
    public String edit(Model model){
        Users student = new Users();
        List list_ParameterMajor = parameterService.getParameterByType("0004");
        model.addAttribute("user",student);
        model.addAttribute("major",list_ParameterMajor);
        return "regist";
    }

    @PostMapping("/regist")
    public String save(@Valid Users user, BindingResult result, RedirectAttributes attr){
        try {
            if (result.hasErrors()){
                return "redirect:/regist";
            }
            String optionFlag = userService.save(user);
            if("0".equals(optionFlag)){
                attr.addFlashAttribute("ok","注册成功，马上登陆试试呢~");
                return "redirect:/login";
            }else {
                attr.addFlashAttribute("error",optionFlag);
                return "redirect:/login";
            }
        }catch (Exception ex){
            attr.addFlashAttribute("error","保存失败" + ex.toString());
            return "redirect:/regist";
        }
    }

    @GetMapping("/userdelete/{account}")
    public String delete(@PathVariable("account") String account, RedirectAttributes attr){
        String optionFlag = userService.deleteByAccount(account);
        if ("0".equals(optionFlag)) {
            return "redirect:/stumanage";
        }else {
            attr.addFlashAttribute("error","删除失败"+optionFlag);
            return "redirect:/stumanage";
        }
    }

    @GetMapping("/teacherdelete/{account}")
    public String teacherDelete(@PathVariable("account") String account, RedirectAttributes attr){
        String optionFlag = teacherService.deleteTeacherByAccount(account);
        if ("0".equals(optionFlag)) {
            return "redirect:/teachermanage";
        }else {
            attr.addFlashAttribute("error","删除失败"+optionFlag);
            return "redirect:/teachermanage";
        }
    }

    /**
     * 修改用户状态
     * @param uid 修改用户的id
     * @return
     */
    @GetMapping("/validuser/{uid}")
    public String validuser(@PathVariable("uid") Integer uid){
        Users user = userService.findById(uid);
        user.setValidstate(1 - user.getValidstate());
        return "redirect:/listusers";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("userLogin",new UserLogin());
        List<Parameter> list_Parameter = parameterService.getParameterByType("0001");
        model.addAttribute("loginType",list_Parameter);
        return "login";
    }

    /**
     * 登录
     * @param userLogin 用户登录实体
     * @param result 检查登录实体是否合法
     * @param session session保存登录信息
     * @param attr 传递登录成功与否
     * @return
     */
    @PostMapping("/login")
    public String login(@Valid UserLogin userLogin,
                        BindingResult result, HttpSession session, RedirectAttributes attr){
        if (result.hasErrors()){
            return "redirect:/login";
        }
        Users u;
        Teacher teacher;
        Intendant intendant;

        if (userLogin.getType().equals(TYPESTUDENT)){
            String optionFlag = userService.checkUser(userLogin);
            if("0".equals(optionFlag)) {
                u = userService.findUsersByAccount(userLogin.getAccount());
                LocalDateTime dateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                u.setLasttime(dateTime.format(formatter));
                userService.update(u);
                session.setAttribute("user",u);
                //跳转到学生登录首页
                return "redirect:stumain";
            }else if("1".equals(optionFlag)){
                attr.addFlashAttribute("error","登录失败，密码错误");
                return "redirect:/login";
            }else{
                attr.addFlashAttribute("error","登录失败，用户不存在");
                return "redirect:/login";
            }
        }else if(userLogin.getType().equals(TYPETEACHER)){
            String optionFlag = teacherService.checkTeacher(userLogin);
            if("0".equals(optionFlag)){
                teacher = teacherService.findTeacherByAccount(userLogin.getAccount());
                session.setAttribute("user",teacher);
                //跳转到教师主页
                return "redirect:techmain";
            }else if("1".equals(optionFlag)){
                attr.addFlashAttribute("error","登录失败，密码错误");
                return "redirect:/login";
            }else{
                attr.addFlashAttribute("error","登录失败，用户不存在");
                return "redirect:/login";
            }
        }else if (userLogin.getType().equals(TYPEADMIN)){
            String optionFlag = intendantService.checkIntendant(userLogin);
            if ("0".equals(optionFlag)){
                intendant = intendantService.findByAccount(userLogin.getAccount());
                session.setAttribute("user",intendant);
                //跳转到管理员主页
                return "redirect:/intendantmain";
            }else if ("1".equals(optionFlag)){
                attr.addFlashAttribute("error","登录失败，密码错误");
                return "redirect:/login";
            }else{
                attr.addFlashAttribute("error","登录失败，用户不存在");
                return "redirect:/login";
            }
        }

        return "redirect:/login";
    }

    @GetMapping("/techmain")
    public String teacherMain(Model model, HttpSession session){
        Teacher teacher = (Teacher) session.getAttribute("user");
        teacher = teacherService.findTeacherByAccount(teacher.getAccount());
        List<Notice> noticeList = noticeService.findByGetUserAccount(teacher.getAccount());
        model.addAttribute("noticeList", noticeList);
        return "techmain";
    }

    @GetMapping("/stumain")
    public String studentMain(Model model, HttpSession session){
        Users student = (Users) session.getAttribute("user");
        student = userService.findUsersByAccount(student.getAccount());
        List<Notice> noticeList = noticeService.findByGetUserAccount(student.getAccount());
        model.addAttribute("noticeList", noticeList);
        return "stumain";
    }

    @GetMapping("/intendantmain")
    public String intendantmain(Model model, HttpSession session){
        Intendant intendant = (Intendant) session.getAttribute("user");
        intendant = intendantService.findByAccount(intendant.getAccount());
        List<Notice> noticeList = noticeService.findByGetUserAccount(intendant.getAccount());
        model.addAttribute("noticeList", noticeList);
        return "intendantmain";
    }

    @GetMapping("/teacherinfo")
    public String getTeacherInfo(Model model, HttpSession session){
        Teacher teacher;
        List<Parameter> list_ParameterTitles;
        List<Parameter> list_ParameterEducation;
        //教师信息获取，显示于个人信息页
        teacher = (Teacher)session.getAttribute("user");
        //如果使用session来显示信息，修改信息后就无法实时显示修改，因此在这里重新查找后再绑定到页面
        teacher = teacherService.findTeacherByAccount(teacher.getAccount());
        list_ParameterTitles = parameterService.getParameterByType("0002");
        list_ParameterEducation = parameterService.getParameterByType("0003");
        if (!"".equals(teacher.getName())){
            model.addAttribute("teacher",teacher);
            model.addAttribute("titleList", list_ParameterTitles);
            model.addAttribute("educationList",list_ParameterEducation);
            return "teacherinfo";
        }
        else {
            return "redirect:/login";
        }
    }

    @GetMapping("/getteacherinfo/{account}")
    public String getTeacherInfoByAccount(@PathVariable("account") String account,Model model, HttpSession session){
        Teacher teacher;
        List<Parameter> list_ParameterTitles;
        List<Parameter> list_ParameterEducation;

        teacher = teacherService.findTeacherByAccount(account);
        list_ParameterTitles = parameterService.getParameterByType("0002");
        list_ParameterEducation = parameterService.getParameterByType("0003");
        if (!"".equals(teacher.getName())){
            model.addAttribute("teacher",teacher);
            model.addAttribute("titleList", list_ParameterTitles);
            model.addAttribute("educationList",list_ParameterEducation);
            model.addAttribute("moveFlag","limit");
            return "teacherinfo";
        }
        return "redirect:/topicselect";
    }

    @PostMapping("/teacherinfo")
    public String alterTeacherInfo(@Valid Teacher teacher, BindingResult result, RedirectAttributes attr){
        if (result.hasErrors()){
            attr.addFlashAttribute("fail","信息有误无法修改");
            return "redirect:/teacherinfo";
        }
        if(teacherService.update(teacher).equals("0")){
            attr.addFlashAttribute("success","修改成功");
            return "redirect:/teacherinfo";
        }else {
            attr.addFlashAttribute("fail","修改失败");
            return "redirect:/teacherinfo";
        }
    }

    @GetMapping("/studentinfo")
    public String getStudentInfo(Model model, HttpSession session){
        Users student;
        List<Parameter> list_ParameterMajor;
        student = (Users)session.getAttribute("user");
        //如果使用session来显示信息，修改信息后就无法实时显示修改，因此在这里重新查找后再绑定到页面
        student = userService.findUsersByAccount(student.getAccount());
        list_ParameterMajor = parameterService.getParameterByType("0004");
        if (!"".equals(student.getName())){
            model.addAttribute("student",student);
            model.addAttribute("major",list_ParameterMajor);
            return "studentinfo";
        }
        else {
            return "redirect:/login";
        }
    }

    @PostMapping("/studentinfo")
    public String alterStudentInfo(@Valid Users student, BindingResult result, RedirectAttributes attr){
        if (result.hasErrors()){
            attr.addFlashAttribute("fail","信息有误无法修改");
            return "redirect:/studentinfo";
        }
        if(userService.update(student).equals("0")){
            attr.addFlashAttribute("success","修改成功");
            return "redirect:/studentinfo";
        }else {
            attr.addFlashAttribute("fail","修改失败");
            return "redirect:/studentinfo";
        }
    }

    @GetMapping("/intendantinfo")
    public String getIntendantInfo(Model model, HttpSession session){
        Intendant intendant;
        intendant = (Intendant)session.getAttribute("user");
        //如果使用session来显示信息，修改信息后就无法实时显示修改，因此在这里重新查找后再绑定到页面
        intendant = intendantService.findByAccount(intendant.getAccount());
        if (!"".equals(intendant.getName())){
            model.addAttribute("intendant",intendant);
            return "intendantinfo";
        }
        else {
            return "redirect:/login";
        }
    }

    @PostMapping("/intendantinfo")
    public String alterIntendantInfo(@Valid Intendant intendant, BindingResult result, RedirectAttributes attr){
        if (result.hasErrors()){
            attr.addFlashAttribute("fail","信息有误无法修改");
            return "redirect:/intendantinfo";
        }
        if(intendantService.update(intendant).equals("0")){
            attr.addFlashAttribute("success","修改成功");
            return "redirect:/intendantinfo";
        }else {
            attr.addFlashAttribute("fail","修改失败");
            return "redirect:/intendantinfo";
        }
    }

    @GetMapping("stumanage")
    public String stuManage(Model model){
        ArrayList<Users> usersArrayList = (ArrayList<Users>) userService.findAll();
        model.addAttribute("usersList",usersArrayList);
        return "stumanage";
    }

    @GetMapping("/stuadd")
    public String stuadd(Model model){
        Users student = new Users();
        ArrayList<Parameter> list_ParameterMajor = (ArrayList) parameterService.getParameterByType("0004");
        model.addAttribute("student",student);
        model.addAttribute("major",list_ParameterMajor);
        return "addstudent";
    }

    @PostMapping("/stuadd")
    public String addStudent(@Valid Users student,BindingResult result, Model model, RedirectAttributes attr){
        if (result.hasErrors()){
            attr.addFlashAttribute("fail","请检查各项是否填写或填写是否有误");
            return "redirect:/stuadd";
        }
        Users tmpUser = userService.findUsersByAccount(student.getAccount());
        if (tmpUser.getAccount()==null){
            //管理员添加的学生默认密码为123
            student.setPassword("123");
            String optionFlag = userService.save(student);
            if("0".equals(optionFlag)){
                attr.addFlashAttribute("success","添加成功");
                return "redirect:/stuadd";
            }else{
                attr.addFlashAttribute("fail","添加失败,错误信息:"+optionFlag);
                return "redirect:/stuadd";
            }
        }else {
            attr.addFlashAttribute("fail","添加失败,学号重复");
            return "redirect:/stuadd";
        }
    }

    @GetMapping("teachermanage")
    public String teacherManage(Model model){
        ArrayList<Teacher> usersArrayList = (ArrayList<Teacher>) teacherService.findAll();
        model.addAttribute("teacherList",usersArrayList);
        return "teachermanage";
    }

    @GetMapping("/teacheradd")
    public String teacherAdd(Model model){
        Teacher teacher = new Teacher();
        List<Parameter> list_ParameterTitles = parameterService.getParameterByType("0002");
        List<Parameter> list_ParameterEducation = parameterService.getParameterByType("0003");
        model.addAttribute("teacher",teacher);
        model.addAttribute("titleList", list_ParameterTitles);
        model.addAttribute("educationList",list_ParameterEducation);
        return "addteacher";
    }

    @PostMapping("/teacheradd")
    public String addTeacher(@Valid Teacher teacher,BindingResult result, Model model, RedirectAttributes attr){
        if (result.hasErrors()){
            attr.addFlashAttribute("fail","请检查各项是否填写或填写是否有误");
            return "redirect:/stuadd";
        }
        Teacher tmpTeacher = teacherService.findTeacherByAccount(teacher.getAccount());
        if (tmpTeacher.getAccount()==null){
            //管理员添加的学生默认密码为123
            teacher.setPassword("123");
            String optionFlag = teacherService.save(teacher);
            if("0".equals(optionFlag)){
                attr.addFlashAttribute("success","添加成功");
                return "redirect:/stuadd";
            }else{
                attr.addFlashAttribute("fail","添加失败,错误信息:"+optionFlag);
                return "redirect:/stuadd";
            }
        }else {
            attr.addFlashAttribute("fail","添加失败,工号重复");
            return "redirect:/stuadd";
        }
    }
}
