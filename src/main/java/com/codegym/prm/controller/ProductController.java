package com.codegym.prm.controller;

import com.codegym.prm.model.Product;
import com.codegym.prm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping("/create")
    public ModelAndView showCreateForm(){
        ModelAndView modelAndView = new ModelAndView("/product/create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }
    @PostMapping("/create")
    public ModelAndView saveProduct(@ModelAttribute("product") Product product){
            productService.save(product);
            ModelAndView modelAndView = new ModelAndView("/product/create");
            modelAndView.addObject("product", new Product());
            modelAndView.addObject("message", "done");
            return modelAndView;
    }
    @GetMapping("/products")
    public ModelAndView listProducts(){
        ModelAndView modelAndView = new ModelAndView("/product/list");
        modelAndView.addObject("products", productService.findAll());
        return modelAndView;
    }
    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id){
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/product/edit");
            modelAndView.addObject("product", productOptional.get());
            return modelAndView;
        }
        return new ModelAndView("/error.404");
    }
    @PostMapping("/edit")
    public ModelAndView updateProduct(@ModelAttribute("product") Product product){
        productService.save(product);
        ModelAndView modelAndView = new ModelAndView("/product/edit");
        modelAndView.addObject("product", new Product());
        modelAndView.addObject("message", "done");
        return modelAndView;
    }
    @GetMapping("/delete/{id}")
    public ModelAndView showDelForm(@PathVariable Long id){
        Optional<Product> productOptional = productService.findById(id);
        if (productOptional.isPresent()){
            ModelAndView modelAndView = new ModelAndView("/product/delete");
            modelAndView.addObject("product", productOptional.get());
            return modelAndView;
        }
        return new ModelAndView("/error.404");
    }
    @PostMapping("/delete")
    public String deleteProduct(@ModelAttribute("product") Product product){
        productService.remove(product.getId());
        return "redirect:products";
    }
}
