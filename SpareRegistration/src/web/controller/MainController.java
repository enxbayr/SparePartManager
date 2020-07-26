package web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import web.db.dao.Mapper;
import web.db.model.User;

@Controller
public class MainController {
	
	@Autowired
	Mapper dMapper;
	
	@RequestMapping(value = "/logout.html", method = RequestMethod.GET)
	public String logoutGet(HttpSession session) {
		session.setAttribute("cmsadmin", null);
		return "redirect:/index.html";	
	}

	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public String indexSetup(ModelMap map, HttpSession session) throws Exception {
		map.addAttribute("page", "index");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (logged) {
			User o = (User) session.getAttribute("cmsadmin");
			map.addAttribute("op", o);
			int role = Integer.parseInt(o.getC_role());
			switch (role) {
				case 0:
				case 4:
					map.addAttribute("depName", "Бүх");
					map.addAttribute("sparestatus", dMapper.getSpareByStatus());
					break;
				default:
					map.addAttribute("depName", dMapper.getDepById(o.getDep_id()).getC_name());
					map.addAttribute("sparestatus", dMapper.getSpareByStatusD(o.getDep_id()));
					break;
			}
			return "/index";
		} else {
			return "/admin/login";
		}
	}
	
	@RequestMapping(value = "/index.html", method = RequestMethod.POST)
	public String cmsLoginPost(ModelMap map, 
			@RequestParam(value = "loginName", required = true) String loginName,
			@RequestParam(value = "loginPass", required = true) String loginPass,
			HttpSession session, HttpServletRequest request) {
		map.addAttribute("page", "index");
		User adm = new User();
		adm.setC_name(loginName);
		adm.setC_pass(loginPass);
		User a = dMapper.isOperator(adm);
		if (a!=null) {
			session.setAttribute("cmsadmin", a);
			map.addAttribute("op", a);
			int role = Integer.parseInt(a.getC_role());
			switch (role) {
				case 0:
				case 4:
					map.addAttribute("depName", "Бүх");
					map.addAttribute("sparestatus", dMapper.getSpareByStatus());
					break;
				default:
					map.addAttribute("depName", dMapper.getDepById(a.getDep_id()).getC_name());
					map.addAttribute("sparestatus", dMapper.getSpareByStatusD(a.getDep_id()));
					break;
			}
			insertLog();
			return "/index";
		} else {
			map.addAttribute("error", "Хэрэглэгчийн нэр эсвэл нууц үг буруу байна");
			return "/admin/login";
		}
	}
	
	@RequestMapping(value = "/users.html", method = RequestMethod.GET)
	public String userSetup(ModelMap map, HttpSession session,
			@RequestParam(value="id", required=false) Integer id) throws Exception {
		map.addAttribute("page", "users");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		// 0-superadmin; 1-admin; 2-shift; 4-superviewer; 3-viewer;
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
			case 0:
				map.addAttribute("adminlist", dMapper.getAllUsers());
				if (id!=null) {
					map.addAttribute("adm", dMapper.getUser(id));
				}
				map.addAttribute("departments", dMapper.getDepartments());
				break;
			case 1:
				map.addAttribute("adminlist", dMapper.getUsers(o.getDep_id()));
				if (id!=null) {
					map.addAttribute("adm", dMapper.getUser(id));
				}
				map.addAttribute("departments", dMapper.getDepartments());
				break;
			default:
				return "redirect:/logout.html";
		}
		map.addAttribute("op", o);
		return "/users";
	}
	
	@RequestMapping(value = "/users.html", method = RequestMethod.POST)
	public String user(ModelMap map, User newop, HttpSession session, HttpServletRequest request,
			@RequestParam(value = "submit", required = true) String submit) {
		map.addAttribute("page", "users");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		map.addAttribute("departments", dMapper.getDepartments());
		if ("Нэмэх".equals(submit)) {
			try {
				if (dMapper.addUser(newop)!=0) {
					return "redirect:/users.html";
				} else {
					map.addAttribute("error", "Хандах эрх нэмэхэд алдаа гарлаа");
				}
			} catch (Exception se) {
				map.addAttribute("error", "Хандах нэр давхцаж байна");
			}
		} 
		if ("Засах".equals(submit)) {
			if (dMapper.updateUserPermission(newop)!=0) {
				return "redirect:/users.html";
			} else {
				map.addAttribute("error", "Хандах эрх засахад алдаа гарлаа");
			}
		}
		return "/users";
	}
	
	@RequestMapping(value = "/remuser.html", method = RequestMethod.POST)
	public String userRemove(ModelMap map, User newop, HttpSession session,
			@RequestParam(value="id", required=true) Integer id) throws Exception {
		map.addAttribute("page", "users");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.removeUser(id)!=0) {
			return "redirect:/users.html";
		} else {
			map.addAttribute("error", "Хандах эрх устгахад алдаа гарлаа");
			return "/users";
		}
	}
	
	
	@RequestMapping(value = "/resetpass.html", method = RequestMethod.GET)
	public String resetPassSetup(ModelMap map, HttpSession session,
			@RequestParam(value = "id", required = true) int id) throws Exception {
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		map.addAttribute("user", dMapper.getUser(id));
		return "/admin/resetpass";
	}
	
	@RequestMapping(value = "/resetpass.html", method = RequestMethod.POST)
	public String resetPass(ModelMap map, HttpSession session,
			@RequestParam(value = "newpass", required = true) String newpass,
			@RequestParam(value = "userid", required = true) int userid) throws Exception {
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		User u = new User();
		u.setId(userid);
		u.setC_pass(newpass);
		if (dMapper.updateUserPass(u)!=0) {
			map.addAttribute("success", "Success");
		} else {
			map.addAttribute("error", "Error");
		}
		return "/admin/resetpass";
	}
	
	@RequestMapping(value = "/changepass.html", method = RequestMethod.GET)
	public String changePassSetup(ModelMap map, HttpSession session) throws Exception {
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		return "/admin/changepass";
	}
	
	@RequestMapping(value = "/changepass.html", method = RequestMethod.POST)
	public String changePass(ModelMap map, HttpSession session,
			@RequestParam(value = "newpass", required = true) String newpass,
			@RequestParam(value = "oldpass", required = true) String oldpass,
			@RequestParam(value = "confirmpass", required = true) String confirmpass) throws Exception {
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (newpass.equals(confirmpass)) {
			User u = new User();
			u.setId(o.getId());
			u.setC_name(o.getC_name());
			u.setC_pass(newpass);
			if (dMapper.isOperator(u)!=null) {
				if (dMapper.updateUserPass(u)!=0) {
					map.addAttribute("success", "Success");
				} else {
					map.addAttribute("error", "Error");
				}
			} else {
				map.addAttribute("error", "Хуучин нууц үг буруу байна.");
			}
		} else {
			map.addAttribute("error", "Шинэ нууц үгийн давталт буруу байна.");
		}
		return "/admin/changepass";
	}
	
	public static void insertLog() {
	}

}