//package cn.edu.bupt.springmvc.web.controller;
//
//import cn.edu.bupt.springmvc.core.generic.GenericController;
//import cn.edu.bupt.springmvc.web.model.User;
//import cn.edu.bupt.springmvc.web.security.PermissionSign;
//import cn.edu.bupt.springmvc.web.security.RoleSign;
//import cn.edu.bupt.springmvc.web.service.UserService;
//import org.apache.shiro.SecurityUtils;
//import org.apache.shiro.authc.AuthenticationException;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.apache.shiro.authz.annotation.RequiresRoles;
//import org.apache.shiro.subject.Subject;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//import java.util.List;
//
///**
// * 用户控制器
// * <p>
// * Created by FirenzesEagle on 2016/4/18 0018.
// */
//@Controller
//@RequestMapping(value = "/user")
//public class UserController extends GenericController {
//
//    @Resource
//    private UserService userService;
//
//    /**
//     * 用户登录
//     *
//     * @param user
//     * @param result
//     * @return
//     */
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String login(@Valid User user, BindingResult result, Model model, HttpServletRequest request) {
//        try {
//            Subject subject = SecurityUtils.getSubject();
//            // 已登陆则 跳到首页
//            if (subject.isAuthenticated()) {
//                return "redirect:/";
//            }
//            if (result.hasErrors()) {
//                model.addAttribute("error", "参数错误！");
//                return "login";
//            }
//            // 身份验证
//            subject.login(new UsernamePasswordToken(user.getUsername(), user.getPassword()));
//            // 验证成功在Session中保存用户信息
//            final User authUserInfo = userService.selectByUsername(user.getUsername());
//            request.getSession().setAttribute("userInfo", authUserInfo);
//        } catch (AuthenticationException e) {
//            // 身份验证失败
//            model.addAttribute("error", "用户名或密码错误 ！");
//            e.printStackTrace();
//            return "login";
//        }
//        return "redirect:/";
//    }
//
//    @RequestMapping(value = "/alluser", method = RequestMethod.GET)
//    public void allUser(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize, HttpServletResponse response) {
//        List<User> userList = userService.selectByPage(pageNo, pageSize);
//        renderString(response,userList);
//    }
//
//    /**
//     * 用户登出
//     *
//     * @param session
//     * @return
//     */
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logout(HttpSession session) {
//        session.removeAttribute("userInfo");
//        // 登出操作
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();
//        return "login";
//    }
//
//    /**
//     * 基于角色 标识的权限控制案例
//     */
//    @RequestMapping(value = "/admin")
//    @ResponseBody
//    @RequiresRoles(value = RoleSign.ADMIN)
//    public String admin() {
//        return "拥有admin角色,能访问";
//    }
//
//    /**
//     * 基于权限标识的权限控制案例
//     */
//    @RequestMapping(value = "/create")
//    @ResponseBody
//    @RequiresPermissions(value = PermissionSign.USER_CREATE)
//    public String create() {
//        return "拥有user:create权限,能访问";
//    }
//
//}
