package web.controller;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

import web.db.dao.Mapper;
import web.db.model.Constants;
import web.db.model.Moving;
import web.db.model.Spare;
import web.db.model.SpareCount;
import web.db.model.User;

@Controller
public class SpareController {
	
	@Autowired
	Mapper dMapper;

	@RequestMapping(value = "/addspare.html", method = RequestMethod.GET)
	public String spareAddSetup(ModelMap map, HttpSession session) throws Exception {
		map.addAttribute("page", "spare");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
		case 0:
			map.addAttribute("systems", dMapper.getSystems());
			break;
		default:
			map.addAttribute("systems", dMapper.getSystemsByDep(o.getDep_id()));
			break;
		}
		
		map.addAttribute("unit", dMapper.getUnits());
		map.addAttribute("location", dMapper.getLocations());
//		map.addAttribute("status", dMapper.getStatuses());
		return "/addspare";
	}
	
	
	@RequestMapping(value = "/addspare.html", method = RequestMethod.POST)
	public String spareAdd(ModelMap map, HttpSession session, HttpServletRequest request, Spare s) throws Exception {
		map.addAttribute("page", "spare");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.addSpare(s)!=0) {
			map.addAttribute("success", "Амжилттай нэмэгдлээ.");
			return "redirect:/spare.html";
		} else {
			map.addAttribute("error", "Нөөц сэлбэг нэмэхэд гарлаа.");
		}
		return "/addspare";
	}
	
	@RequestMapping(value = "/editspare.html", method = RequestMethod.GET)
	public String spareEditSetup(ModelMap map, HttpSession session,
			@RequestParam(value="id", required = true) long id) throws Exception {
		map.addAttribute("page", "spare");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
		case 0:
			map.addAttribute("systems", dMapper.getSystems());
			break;
		default:
			map.addAttribute("systems", dMapper.getSystemsByDep(o.getDep_id()));
			break;
		}
		Spare s = dMapper.getSpareById(id);
		map.addAttribute("sp", s);
		map.addAttribute("unit", dMapper.getUnits());
		map.addAttribute("location", dMapper.getLocations());
		map.addAttribute("status", dMapper.getStatuses());
		return "/editspare";
	}
	
	
	@RequestMapping(value = "/editspare.html", method = RequestMethod.POST)
	public String spareEdit(ModelMap map, HttpSession session, HttpServletRequest request, Spare s) throws Exception {
		map.addAttribute("page", "spare");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.updateSpare(s)!=0) {
			map.addAttribute("success", "Амжилттай засагдлаа.");
			return "redirect:/spare.html";
		} else {
			map.addAttribute("error", "Нөөц сэлбэг засахад алдаа гарлаа.");
		}
		return "/editspare";
	}
	
	@RequestMapping(value = "/spare.html", method = RequestMethod.GET)
	public String spareSetup(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "p", required = false) Integer start, 
			@RequestParam(value = "tid", required = false) Integer tid,
			@RequestParam(value = "sysid", required = false) Integer sysid,
			@RequestParam(value = "depid", required = false) Integer depid, 
			@RequestParam(value = "locid", required = false) Integer locid, 
			@RequestParam(value = "uid", required = false) String uid, 
			@RequestParam(value = "stid", required = false) Integer stid,
			@RequestParam(value = "fn", required = false) String fn, 
			@RequestParam(value = "fs", required = false) String fs, 
			@RequestParam(value = "fr", required = false) String fr, 
			@RequestParam(value = "fa", required = false) String fa,
			HttpSession session) throws Exception {
		map.addAttribute("page", "spare");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		
		int size = Constants.pageLimit;
		if (start == null) start = 0;
		else start = start*size;
		
		tid = tid==null?0:tid;
		sysid = sysid==null?0:sysid;
		depid = depid==null?0:depid;
		locid = locid==null?0:locid;
		uid = uid==null?"":uid;
		stid = stid==null?0:stid;
		fn = fn==null?"":fn;
		fs = fs==null?"":fs;
		fr = fr==null?"":fr;
		fa = fa==null?"":fa;
		String query ="";
		
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
			case 0:
			case 4:
				query = "where 1=1";
				if (tid!=0) query += (" and (select ty.id from t_type ty where ty.id=un.type_id)=" + tid);
				if (sysid!=0) query += (" and s.sys_id=" + sysid);
				if (depid!=0) query += (" and s.dep_id=" + depid);
				if (locid!=0) query += (" and s.loc_id=" + locid);
				if (!"".equals(uid)) query += (" and s.user_name='" + uid + "'");
				if (stid!=0) query += (" and s.status_id=" + stid);
				if (!"".equals(fn)) query += (" and un.c_name like '%" + fn +"%'");
				if (!"".equals(fs)) query += (" and s.serial_key like '%" + fs +"%'");
				if (!"".equals(fr)) query += (" and s.product_num like '%" + fr +"%'");
				if (!"".equals(fa)) query += (" and s.asset_id like '%" + fa +"%'");
				map.addAttribute("dep", dMapper.getDepartments());
				map.addAttribute("system", dMapper.getSystems());
				break;
			default:
				query = "where s.dep_id="+o.getDep_id();
				if (tid!=0) query += (" and (select ty.id from t_type ty where ty.id=un.type_id)=" + tid);
				if (sysid!=0) query += (" and s.sys_id=" + sysid);
				if (depid!=0) query += (" and s.dep_id=" + depid);
				if (locid!=0) query += (" and s.loc_id=" + locid);
				if (!"".equals(uid)) query += (" and s.user_name='" + uid + "'");
				if (stid!=0) query += (" and s.status_id=" + stid);
				if (!"".equals(fn)) query += (" and un.c_name like '%" + fn +"%'");
				if (!"".equals(fs)) query += (" and s.serial_key like '%" + fs +"%'");
				if (!"".equals(fr)) query += (" and s.product_num like '%" + fr +"%'");
				if (!"".equals(fa)) query += (" and s.asset_id like '%" + fa +"%'");
				map.addAttribute("system", dMapper.getSystemsByDep(o.getDep_id()));
				break;
		}
		
		map.addAttribute("tid", tid);
		map.addAttribute("sysid", sysid);
		map.addAttribute("depid", depid);
		map.addAttribute("locid", locid);
		map.addAttribute("uid", uid);
		map.addAttribute("stid", stid);
		map.addAttribute("fn", fn);
		map.addAttribute("fs", fs);
		map.addAttribute("fr", fr);
		map.addAttribute("fa", fa);
		
		int pageCount = dMapper.getSpareFilterCount(query);
		
		map.addAttribute("pageCount", pageCount);
		map.addAttribute("pageStart", start/size);
		map.addAttribute("perPage", size);
		map.addAttribute("pageCountperPage", pageCount/size);
		
		map.addAttribute("sparelist", dMapper.getSpareFilter(query, start, size));
		
		map.addAttribute("type", dMapper.getTypes());
		map.addAttribute("status", dMapper.getStatuses());
		
		map.addAttribute("unit", dMapper.getUnits());
		map.addAttribute("user", dMapper.getAllUsers());
		map.addAttribute("loc", dMapper.getLocations());
		
		return "/sparelist";
	}
	
	@RequestMapping(value = "/spare.html", method = RequestMethod.POST)
	public String spare(ModelMap map, HttpSession session, HttpServletRequest request,
			@RequestParam(value="id", required=true) Integer id) throws Exception {
		map.addAttribute("page", "spare");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.removeSpare(id)!=0) {
			return "redirect:/spare.html";
		} else {
			map.addAttribute("error", "Нөөц сэлбэг устгахад алдаа гарлаа.");
			return "/sparelist";
		}
	}
	
	@RequestMapping(value = "/sparel.html", method = RequestMethod.POST)
	//@RequestMapping(value = "/sparel.html")
	public String spareFilter(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "tid", required = true) Integer tid,
			@RequestParam(value = "sysid", required = true) Integer sysid,
			@RequestParam(value = "depid", required = false) Integer depid, 
			@RequestParam(value = "locid", required = true) Integer locid, 
			@RequestParam(value = "uid", required = true) String uid, 
			@RequestParam(value = "stid", required = true) Integer stid,
			@RequestParam(value = "fn", required = true) String fn, 
			@RequestParam(value = "fs", required = true) String fs, 
			@RequestParam(value = "fr", required = true) String fr, 
			@RequestParam(value = "fa", required = true) String fa,
			HttpSession session) throws Exception {
		map.addAttribute("page", "spare");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		
		int size = Constants.pageLimit;
		int start = 0;
		
		String query = "";
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
			case 0:
			case 4:
				query = "where 1=1";
				if (tid!=0) query += (" and (select ty.id from t_type ty where ty.id=un.type_id)=" + tid);
				if (sysid!=0) query += (" and s.sys_id=" + sysid);
				if (depid!=0) query += (" and s.dep_id=" + depid);
				if (locid!=0) query += (" and s.loc_id=" + locid);
				if (!"".equals(uid)) query += (" and s.user_name='" + uid + "'");
				if (stid!=0) query += (" and s.status_id=" + stid);
				if (!"".equals(fn)) query += (" and un.c_name like '%" + fn +"%'");
				if (!"".equals(fs)) query += (" and s.serial_key like '%" + fs +"%'");
				if (!"".equals(fr)) query += (" and s.product_num like '%" + fr +"%'");
				if (!"".equals(fa)) query += (" and s.asset_id like '%" + fa +"%'");
				map.addAttribute("dep", dMapper.getDepartments());
				map.addAttribute("system", dMapper.getSystems());
				break;
			default:
				query = "where s.dep_id="+o.getDep_id();
				if (tid!=0) query += (" and (select ty.id from t_type ty where ty.id=un.type_id)=" + tid);
				if (sysid!=0) query += (" and s.sys_id=" + sysid);
				if (depid!=0) query += (" and s.dep_id=" + depid);
				if (locid!=0) query += (" and s.loc_id=" + locid);
				if (!"".equals(uid)) query += (" and s.user_name='" + uid + "'");
				if (stid!=0) query += (" and s.status_id=" + stid);
				if (!"".equals(fn)) query += (" and un.c_name like '%" + fn +"%'");
				if (!"".equals(fs)) query += (" and s.serial_key like '%" + fs +"%'");
				if (!"".equals(fr)) query += (" and s.product_num like '%" + fr +"%'");
				if (!"".equals(fa)) query += (" and s.asset_id like '%" + fa +"%'");
				
				map.addAttribute("system", dMapper.getSystemsByDep(o.getDep_id()));
				break;
		}
		
		map.addAttribute("sparelist", dMapper.getSpareFilter(query, start, size));
		
		return "/admin/sparelist";
	}
	
	@RequestMapping(value = "/sparep.html", method = RequestMethod.POST)
	//@RequestMapping(value = "/sparep.html")
	public String spareFilterPage(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "tid", required = true) Integer tid,
			@RequestParam(value = "sysid", required = true) Integer sysid,
			@RequestParam(value = "depid", required = false) Integer depid, 
			@RequestParam(value = "locid", required = true) Integer locid, 
			@RequestParam(value = "uid", required = true) String uid, 
			@RequestParam(value = "stid", required = true) Integer stid,
			@RequestParam(value = "fn", required = true) String fn, 
			@RequestParam(value = "fs", required = true) String fs, 
			@RequestParam(value = "fr", required = true) String fr, 
			@RequestParam(value = "fa", required = true) String fa,
			HttpSession session) throws Exception {
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		
		String query = "";
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
			case 0:
			case 4:
				query = "where 1=1";
				if (tid!=0) query += (" and (select ty.id from t_type ty where ty.id=un.type_id)=" + tid);
				if (sysid!=0) query += (" and s.sys_id=" + sysid);
				if (depid!=0) query += (" and s.dep_id=" + depid);
				if (locid!=0) query += (" and s.loc_id=" + locid);
				if (!"".equals(uid)) query += (" and s.user_name='" + uid + "'");
				if (stid!=0) query += (" and s.status_id=" + stid);
				if (!"".equals(fn)) query += (" and un.c_name like '%" + fn +"%'");
				if (!"".equals(fs)) query += (" and s.serial_key like '%" + fs +"%'");
				if (!"".equals(fr)) query += (" and s.product_num like '%" + fr +"%'");
				if (!"".equals(fa)) query += (" and s.asset_id like '%" + fa +"%'");
				map.addAttribute("dep", dMapper.getDepartments());
				map.addAttribute("system", dMapper.getSystems());
				break;
			default:
				query = "where s.dep_id="+o.getDep_id();
				if (tid!=0) query += (" and (select ty.id from t_type ty where ty.id=un.type_id)=" + tid);
				if (sysid!=0) query += (" and s.sys_id=" + sysid);
				if (depid!=0) query += (" and s.dep_id=" + depid);
				if (locid!=0) query += (" and s.loc_id=" + locid);
				if (!"".equals(uid)) query += (" and s.user_name='" + uid + "'");
				if (stid!=0) query += (" and s.status_id=" + stid);
				if (!"".equals(fn)) query += (" and un.c_name like '%" + fn +"%'");
				if (!"".equals(fs)) query += (" and s.serial_key like '%" + fs +"%'");
				if (!"".equals(fr)) query += (" and s.product_num like '%" + fr +"%'");
				if (!"".equals(fa)) query += (" and s.asset_id like '%" + fa +"%'");
				map.addAttribute("system", dMapper.getSystemsByDep(o.getDep_id()));
				break;
		}

		int pageCount = dMapper.getSpareFilterCount(query);
		int size = Constants.pageLimit;
		int start = 0;
		map.addAttribute("pageCount", pageCount);
		map.addAttribute("pageStart", start/size);
		map.addAttribute("perPage", size);
		map.addAttribute("pageCountperPage", pageCount/size);
		
		map.addAttribute("tid", tid);
		map.addAttribute("sysid", sysid);
		map.addAttribute("depid", depid);
		map.addAttribute("locid", locid);
		map.addAttribute("uid", uid);
		map.addAttribute("stid", stid);
		map.addAttribute("fn", fn);
		map.addAttribute("fs", fs);
		map.addAttribute("fr", fr);
		map.addAttribute("fa", fa);
		
		return "/admin/sparepage";
	}
 
	@RequestMapping(value = "/trspare.html", method = RequestMethod.GET)
	public String spareTransferSetup(ModelMap map, HttpSession session,
			@RequestParam(value="id", required = true) long id) throws Exception {
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		Spare s = dMapper.getSpareByIdT(id);
		map.addAttribute("sp", s);
		map.addAttribute("location", dMapper.getLocations());
		map.addAttribute("status", dMapper.getStatuses());
		return "/admin/transferspare";
	}
	
	@RequestMapping(value = "/trspare.html", method = RequestMethod.POST)
	public String spareTransfer(ModelMap map, HttpSession session,
			Moving m) throws Exception {
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (dMapper.addMoving(m)!=0) {
			dMapper.updateSpareAfterMoving(m.getLoc_to(), m.getStatus_to_id(), m.getSpare_id());
			MainController.insertLog();
			map.addAttribute("success", "Амжилттай шилжлээ.");
			return "/admin/close";
		} else {
			map.addAttribute("error", "Шилжүүлэг хийхэд алдаа гарлаа.");
			return "/admin/transferspare";
		}
	}
	
	@RequestMapping(value = "/sparecount.html", method = RequestMethod.GET)
	public String spareCount(ModelMap map, HttpSession session,
			@RequestParam(value = "p", required = false) Integer start) throws Exception {
		map.addAttribute("page", "spare");
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
		
		List<SpareCount> spcountlist = new ArrayList();
		
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
			case 0:
				pageCount = dMapper.getSpareCountC();
				List<Spare> sp = dMapper.getSpareCount(start, size);
				for (int i=0; i<sp.size(); i++) {
					Spare s = (Spare)sp.get(i);
					SpareCount spcount = new SpareCount();
					spcount.setSp(s);
					spcount.setSt(dMapper.getSpareCountByStatus(s.getUnit_id()));
					spcountlist.add(spcount);
				}
				break;
			default:
				pageCount = dMapper.getSpareCountByDepC(o.getDep_id());
				List<Spare> sp1 = dMapper.getSpareCountByDep(o.getDep_id(), start, size);
				for (int i=0; i<sp1.size(); i++) {
					Spare s = (Spare)sp1.get(i);
					SpareCount spcount = new SpareCount();
					spcount.setSp(s);
					spcount.setSt(dMapper.getSpareCountByStatusByDep(o.getDep_id(), s.getUnit_id()));
					spcountlist.add(spcount);
				}
				break;
		}
		
		map.addAttribute("pageCount", pageCount);
		map.addAttribute("pageStart", start/size);
		map.addAttribute("perPage", size);
		map.addAttribute("pageCountperPage", pageCount/size);
		
		map.addAttribute("sparecount", spcountlist);
		map.addAttribute("statuses", dMapper.getStatuses());
		return "/sparecount";
	}

	
	
	@RequestMapping(value = "/sparexls.html", method = RequestMethod.POST)
	public String spareExcel(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "tid", required = true) Integer tid,
			@RequestParam(value = "sysid", required = true) Integer sysid,
			@RequestParam(value = "depid", required = false) Integer depid, 
			@RequestParam(value = "locid", required = true) Integer locid, 
			@RequestParam(value = "uid", required = true) String uid, 
			@RequestParam(value = "stid", required = true) Integer stid,
			@RequestParam(value = "fn", required = true) String fn, 
			@RequestParam(value = "fs", required = true) String fs, 
			@RequestParam(value = "fr", required = true) String fr, 
			@RequestParam(value = "fa", required = true) String fa,
			HttpSession session) throws Exception {
		map.addAttribute("page", "spare");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		String query = "";
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
			case 0:
			case 4:
				query = "where 1=1";
				if (tid!=0) query += (" and (select ty.id from t_type ty where ty.id=un.type_id)=" + tid);
				if (sysid!=0) query += (" and s.sys_id=" + sysid);
				if (depid!=0) query += (" and s.dep_id=" + depid);
				if (locid!=0) query += (" and s.loc_id=" + locid);
				if (!"".equals(uid)) query += (" and s.user_name='" + uid + "'");
				if (stid!=0) query += (" and s.status_id=" + stid);
				if (!"".equals(fn)) query += (" and un.c_name like '%" + fn +"%'");
				if (!"".equals(fs)) query += (" and s.serial_key like '%" + fs +"%'");
				if (!"".equals(fr)) query += (" and s.product_num like '%" + fr +"%'");
				if (!"".equals(fa)) query += (" and s.asset_id like '%" + fa +"%'");
				map.addAttribute("dep", dMapper.getDepartments());
				map.addAttribute("system", dMapper.getSystems());
				break;
			default:
				query = "where s.dep_id="+o.getDep_id();
				if (tid!=0) query += (" and (select ty.id from t_type ty where ty.id=un.type_id)=" + tid);
				if (sysid!=0) query += (" and s.sys_id=" + sysid);
				if (depid!=0) query += (" and s.dep_id=" + depid);
				if (locid!=0) query += (" and s.loc_id=" + locid);
				if (!"".equals(uid)) query += (" and s.user_name='" + uid + "'");
				if (stid!=0) query += (" and s.status_id=" + stid);
				if (!"".equals(fn)) query += (" and un.c_name like '%" + fn +"%'");
				if (!"".equals(fs)) query += (" and s.serial_key like '%" + fs +"%'");
				if (!"".equals(fr)) query += (" and s.product_num like '%" + fr +"%'");
				if (!"".equals(fa)) query += (" and s.asset_id like '%" + fa +"%'");
				map.addAttribute("system", dMapper.getSystemsByDep(o.getDep_id()));
				break;
		}

		List<Spare> sparelist = dMapper.getSpareFilterXLS(query);
		Date dNow = new Date( );
	    SimpleDateFormat ft =  new SimpleDateFormat ("yyyyMMddHHmmss");
		String filename="D:/spare_"+ft.format(dNow)+".xls" ;
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("Spare"+ft.format(dNow));

		HSSFRow rowhead = sheet.createRow((short)0);
		rowhead.createCell((short) 0).setCellValue("Нэр");
		rowhead.createCell((short) 1).setCellValue("Төрөл");
		rowhead.createCell((short) 2).setCellValue("Систем");
		rowhead.createCell((short) 3).setCellValue("Байршил");
		rowhead.createCell((short) 4).setCellValue("Огноо");
		rowhead.createCell((short) 5).setCellValue("Сериал.№");
		rowhead.createCell((short) 6).setCellValue("Продакт.№");
		rowhead.createCell((short) 7).setCellValue("Бүртгэсэн");
		rowhead.createCell((short) 8).setCellValue("Төлөв");
		rowhead.createCell((short) 9).setCellValue("Ассет.№");
		for (int i=0; i<sparelist.size(); i++) {
			Spare sp = (Spare) sparelist.get(i);
			HSSFRow row = sheet.createRow((short)i+1);
			row.createCell((short) 0).setCellValue(sp.getUnitName());
			row.createCell((short) 1).setCellValue(sp.getUnitType());
			row.createCell((short) 2).setCellValue(sp.getSysName());
			row.createCell((short) 3).setCellValue(sp.getLocName());
			row.createCell((short) 4).setCellValue(sp.getC_date());
			row.createCell((short) 5).setCellValue(sp.getSerial_key());
			row.createCell((short) 6).setCellValue(sp.getProduct_num());
			row.createCell((short) 7).setCellValue(sp.getUser_name());
			row.createCell((short) 8).setCellValue(sp.getStatName());
			row.createCell((short) 9).setCellValue(sp.getAsset_id());
		}
		FileOutputStream fileOut = new FileOutputStream(filename);
		hwb.write(fileOut);
		fileOut.close();
		System.out.println("Your excel file has been generated!");
		return null;
	}
}