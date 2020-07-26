package web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import web.db.dao.Mapper;
import web.db.model.Constants;
import web.db.model.Ttype;
import web.db.model.User;

import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

@Controller
public class TypeController {

	@Autowired
	Mapper dMapper;

	@RequestMapping(value = "/addtype.html", method = RequestMethod.GET)
	public String typeAddSetup(ModelMap map, HttpSession session)
			throws Exception {
		map.addAttribute("page", "type");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
		case 0:
			break;
		default:
			return "redirect:/logout.html";
		}
		return "/addtype";
	}

	@RequestMapping(value = "/addtype.html", method = RequestMethod.POST)
	public String typeAdd(ModelMap map, HttpSession session,
			HttpServletRequest request, Ttype u) throws Exception {
		map.addAttribute("page", "type");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.addType(u) != 0) {
			return "redirect:/type.html";
		} else {
			map.addAttribute("error", "Төрөл нэмэхэд алдаа гарлаа.");
		}
		return "/addtype";
	}

	@RequestMapping(value = "/edittype.html", method = RequestMethod.GET)
	public String typeEditSetup(ModelMap map, HttpSession session,
			@RequestParam(value = "id", required = true) int tid)
			throws Exception {
		map.addAttribute("page", "type");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
		case 0:
			map.addAttribute("types", dMapper.getTypeById(tid));
			break;
		default:
			return "redirect:/logout.html";
		}
		return "/edittype";
	}

	@RequestMapping(value = "/edittype.html", method = RequestMethod.POST)
	public String typeEdit(ModelMap map, HttpSession session,
			HttpServletRequest request, Ttype l) throws Exception {
		map.addAttribute("page", "type");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.updateType(l) != 0) {
			return "redirect:/type.html";
		} else {
			map.addAttribute("error", "Төрөл засахад алдаа гарлаа.");
		}
		return "/edittype";
	}

	@RequestMapping(value = "/type.html", method = RequestMethod.GET)
	public String typeSetup(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "p", required = false) Integer start,
			HttpSession session) throws Exception {
		map.addAttribute("page", "type");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
		case 0:
			int size = Constants.pageLimit;
			if (start == null)
				start = 0;
			else
				start = start * size;

			int pageCount = dMapper.getTypeCount();
			map.addAttribute("pageCount", pageCount);
			map.addAttribute("pageStart", start / size);
			map.addAttribute("perPage", size);
			map.addAttribute("pageCountperPage", pageCount / size);

			map.addAttribute("type", dMapper.getTypesByPage(start, size));
			break;
		default:
			return "redirect:/logout.html";
		}
		return "/type";
	}

	@RequestMapping(value = "/type.html", method = RequestMethod.POST)
	public String type(ModelMap map, HttpSession session,
			@RequestParam(value = "id", required = true) Integer id)
			throws Exception {
		map.addAttribute("page", "type");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.removeType(id) != 0) {
			return "redirect:/type.html";
		} else {
			map.addAttribute("error", "Төрөл устгахад алдаа гарлаа.");
			return "/type";
		}
	}

}