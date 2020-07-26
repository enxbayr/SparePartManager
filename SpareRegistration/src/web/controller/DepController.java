package web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import web.db.dao.Mapper;
import web.db.model.Constants;
import web.db.model.Department;
import web.db.model.User;

import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;


@Controller
public class DepController {
	
	@Autowired
	Mapper dMapper;
	
	
	@RequestMapping(value = "/adddep.html", method = RequestMethod.GET)
	public String typeAddSetup(ModelMap map, HttpSession session) throws Exception {
		map.addAttribute("page", "dep");
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
		return "/adddep";
	}
	
	
	@RequestMapping(value = "/adddep.html", method = RequestMethod.POST)
	public String typeAdd(ModelMap map, HttpSession session, HttpServletRequest request, Department u) throws Exception {
		map.addAttribute("page", "dep");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.addDep(u)!=0) {
			return "redirect:/department.html";
		} else {
			map.addAttribute("error", "Хэсэг нэмэхэд алдаа гарлаа.");
		}
		return "/adddep";
	}
	
	@RequestMapping(value = "/editdep.html", method = RequestMethod.GET)
	public String depEditSetup(ModelMap map, HttpSession session,
			@RequestParam(value="id", required = true) int tid) throws Exception {
		map.addAttribute("page", "dep");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
			case 0:
				map.addAttribute("department", dMapper.getDepById(tid));
				break;
			default:
				return "redirect:/logout.html";
		}
		return "/editdep";
	}
	
	
	@RequestMapping(value = "/editdep.html", method = RequestMethod.POST)
	public String depEdit(ModelMap map, HttpSession session, HttpServletRequest request, Department l) throws Exception {
		map.addAttribute("page", "dep");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.updateDep(l)!=0) {
			return "redirect:/department.html";
		} else {
			map.addAttribute("error", "Хэсэг засахад алдаа гарлаа.");
		}
		return "/editdep";
	}
	
	@RequestMapping(value = "/department.html", method = RequestMethod.GET)
	public String depSetup(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "p", required = false) Integer start,
			HttpSession session) throws Exception {
		map.addAttribute("page", "dep");
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
				if (start == null) start = 0;
				else start = start*size;
				
				int pageCount = dMapper.getDepCount();
				map.addAttribute("pageCount", pageCount);
				map.addAttribute("pageStart", start/size);
				map.addAttribute("perPage", size);
				map.addAttribute("pageCountperPage", pageCount/size);
				
				map.addAttribute("department", dMapper.getDepByPage(start, size));
				break;
			default:
				return "redirect:/logout.html";
		}
		return "/department";
	}
	
	@RequestMapping(value = "/department.html", method = RequestMethod.POST)
	public String department(ModelMap map, HttpSession session,
			@RequestParam(value="id", required=true) Integer id) throws Exception {
		map.addAttribute("page", "dep");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.removeDep(id)!=0) {
			return "redirect:/department.html";
		} else {
			map.addAttribute("error", "Хэсэг устгахад алдаа гарлаа.");
			return "/department";
		}
	}
	
}