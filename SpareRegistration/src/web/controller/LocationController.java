package web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import web.db.dao.Mapper;
import web.db.model.Constants;
import web.db.model.Location;
import web.db.model.User;

import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;


@Controller
public class LocationController {
	
	@Autowired
	Mapper dMapper;
	
	
	@RequestMapping(value = "/addlocation.html", method = RequestMethod.GET)
	public String locAddSetup(ModelMap map, HttpSession session) throws Exception {
		map.addAttribute("page", "loc");
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
			break;
		default:
			return "redirect:/logout.html";
		}
		return "/addlocation";
	}
	
	
	@RequestMapping(value = "/addlocation.html", method = RequestMethod.POST)
	public String locAdd(ModelMap map, HttpSession session, HttpServletRequest request, Location u) throws Exception {
		map.addAttribute("page", "loc");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.addLocation(u)!=0) {
			return "redirect:/location.html";
		} else {
			map.addAttribute("error", "Байршил нэмэхэд алдаа гарлаа.");
		}
		return "/addlocation";
	}
	
	@RequestMapping(value = "/editlocation.html", method = RequestMethod.GET)
	public String locEditSetup(ModelMap map, HttpSession session,
			@RequestParam(value="id", required = true) long id) throws Exception {
		map.addAttribute("page", "loc");
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
			Location location = dMapper.getLocation(id);
			map.addAttribute("location", location);
			break;
		default:
			return "redirect:/logout.html";
		}
		return "/editlocation";
	}
	
	
	@RequestMapping(value = "/editlocation.html", method = RequestMethod.POST)
	public String locEdit(ModelMap map, HttpSession session, HttpServletRequest request, Location l) throws Exception {
		map.addAttribute("page", "loc");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.updateLocation(l)!=0) {
			return "redirect:/location.html";
		} else {
			map.addAttribute("error", "Байршил засахад алдаа гарлаа.");
		}
		return "/editlocation";
	}
	
	@RequestMapping(value = "/location.html", method = RequestMethod.GET)
	public String locSetup(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "p", required = false) Integer start,
			HttpSession session) throws Exception {
		map.addAttribute("page", "loc");
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
			int size = Constants.pageLimit;
			if (start == null) start = 0;
			else start = start*size;
			
			int pageCount = dMapper.getLocationCount();
			map.addAttribute("pageCount", pageCount);
			map.addAttribute("pageStart", start/size);
			map.addAttribute("perPage", size);
			map.addAttribute("pageCountperPage", pageCount/size);
			
			map.addAttribute("locations", dMapper.getLocationsByPage(start, size));
			break;
		default:
			return "redirect:/logout.html";
		}
		
		return "/location";
	}
	
	@RequestMapping(value = "/location.html", method = RequestMethod.POST)
	public String loc(ModelMap map, HttpSession session, HttpServletRequest request,
			@RequestParam(value="id", required=true) Integer id) throws Exception {
		map.addAttribute("page", "loc");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.removeLocation(id)!=0) {
			return "redirect:/location.html";
		} else {
			map.addAttribute("error", "Байршил устгахад алдаа гарлаа.");
			return "/location";
		}
	}
	
}