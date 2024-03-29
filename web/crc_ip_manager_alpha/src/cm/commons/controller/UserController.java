package cm.commons.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cm.commons.controller.form.PageModelForm;
import cm.commons.controller.form.UserForm;
import cm.commons.dao.hiber.util.Element;
import cm.commons.dao.hiber.util.Link;
import cm.commons.dao.hiber.util.OP;
import cm.commons.pojos.User;
import cm.commons.sys.service.SystemService;
import cm.commons.sys.service.UserService;
import cm.commons.util.AlarmUtil;
import cm.commons.util.NullUtil;
import cm.commons.util.PageModel;

/**
 * 用户模块很多是需要管理员才能看
 * @author Administrator
 *
 */
@Controller
public class UserController {

	@Autowired
	private UserService userService;

	/**
	 * 删除多个用户
	 * @param ids
	 * @return
	 */
	@RequestMapping("admin/delete_user_by_ids")
	public ModelAndView deleteItems(@RequestParam String userIds){
		ModelAndView mv = new ModelAndView();
		if(NullUtil.notNull(userIds)){
			for(String id:userIds.split(",")){
				if(NullUtil.notNull(id)){
					userService.deleteById(Integer.valueOf(id));
				}
			}
		}
		mv.setView(new RedirectView("all_user_by_page.do?pageNo=1&queryString="));
		return mv;
	}

	
	/**
	 * 添加用户
	 * @param user
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping(value="admin/add_user")
	public ModelAndView addUser(@RequestParam String flags, UserForm user, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String r = checkUser(user, result);
		if(r != null){
			mv.setViewName("../public/error");
			mv.addObject("error", r);
			return mv;
		}
		if(userService.getByName(user.getUsername()) != null){
			mv.setViewName("../public/error");
			mv.addObject("error", "当前用户名:"+user.getUsername()+"已经存在!");
			return mv;
		}
		User u = new User();
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		u.setAuthority(user.getAuthority());
		userService.saveOrUpdate(u);
		if(flags.equals("admin")){
			return new ModelAndView(new RedirectView("admin/all_user.do"));
		}else{
			mv.setViewName("../public/success");
		}
		return mv;
	}
	
	/**
	 * 删除用户
	 * @param user_id
	 */
	@RequestMapping("admin/delete_user")
	public ModelAndView deleteUser(@RequestParam int user_id, HttpServletRequest request){
		userService.deleteById(user_id);
		return new ModelAndView(new RedirectView("all_user_by_page.do?pageNo=1&queryString="));
	}
	
	
	
	/**
	 * 显示修改用户界面
	 * @return
	 */
	@RequestMapping("show_modify_user")
	public ModelAndView showModifyUser(@RequestParam int id){
		ModelAndView mv = new ModelAndView();
		User u = (User) userService.get(id);
		UserForm uf = new UserForm();
		uf.setId(id);
		uf.setUsername(u.getUsername());
		uf.setPassword(u.getPassword());
		mv.addObject("user", uf);
		mv.setViewName("modify_user");
		return mv;
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
		//判断原来密码是否正确，如果普通用户不能修改权限
		
		if(r != null){
			mv.setViewName("../page/public/error.jsp");
			mv.addObject("error", r);
			return mv;
		}
		
		User u = new User();
		u.setId(user.getId());
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		u.setAuthority(user.getAuthority());
		userService.update(u);
		return new ModelAndView(new RedirectView("../page/public/success.jsp"));
	}
	

