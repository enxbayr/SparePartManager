package web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import web.db.dao.Mapper;
import web.db.model.Constants;
import web.db.model.Systems;
import web.db.model.User;

import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;


@Controller
public class SystemController {
	
	@Autowired
	Mapper dMapper;
	
	
	@RequestMapping(value = "/addsystem.html", method = RequestMethod.GET)
	public String sysAddSetup(ModelMap map, HttpSession session) throws Exception {
		map.addAttribute("page", "system");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
		case 0:
		case 1:
			map.addAttribute("deps", dMapper.getDepartments());
			break;
		default:
			return "redirect:/logout.html";
		}
		return "/addsystem";
	}
	
	
	@RequestMapping(value = "/addsystem.html", method = RequestMethod.POST)
	public String sysAdd(ModelMap map, HttpSession session, HttpServletRequest request, Systems u) throws Exception {
		map.addAttribute("page", "system");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.addSystem(u)!=0) {
			return "redirect:/system.html";
		} else {
			map.addAttribute("error", "Систем нэмэхэд алдаа гарлаа.");
		}
		return "/addsystem";
	}
	
	@RequestMapping(value = "/editsystem.html", method = RequestMethod.GET)
	public String sysEditSetup(ModelMap map, HttpSession session,
			@RequestParam(value="id", required = true) long id) throws Exception {
		map.addAttribute("page", "system");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
		case 0:
		case 1:
			map.addAttribute("types", dMapper.getTypes());
			Systems systems = dMapper.getSystemById(id);
			map.addAttribute("systems", systems);
			map.addAttribute("deps", dMapper.getDepartments());
			break;
		default:
			return "redirect:/logout.html";
		}
		return "/editsystem";
	}
	
	@RequestMapping(value = "/editsystem.html", method = RequestMethod.POST)
	public String sysEdit(ModelMap map, HttpSession session, HttpServletRequest request, Systems s) throws Exception {
		map.addAttribute("page", "system");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.updateSystem(s)!=0) {
			return "redirect:/system.html";
		} else {
			map.addAttribute("error", "Систем засахад алдаа гарлаа.");
		}
		return "/editsystem";
	}
	
	@RequestMapping(value = "/system.html", method = RequestMethod.GET)
	public String sysSetup(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "p", required = false) Integer start,
			HttpSession session) throws Exception {
		map.addAttribute("page", "system");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		
		int size = Constants.pageLimit;
		if (start == null) start = 0;
		else start = start*size;
		
		int pageCount = 0;
		
		map.addAttribute("op", o);
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
			case 0:
				pageCount = dMapper.getSystemCount();
				map.addAttribute("systems", dMapper.getSystemsByPage(start, size));
				break;
			case 1:
				pageCount = dMapper.getSystemsByPageCountD(o.getDep_id());
				map.addAttribute("systems", dMapper.getSystemsByPageD(o.getDep_id(), start, size));
				break;
			default:
				return "redirect:/logout.html";
		}
		
		map.addAttribute("pageCount", pageCount);
		map.addAttribute("pageStart", start/size);
		map.addAttribute("perPage", size);
		map.addAttribute("pageCountperPage", pageCount/size);
		
		return "/systems";
	}
	
	@RequestMapping(value = "/system.html", method = RequestMethod.POST)
	public String sys(ModelMap map, HttpSession session, HttpServletRequest request,
			@RequestParam(value="id", required=true) Integer id) throws Exception {
		map.addAttribute("page", "system");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.removeSystem(id)!=0) {
			return "redirect:/system.html";
		} else {
			map.addAttribute("error", "Систем устгахад алдаа гарлаа.");
			return "/systems";
		}
	}
	
}