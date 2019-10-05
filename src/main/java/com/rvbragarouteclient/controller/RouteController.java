package com.rvbragarouteclient.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rvbragarouteclient.domain.PlannedStop;
import com.rvbragarouteclient.domain.Route;
import com.rvbragarouteclient.service.RouteService;

@Controller
public class RouteController {
	@Autowired
	private RouteService service;

	@GetMapping("/")
	public ModelAndView main() {
		ModelAndView mv = new ModelAndView("/main");
		return mv;
	}

	@GetMapping(value = "/routes")
	public ModelAndView findaAll() {

		ModelAndView mv = new ModelAndView("/routes");

		List<Route> list = service.findAll();

		if (list == null)
			mv.addObject("status", "Servidor offline!");
		else
			mv.addObject("status", "Servidor online!");

		mv.addObject("list", list);

		return mv;
	}

	@GetMapping(value = "/toView/{id}")
	public ModelAndView toView(@PathVariable("id") Long id) {
		ModelAndView mv = new ModelAndView("/addroute");
		Route route = service.findById(id);
		mv.addObject("route", route);
		return mv;
	}

	@GetMapping(value = "/addRoute")
	public ModelAndView addRoute(Route route) {
		ModelAndView mv = new ModelAndView("/addroute");
		List<PlannedStop> list = new ArrayList<PlannedStop>();
		route.setPlannedStops(list);
		System.out.println(route.getPlannedStops().size() + "paradas cadastradas na rota");
		mv.addObject("route", route);
		return mv;
	}

	// *****************Dynamics
	// Fields**********************************************
	@RequestMapping(value = "/addRoute", params = { "addItem" })
	public ModelAndView addRow(final Route route, final BindingResult bindingResult) {
		try {
			route.getPlannedStops().add(new PlannedStop());
		} catch (NullPointerException n) {
			route.setPlannedStops(new ArrayList<>());
			route.getPlannedStops().add(new PlannedStop());
		}
		ModelAndView mv = new ModelAndView("/addroute");
		mv.addObject("route", route);
		return mv;
	}

	@RequestMapping(value = "/addRoute", params = { "removeItem" })
	public ModelAndView removeRow(final Route route, final BindingResult bindingResult, final HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("removeItem"));
		if (route.getPlannedStops().get(rowId.intValue()).getId() != null) {
			System.out
					.println("Deletando PlannedStop com ID - " + route.getPlannedStops().get(rowId.intValue()).getId());
			service.deletePlannedStop(route.getPlannedStops().get(rowId.intValue()),
					route.getPlannedStops().get(rowId.intValue()).getId());
		}
		route.getPlannedStops().remove(rowId.intValue());
		ModelAndView mv = new ModelAndView("/addroute");
		mv.addObject("route", route);
		return mv;
	}
	// ***************************************************************

	@PostMapping(value = "/addRoute")
	public ModelAndView saveRoute(@Valid Route route, BindingResult bind) {
		if (bind.hasErrors())
			return addRoute(route);
		service.saveRoute(route);
		ModelAndView mv = new ModelAndView("redirect:/routes");

		return mv;
	}
	
	@GetMapping(value = "/routes-file")
	public ModelAndView saveRountesInFile() {

		ModelAndView mv = new ModelAndView("redirect:/routes");
		service.createFile();			
		mv.addObject("status", "Rotas Salvas em disco com sucesso!");	
		List<Route> list = service.findAll();
		mv.addObject("list", list);		
		return mv;
	}
	
	@GetMapping("/routes-file-load")
	public ModelAndView loadRoutesFromFile() {
		ModelAndView mv = new ModelAndView("redirect:/routes");
		service.loadFile();			
		mv.addObject("status", "Rotas carregadas com sucesso!");	
		List<Route> list = service.findAll();
		mv.addObject("list", list);		
		return mv;
		
	}
}
