package cm.commons.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cm.commons.controller.form.UserForm;
import cm.commons.pojos.User;
import cm.commons.sys.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value="add_user")
	public ModelAndView addUser(UserForm user, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String r = checkUser(user, result);
		if(r != null){
			mv.setViewName("error");
			mv.addObject("error", r);
			return mv;
		}
		if(userService.getByName(user.getUsername()) != null){
			mv.setViewName("error");
			mv.addObject("error", "当前用户名:"+user.getUsername()+"已经存在!");
			return mv;
		}
		User u = new User();
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		u.setAuthority(user.getAuthority());
		userService.saveOrUpdate(u);
		request.getSession().setAttribute("user", u);
		mv.setViewName("main");
		return mv;
		
	}
	
	@RequestMapping("admin/delete_user")
	public void deleteUser(@RequestParam int user_id){
		userService.deleteById(user_id);
	}
	
	/**
	 * 修改用户，只有当用户登陆后才能修改，自己能修改自己(只密码)
	 * 管理员能修改普通用户
	 * @param user
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping("admin/modify_user")
	public ModelAndView modifyUser(UserForm user, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String r = checkUser(user, result);
		if(r != null){
			mv.setViewName("error");
			mv.addObject("error", r);
			return mv;
		}
		User currentUser = (User) request.getSession().getAttribute("user");
		if(currentUser == null){
			//如果还没登陆，跳转到登陆界面,这个后面交给拦截器处理
			mv.setViewName("begin");
			return mv;
		}
		User u = (User) userService.get(currentUser.getId());
		u.setPassword(user.getPassword());
//		u.setAuthority(user.getAuthority());权限只有管理员才能修改
		userService.update(u);
		mv.addObject("error", "密码修改成功");
		mv.setViewName("error");
		return mv;
	}
	
	@RequestMapping("all_user")
	public ModelAndView showAllUser(){
		ModelAndView mv = new ModelAndView();
		List<UserForm> userForms = new ArrayList<UserForm>();
		for(User u: (List<User>)userService.getAll()){
			UserForm uf = new UserForm();
			uf.setId(u.getId());
			uf.setAuthority(u.getAuthority());
			uf.setPassword(u.getPassword());
			uf.setUsername(u.getUsername());
			userForms.add(uf);
		}
		mv.addObject("user_list", userForms);
		mv.setViewName("show_user");
		return mv;
	}
	
	@RequestMapping("login")
	public ModelAndView login(UserForm user, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String r = checkUser(user, result);
		if(r != null){
			mv.setViewName("error");
			mv.addObject("error", "表单提交错误！");
			return mv;
		}
		User u = (User) userService.getByName(user.getUsername());
		if(u == null){
			mv.setViewName("error");
			mv.addObject("error", "当前用户名:"+user.getUsername()+"不存在!");
			return mv;
		}else if(!u.getPassword().equals(user.getPassword())){
			mv.setViewName("error");
			mv.addObject("error", "当前用户:"+user.getUsername()+"密码错误!");
			return mv;
		}else{
			request.getSession().setAttribute("user", u);
		}
		return new ModelAndView(new RedirectView("../main.do?route_id=1"));
	}
	
	@RequestMapping("begin")
	public String showLogin(){
		return "login";
	}
	
	private String checkUser(UserForm user, BindingResult result){
		String error = null;
		if(result.hasErrors()){
			error = "表单提交错误！";
		}
		String username = user.getUsername();
		String pwd = user.getPassword();
		if("".equals(username) || "".equals(pwd)){
			error = "当前用户名或密码不能为空！";
		}
		return error;
	}
}