	@RequestMapping("admin/query_user_by_page")
	public ModelAndView showQueryUserByPage(User cond,@RequestParam int pageNo,HttpServletRequest request){
		List<Element> conditions=new ArrayList<Element>();
		String username=cond.getUsername();
		boolean isNotNullUserName=NullUtil.notNull(username);
		if(isNotNullUserName){
			conditions.add(new Element(Link.WHERE,OP.LIKE,"username",username));
		}
		String authority=cond.getAuthority();
		boolean isNotNullAuthority=NullUtil.notNull(authority);
		
		if(isNotNullUserName&&isNotNullAuthority){
			conditions.add(new Element(Link.AND,OP.EQ,"authority",authority));
		}
		if(!isNotNullUserName&&isNotNullAuthority){
			conditions.add(new Element(Link.WHERE,OP.EQ,"authority",authority));
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("condition", cond);
		//从web.xml的配置中配置页面大小
		int pageSize = Integer.parseInt(request.getSession().getServletContext().getInitParameter("page-size"));
		PageModel<User> pageModel=userService.getPagedUserByCondition(conditions, pageNo, pageSize);
		mv.addObject("pageModel", pageModel);
		mv.addObject("user_list", pageModel.getList());
		mv.setViewName("UserManage/UserList");
		return mv;
	}
	/**
	 * 分页显示用户
	 * @param searchStr
	 * @param pageNo
	 * @param queryString 查询的关键词，用户名或者权限
	 * @param request
	 * @return
	 */
	@RequestMapping("admin/all_user_by_page")
	public ModelAndView showAllUserByPage(String searchStr, @RequestParam int pageNo, @RequestParam String queryString, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String str = "";
		if(searchStr == null || searchStr.equals("")){
			str = (queryString == null || queryString.equals(""))?"":queryString;
		}else{
			str = searchStr;
		}
		//从web.xml的配置中配置页面大小
		int pageSize = Integer.parseInt(request.getSession().getServletContext().getInitParameter("page-size"));
		PageModelForm<UserForm> pmf = this.getAllUserByPage(queryString, pageNo, pageSize);
		mv.addObject("pageModel", pmf);
		mv.addObject("user_list", pmf.getData());
		mv.addObject("queryStr", str);
		mv.setViewName("UserManage/UserList");
		return mv;
	}
	
	
	/**
	 * 退出系统
	 * @param request
	 * @return
	 */
	@RequestMapping("login_out")
	public String loginOut(HttpServletRequest request){
		request.getSession().removeAttribute("user");
		return "../../index";
	}
	
	/**
	 * 登录
	 * @param user
	 * @param result
	 * @param request
	 * @return
	 */
	@RequestMapping("login")
	public ModelAndView login(UserForm user, BindingResult result, HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		String r = checkUser(user, result);
		if(r != null){
			mv.setViewName("../public/error");
			mv.addObject("error", r);
			return mv;
		}
		User u = (User) userService.getByName(user.getUsername());
		if(u == null){
			mv.setViewName("../../index");
			request.setAttribute("info", "当前用户名:"+user.getUsername()+"不存在!");
			return mv;
		}else if(!u.getPassword().equals(user.getPassword())){
			mv.setViewName("../../index");
			UserForm uf = new UserForm();
			uf.setUsername(u.getUsername());
			mv.addObject("user", uf);
			request.setAttribute("info", "当前用户:"+user.getUsername()+"密码错误!");
			return mv;
		}
		
		request.getSession().setAttribute("user", u);
		return new ModelAndView(new RedirectView("crc.jsp?route_id=1"));
	}

	
	private String checkUser(UserForm user, BindingResult result){
		String error = null;
		if(result.hasErrors()){
			error = "表单提交错误！"+result.getAllErrors();
		}
		String username = user.getUsername();
		String pwd = user.getPassword();
		if("".equals(username) || "".equals(pwd)){
			error = "当前用户名或密码不能为空！";
		}
		return error;
	}
	
	/**
	 * 通过分页获取用户
	 * @return
	 */
	private PageModelForm<UserForm> getAllUserByPage(String queryString,int pageNo,int pageSize){
		PageModel<User> pm = userService.getAll(queryString, pageNo, pageSize);
		PageModelForm<UserForm> pmf = new PageModelForm<UserForm>();
		
		List<User> list = pm.getList();
		List<UserForm> userForms = new ArrayList<UserForm>();
		if(list != null){
			for(User u:list){
				UserForm uf = new UserForm();
				uf.setAuthority(u.getAuthority());
				uf.setPassword(uf.getPassword());
				uf.setUsername(u.getUsername());
				uf.setId(u.getId());
				userForms.add(uf);
			}
		}
		pmf.setButtomPageNo(pm.getButtomPageNo());
		pmf.setData(userForms);
		pmf.setPageNo(pageNo);
		pmf.setPageSize(pageSize);
		pmf.setTotalPages(pm.getTotalPages());
		return pmf;
	}
}
