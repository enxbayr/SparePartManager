package web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import web.db.dao.Mapper;
import web.db.model.Constants;
import web.db.model.Unit;
import web.db.model.User;

import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;


@Controller
public class UnitController {
	
	@Autowired
	Mapper dMapper;
	
	
	@RequestMapping(value = "/addunit.html", method = RequestMethod.GET)
	public String unitAddSetup(ModelMap map, HttpSession session) throws Exception {
		map.addAttribute("page", "unit");
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
			break;
		default:
			return "redirect:/logout.html";
		}
		return "/addunit";
	}
	
	
	@RequestMapping(value = "/addunit.html", method = RequestMethod.POST)
	public String unitAdd(ModelMap map, HttpSession session, HttpServletRequest request, Unit u) throws Exception {
		map.addAttribute("page", "unit");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.addUnit(u)!=0) {
			return "redirect:/unit.html";
		} else {
			map.addAttribute("error", "Төхөөрөмж нэмэхэд алдаа гарлаа.");
		}
		return "/addunit";
	}
	
	@RequestMapping(value = "/editunit.html", method = RequestMethod.GET)
	public String unitEditSetup(ModelMap map, HttpSession session,
			@RequestParam(value="id", required = true) long id) throws Exception {
		map.addAttribute("page", "unit");
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
			Unit unit = dMapper.getUnitById(id);
			map.addAttribute("unit", unit);
			break;
		default:
			return "redirect:/logout.html";
		}
		return "/editunit";
	}
	
	
	@RequestMapping(value = "/editunit.html", method = RequestMethod.POST)
	public String unitEdit(ModelMap map, HttpSession session, HttpServletRequest request, Unit u) throws Exception {
		map.addAttribute("page", "unit");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.updateUnit(u)!=0) {
			return "redirect:/unit.html";
		} else {
			map.addAttribute("error", "Төхөөрөмж засахад алдаа гарлаа.");
		}
		return "/editunit";
	}
	
	@RequestMapping(value = "/unit.html", method = RequestMethod.GET)
	public String unitSetup(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "p", required = false) Integer start,
			@RequestParam(value="tid", required = false) Integer typeid,
			HttpSession session) throws Exception {
		map.addAttribute("page", "unit");
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
			
			int size = Constants.pageLimit;
			if (start == null) start = 0;
			else start = start*size;
			
			if (typeid == null) typeid = 0;
			map.addAttribute("tid", typeid);
			int pageCount = 0;
			if (typeid==0)
				pageCount = dMapper.getUnitCount();
			else
				pageCount = dMapper.getUnitCountById(typeid);
			map.addAttribute("pageCount", pageCount);
			map.addAttribute("pageStart", start/size);
			map.addAttribute("perPage", size);
			map.addAttribute("pageCountperPage", pageCount/size);
			
			if (typeid==0)
				map.addAttribute("unitlist", dMapper.getUnit(start, size));
			else
				map.addAttribute("unitlist", dMapper.getUnitsByType(typeid, start, size));
			break;
		default:
			return "redirect:/logout.html";
		}
		return "/unitlist";
	}
	
	@RequestMapping(value = "/unit.html", method = RequestMethod.POST)
	public String unit(ModelMap map, HttpSession session,
			@RequestParam(value="id", required=true) Integer id) throws Exception {
		map.addAttribute("page", "unit");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.removeUnit(id)!=0) {
			return "redirect:/unit.html";
		} else {
			map.addAttribute("error", "Төхөөрөмж устгахад алдаа гарлаа.");
			return "/unitlist";
		}
	}
	
	@RequestMapping(value = "/units.html", method = RequestMethod.POST)
	public String unitlist(ModelMap map, HttpSession session,
			@RequestParam(value="tid", required=true) long typeid) throws Exception {
		map.addAttribute("page", "unit");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		int size = Constants.pageLimit;
		int start = 0;
		if (typeid==0)
			map.addAttribute("unitlist", dMapper.getUnit(start, size));
		else
			map.addAttribute("unitlist", dMapper.getUnitsByType(typeid, start, size));
		return "/admin/unitlist";
	}
	
	@RequestMapping(value = "/unitp.html", method = RequestMethod.POST)
	public String unitpage(ModelMap map, HttpSession session,
			@RequestParam(value="tid", required=true) long typeid) throws Exception {
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		int pageCount = 0;
		if (typeid==0)
			pageCount = dMapper.getUnitCount();
		else
			pageCount = dMapper.getUnitCountById(typeid);
		int size = Constants.pageLimit;
		int start = 0;
		map.addAttribute("pageCount", pageCount);
		map.addAttribute("pageStart", start/size);
		map.addAttribute("perPage", size);
		map.addAttribute("pageCountperPage", pageCount/size);
		map.addAttribute("ttid", typeid);
		return "/admin/unitpage";
	}
}