package com.coffeeshop.menu.controller;

import com.coffeeshop.menu.model.Product;
import com.coffeeshop.menu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/products") // This means all URLs start with http://localhost:8080/products/
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/") // This maps to the URL http://localhost:8080/products/
   public String viewHomePage(Model model) {
        model.addAttribute("listProducts", productService.getAllProducts());
        return "menu";
    }

    @GetMapping("/showNewProductForm")
    public String showNewProductForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "add-new-product";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products/";
    }

    @RequestMapping("/list") // This maps to the URL http://localhost:8080/products/list
    public String listProducts(Model productListModel) { // Model argument is used to pass data to the view
        productListModel.addAttribute("listProducts", productService.getAllProducts()); // Add the productsList to the model
        return "menu";  // This returns the view name, that is, the HTML file name
    }

    @RequestMapping("/details/{id}") // This maps to the URL http://localhost:8080/products/details/{id}
    @ResponseBody
    public String getProductDetailsByID(@PathVariable int id){
        for (Product product : productService.getAllProducts()) {
            if (product.getId() == id) {
               return "<strong>Requested Product Details:</strong><hr>"
                        + "Product ID: " + product.getId() + "<br>"
                        + "Name: " + product.getName() + "<br>"
                        + "Price: $" + product.getPrice();
            }
        }
        return "Product not found!";
    }

    @GetMapping("/ShowFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        return "update-product";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable int id){
        this.productService.deleteProductById(id);
        return "redirect:/products/";
    }

}
