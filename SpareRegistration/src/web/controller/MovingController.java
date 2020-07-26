package web.controller;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import web.db.dao.Mapper;
import web.db.model.Constants;
import web.db.model.Moving;
import web.db.model.User;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;


@Controller
public class MovingController {
	
	@Autowired
	Mapper dMapper;
	
	
	@RequestMapping(value = "/moving.html", method = RequestMethod.GET)
	public String movingSetup(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "sp", required = false) Integer sp,
			@RequestParam(value = "p", required = false) Integer start,
			@RequestParam(value = "sd", required = false) String sdate,
			@RequestParam(value = "ed", required = false) String edate,
			HttpSession session) throws Exception {
		map.addAttribute("page", "moving");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		if (sp!=null)
			map.addAttribute("moving", dMapper.getMovingHistory(sp));
		else {
			int size = Constants.pageLimit;
			if (start == null) start = 0;
			else start = start*size;
			
			int pageCount = 0;
					
			int role = Integer.parseInt(o.getC_role());
			if (sdate==null) sdate="";
			if (edate==null) edate="";
			if ("".equals(sdate) && "".equals(edate)) {
				switch (role) {
					case 0:
					case 4:
						pageCount = dMapper.getMovingCount();
						map.addAttribute("moving", dMapper.getMoving(start, size));
						break;
					default:
						pageCount = dMapper.getMovingCountByDep(o.getDep_id());
						map.addAttribute("moving", dMapper.getMovingByDep(o.getDep_id(), start, size));
						break;
				}
			} else {
				switch (role) {
				case 0:
				case 4:
					pageCount = dMapper.getMovingCountByDate(sdate+" 00:00:00", edate+ " 23:59:59");
					map.addAttribute("moving", dMapper.getMovingByDate(sdate+" 00:00:00", edate+ " 23:59:59", start, size));
					break;
				default:
					pageCount = dMapper.getMovingCountByDepDate(o.getDep_id(), sdate+" 00:00:00", edate+ " 23:59:59");
					map.addAttribute("moving", dMapper.getMovingByDepDate(o.getDep_id(), sdate+" 00:00:00", edate+ " 23:59:59", start, size));
					break;
				}
				map.addAttribute("sdate", sdate);
				map.addAttribute("edate", edate);
			}
			
			map.addAttribute("pageCount", pageCount);
			map.addAttribute("pageStart", start/size);
			map.addAttribute("perPage", size);
			map.addAttribute("pageCountperPage", pageCount/size);
		}
		
		return "/moving";
	}
	
	@RequestMapping(value = "/movingl.html", method = RequestMethod.POST)
	public String movingList(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "sdate", required = true) String sdate,
			@RequestParam(value = "edate", required = true) String edate,
			HttpSession session) throws Exception {
		map.addAttribute("page", "moving");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		
		int size = Constants.pageLimit;
		int start = 0;
					
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
			case 0:
			case 4:
				map.addAttribute("moving", dMapper.getMovingByDate(sdate+" 00:00:00", edate+ " 23:59:59", start, size));
				break;
			default:
				map.addAttribute("moving", dMapper.getMovingByDepDate(o.getDep_id(), sdate+" 00:00:00", edate+ " 23:59:59", start, size));
				break;
		}
		
		return "/admin/movinglist";
	}
	
	@RequestMapping(value = "/movingp.html", method = RequestMethod.POST)
	public String movingPage(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "sdate", required = true) String sdate,
			@RequestParam(value = "edate", required = true) String edate,
			HttpSession session) throws Exception {
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);
		
		int size = Constants.pageLimit;
		int start = 0;
		int pageCount = 0;
		
		int role = Integer.parseInt(o.getC_role());
		switch (role) {
			case 0:
			case 4:
				pageCount = dMapper.getMovingCountByDate(sdate+" 00:00:00", edate+ " 23:59:59");
				map.addAttribute("pageCount", pageCount);
			default:
				pageCount = dMapper.getMovingCountByDepDate(o.getDep_id(), sdate+" 00:00:00", edate+ " 23:59:59");
				map.addAttribute("pageCount", pageCount);
				break;
		}
		map.addAttribute("pageStart", start/size);
		map.addAttribute("perPage", size);
		map.addAttribute("pageCountperPage", pageCount/size);
		
		map.addAttribute("sdate", sdate);
		map.addAttribute("edate", edate);
		
		return "/admin/movingpage";
	}
	
	@RequestMapping(value = "/movingxls.html", method = RequestMethod.POST)
	public String movingExcel(ModelMap map, HttpServletRequest request,
			@RequestParam(value = "sdate", required = true) String sdate,
			@RequestParam(value = "edate", required = true) String edate,
			HttpSession session) throws Exception {
		map.addAttribute("page", "moving");
		boolean logged = null != session.getAttribute("cmsadmin");
		if (!logged) {
			return "redirect:/index.html";
		}
		User o = (User) session.getAttribute("cmsadmin");
		map.addAttribute("op", o);

		List<Moving> movinglist = null;
					
		int role = Integer.parseInt(o.getC_role());
		if ("".equals(sdate) && "".equals(edate)) {
			switch (role) {
				case 0:
				case 4:
					movinglist = dMapper.getMovingXLS();
					break;
				default:
					movinglist = dMapper.getMovingByDepXLS(o.getDep_id());
					break;
			}
		} else {
			switch (role) {
			case 0:
			case 4:
				movinglist = dMapper.getMovingByDateXLS(sdate+" 00:00:00", edate+ " 23:59:59");
				break;
			default:
				movinglist = dMapper.getMovingByDepDateXLS(o.getDep_id(), sdate+" 00:00:00", edate+ " 23:59:59");
				break;
			}
		}
		
		Date dNow = new Date( );
	    SimpleDateFormat ft =  new SimpleDateFormat ("yyyyMMddHHmmss");
		String filename="D:/moving_"+ft.format(dNow)+".xls" ;
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("Moving"+ft.format(dNow));

		HSSFRow rowhead = sheet.createRow((short)0);
		rowhead.createCell((short) 0).setCellValue("Нэр");
		rowhead.createCell((short) 1).setCellValue("Сериал");
		rowhead.createCell((short) 2).setCellValue("Хаанаас");
		rowhead.createCell((short) 3).setCellValue("Хаашаа");
		rowhead.createCell((short) 4).setCellValue("Өмнөх төлөв");
		rowhead.createCell((short) 5).setCellValue("Дараах төлөв");
		rowhead.createCell((short) 6).setCellValue("Огноо");
		rowhead.createCell((short) 7).setCellValue("Тайлбар");
		rowhead.createCell((short) 8).setCellValue("Гүйцэтгэсэн");
		rowhead.createCell((short) 9).setCellValue("Хэсэг");
		for (int i=0; i<movinglist.size(); i++) {
			Moving m = (Moving) movinglist.get(i);
			HSSFRow row = sheet.createRow((short)i+1);
			row.createCell((short) 0).setCellValue(m.getSpareName());
			row.createCell((short) 1).setCellValue(m.getSerialKey());
			row.createCell((short) 2).setCellValue(m.getLocFrom());
			row.createCell((short) 3).setCellValue(m.getLocTo());
			row.createCell((short) 4).setCellValue(m.getStatFrom());
			row.createCell((short) 5).setCellValue(m.getStatTo());
			row.createCell((short) 6).setCellValue(m.getC_date());
			row.createCell((short) 7).setCellValue(m.getComment());
			row.createCell((short) 8).setCellValue(m.getUser_name());
			row.createCell((short) 9).setCellValue(m.getDepName());
		}
		FileOutputStream fileOut = new FileOutputStream(filename);
		hwb.write(fileOut);
		fileOut.close();
		System.out.println("Your excel file has been generated!");
		return null;
	}
	
}