package com.gsm.dissertation.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gsm.dissertation.model.UserLogin;
import com.gsm.dissertation.model.Users;
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

    /**
     * 根据条件查询用户信息
     * @param kw 关键字
     * @param model 模型对象，视图界面的环境对象
     * @param pageable 分页信息对象，包含分页基本信息，如当前页码，每页条数
     * @return
     */
    @RequestMapping("/listusers")
    public String list(String kw, Model model, Pageable pageable){
        if (kw != null){
            kw="%" + kw + "%";
        }else{
            kw="%%";
        }
        Page<Users> pageUser = userService.findAll(kw,pageable);

        model.addAttribute("pages",pageUser);
        return "listusers";
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
        return "login";
    }

    /**
     * 登录
     * @param user 用户登录实体
     * @param result 检查登录实体是否合法
     * @param session session保存登录信息
     * @param model 传递登录成功与否
     * @return
     */
    @PostMapping("/login")
    public String login(@Valid UserLogin user,
                        BindingResult result, HttpSession session, Model model){
        if (result.hasErrors()){
            return "redirect:/login";
        }
        Users u = userService.checkUser(user);
        if (u != null){
            session.setAttribute("user",u);
            return "index";
        }
        model.addAttribute("fail","登录失败");
        return "redirect:/login";
    }
}
