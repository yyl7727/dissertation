package com.gsm.dissertation.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gsm.dissertation.model.Users;
import com.gsm.dissertation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String save(@Valid Users user, BindingResult result){
        try {
            if (result.hasErrors()){
                return "redirect:/edituser";
            }
            userService.save(user);
            return "redirect:/listusers";
        }catch (Exception ex){
            return "redirect:/edituser";
        }
    }

    @GetMapping("/delete/{uid}")
    public String delete(@PathVariable("uid") Integer uid){
        userService.deleteById(uid);
        return "redirect:/listusers";
    }

    @GetMapping("/deletes{uids}")
    public String deletes(@PathVariable("uids") String uids){
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
}
