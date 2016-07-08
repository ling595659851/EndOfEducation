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
import cn.edu.bupt.springmvc.web.model.TypeRegular;
import cn.edu.bupt.springmvc.web.service.TypeRegularService;

@Controller
@RequestMapping(value = "/type")
public class TypeRegularController extends GenericController {

	@Resource
	private TypeRegularService service;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getIndex(Model model) {
		List<TypeRegular> list = service.selectList();
		model.addAttribute("lists",list);
		System.out.println(list);
		return "typeindex";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateRegular(@ModelAttribute TypeRegular type,HttpServletRequest request,HttpServletResponse response) throws IOException {
		System.out.println(type);
		if(type != null) {
			if(type.getId() == null) {
				type.setCount(0L);
				service.insert(type);
			} else {
				service.update(type);
			}
		}
		response.sendRedirect("index");
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addRegular() {
		return "addType";
	}
	
	@RequestMapping(value = "/getOne", method = RequestMethod.GET)
	public String showRegular(@RequestParam("id") Long id,Model model,HttpServletRequest request) {
		if(id < 0) {
			return null;
		}
		TypeRegular type = service.selectById(id);
		model.addAttribute("type", type);
		return "typeDetail";
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
		List<TypeRegular> list = service.getByTypeAndCountForName(name, type, realCount);
		model.addAttribute("lists",list);
		return "typeindex";
	}
}
