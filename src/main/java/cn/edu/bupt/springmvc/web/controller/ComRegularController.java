package cn.edu.bupt.springmvc.web.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.edu.bupt.springmvc.core.generic.GenericController;
import cn.edu.bupt.springmvc.web.model.ComRegular;
import cn.edu.bupt.springmvc.web.service.ComRegularService;
import cn.edu.bupt.springmvc.web.service.UrlOperationService;

@Controller
@RequestMapping(value = "/com")
public class ComRegularController extends GenericController  {

	@Resource
	private ComRegularService service;
	
	@Resource
	private UrlOperationService urlOp;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getIndex(Model model) {
		List<ComRegular> list = service.selectList();
		model.addAttribute("lists",list);
		return "comindex";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateRegular(@ModelAttribute ComRegular com,HttpServletRequest request,HttpServletResponse response) throws IOException {
		System.out.println(com);
		if(com != null) {
			if(com.getId() == null) {
				com.setCount(0L);
				service.insert(com);
			} else {
				service.update(com);
			}
		}
		response.sendRedirect("index");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addRegular() {
		return "addCom";
	}
	
	@RequestMapping(value = "/getOne", method = RequestMethod.GET)
	public String showRegular(@RequestParam("id") Long id,Model model,HttpServletRequest request) {
		if(id < 0) {
			return null;
		}
		ComRegular com = service.selectById(id);
		model.addAttribute("com", com);
		return "comDetail";
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search(HttpServletRequest request,Model model) throws IOException {
		String type = request.getParameter("type");
		String count = request.getParameter("count");
		String name = request.getParameter("empty");
		if(name != null && name.equals("on")) {
			name = "";
		} else {
			name = null;
		}
		if(type == null || type.isEmpty()) {
			type = null;
		}
		Long realCount = 0L;
		try{
			realCount = Long.valueOf(count);
		} catch(Exception e) {
		}
		List<ComRegular> list = service.getByTypeAndCountForCompany(name, type, realCount);
		model.addAttribute("lists",list);
		return "comindex";
	}
}
