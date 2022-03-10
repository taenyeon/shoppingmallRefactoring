package com.shop.myapp.controller;

import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.shop.myapp.dto.Item;
import com.shop.myapp.service.ItemService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private final ItemService itemService;
	
	public HomeController(ItemService itemService) {
		this.itemService = itemService;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		List<Item> newItems = itemService.findNewItems();
		List<Item> hitItems = itemService.findHitItems();
		List<Item> sellItems = itemService.findSellItems();
		model.addAttribute("newItems",newItems);
		model.addAttribute("hitItems",hitItems);
		model.addAttribute("sellItems",sellItems);
		return "/item/home";
	}
	
}
