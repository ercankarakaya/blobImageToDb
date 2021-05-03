package com.ercan.controller;

import com.ercan.entity.Product;
import com.ercan.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${uploadDir}")
    private String uploadFolder;

    @Autowired
    private ProductService productService;

    @GetMapping(value = {"/","/home"})
    public String addProductPage(){
        return "index";
    }

    @PostMapping("/product/saveProduct")
    public @ResponseBody
    ResponseEntity<?> createEmployee(@RequestParam("name") String name,
                                     @RequestParam("price") double price, @RequestParam("description") String description, Model model,
                                     HttpServletRequest request, final @RequestParam("image") MultipartFile file) {
        try {
            //String uploadDirectory = System.getProperty("user.dir") + uploadFolder;
            String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
            logger.info("uploadDirectory:: " + uploadDirectory);
            String fileName = file.getOriginalFilename();
            String filePath = Paths.get(uploadDirectory, fileName).toString();
            logger.info("FileName: " + file.getOriginalFilename());
            if (fileName == null || fileName.contains("..")) {
                model.addAttribute("invalid", "Sorry! Filename contains invalid path sequence \" + fileName");
                return new ResponseEntity<>("Sorry! Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
            }
            String[] names = name.split(",");
            String[] descriptions = description.split(",");
            Date createDate = new Date();
            logger.info("Name: " + names[0]+" "+filePath);
            logger.info("description: " + descriptions[0]);
            logger.info("price: " + price);
            try {
                File dir = new File(uploadDirectory);
                if (!dir.exists()) {
                    logger.info("Folder Created");
                    dir.mkdirs();
                }
                // Save the file locally
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
                stream.write(file.getBytes());
                stream.close();
            } catch (Exception e) {
                logger.info("in catch");
                e.printStackTrace();
            }
            byte[] imageData = file.getBytes();
            Product product = new Product();
            product.setName(names[0]);
            product.setImage(imageData);
            product.setPrice(price);
            product.setDescription(descriptions[0]);
            product.setCreatedDate(createDate);
            productService.save(product);
            logger.info("HttpStatus===" + new ResponseEntity<>(HttpStatus.OK));
            return new ResponseEntity<>("Product Saved With File - " + fileName, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Exception: " + e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/product/display/{id}")
    @ResponseBody
    void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<Product> product)
            throws ServletException, IOException {
        logger.info("Id :: " + id);
        product = productService.getProductById(id);
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(product.get().getImage());
        response.getOutputStream().close();
    }

    @GetMapping("/product/productdetails")
    String showProductDetails(@RequestParam("id") Long id, Optional<Product> product, Model model) {
        try {
            logger.info("Id :: " + id);
            if (id != 0) {
                product = productService.getProductById(id);

                logger.info("products :: " + product);
                if (product.isPresent()) {
                    model.addAttribute("id", product.get().getId());
                    model.addAttribute("description", product.get().getDescription());
                    model.addAttribute("name", product.get().getName());
                    model.addAttribute("price", product.get().getPrice());
                    return "productdetails";
                }
                return "redirect:/home";
            }
            return "redirect:/home";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/home";
        }
    }

    @GetMapping("/product/show")
    String show(Model map) {
        List<Product> products = productService.getAllActiceProducts();
        map.addAttribute("products", products);
        return "products";
    }



}
