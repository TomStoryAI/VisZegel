/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.fischzegel.viszegel.controller;

import de.fischzegel.viszegel.daos.interfaces.ProductDAO;
import de.fischzegel.viszegel.model.Customer;
import de.fischzegel.viszegel.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author tnowicki
 */
@Controller
public class ProductController extends AbstractController {

    @Autowired
    ProductDAO productDAO;

    @RequestMapping(value = "/create_product_result", method = {RequestMethod.GET, RequestMethod.POST})
    //public String add_customer(@RequestParam Map<String, String> allParams, Model model) {
    public String add_product(Product prod, Model mod) {
        productDAO.save(prod);
        mod.addAttribute("productEntity", new Product());
        return "billing/createProduct";
    }

    /**
     *
     * @param cus
     * @param mod
     * @return
     */
    @RequestMapping(value = "/view_products", method = RequestMethod.GET)
    public String mainView(@ModelAttribute Customer cus, Model mod) {
        mod.addAttribute("products", productDAO.list());
        return "billing/view_products";
    }

    /**
     * 
     * @param prod
     * @param mod
     * @param mode
     * @return 
     */
    @RequestMapping(value = "/change_delete_result_product", method = RequestMethod.POST)
    public String change_customer(@ModelAttribute Product prod, Model mod, @RequestParam(value = "mode", required = false) String mode) {
        logger.info("--> Change Product, Mode is set to : " + mode);
        if (mode.equals("edit")) {
            productDAO.save(prod);
        }
        if (mode.equals("delete")) {
            productDAO.delete(prod);
        }
        mod.addAttribute("products", productDAO.list());
        return "billing/view_products";
    }
}