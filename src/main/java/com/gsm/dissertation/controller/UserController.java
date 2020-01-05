package com.gsm.dissertation.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gsm.dissertation.model.*;
import com.gsm.dissertation.service.GuideTeacherService;
import com.gsm.dissertation.service.ParameterService;
import com.gsm.dissertation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

    @RequestMapping("/stulist")
    public String stuList(Model model,HttpSession session){
        //获取教师管理的学生的学号姓名以及选题状态
        Teacher teacher = (Teacher)session.getAttribute("user");
        List<GuideTeacher> guideTeacherList = guideTeacherService.getGuideTeacherByAccount(teacher.getAccount());
        model.addAttribute("guideTeacherList",guideTeacherList);
        return "stulist";
    }

    @GetMapping({"/edituser", "/edituser/{uid}"})
    public String edit(@PathVariable(name = "uid", required = false) Integer uid,Model model){
        Users user = new Users();
        if(uid != null && uid > 0){
            user = userService.findById(uid);
        }
        model.addAttribute("user", user);
        return "edituser";
    }

    @PostMapping("/saveuser")
    public String save(@Valid Users user, BindingResult result, RedirectAttributes attr){
        try {
            if (result.hasErrors()){
                return "redirect:/edituser";
            }
            userService.save(user);
            attr.addFlashAttribute("ok","保存成功");
            return "redirect:/listusers";
        }catch (Exception ex){
            attr.addFlashAttribute("err","保存失败" + ex.toString());
            return "redirect:/edituser";
        }
    }

    @GetMapping("/deleteuser/{uid}")
    public String delete(@PathVariable("uid") Integer uid){
        userService.deleteById(uid);
        return "redirect:/listusers";
    }


    /**
     * 批量删除用户
     * 目前存在问题删除后不能刷新页面
     * 等待以后解决问题
     * @param uids
     * @return
     */
    @PostMapping("/deleteusers")
    public String deletes(@RequestBody String uids){
        List<Users> usersList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(uids);
        JSONArray jsonArray = jsonObject.getJSONArray("uids");//前端传递时使用uids作为json的键
        int ilen = jsonArray.size();
        for (int i = 0; i < ilen; i++){
            usersList.add(userService.findById(jsonArray.getInteger(i)));
        }
        userService.deletes(usersList);
        return "redirect:/listusers";
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
        Users u = null;
        Teacher teacher = null;


        if (userLogin.getType().equals(TYPESTUDENT)){
            if(userService.checkUser(userLogin).equals("0")) {
                session.setAttribute("user",u);
                //跳转到学生登录首页
                return "stumain";
            }
        }else if(userLogin.getType().equals(TYPETEACHER)){
            if(userService.checkTeacher(userLogin).equals("0")){
                teacher = userService.findTeacherByAccount(userLogin.getAccount());
                session.setAttribute("user",teacher);
                //跳转到教师主页
                return "techmain";
            }
        }else{
            attr.addFlashAttribute("fail","登录失败");
        }
        //TODO 管理员

        return "redirect:/login";
    }
}
