package com.dits.tessrect.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageToTextConvertor {
	
	@GetMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping(value = "/fileupload")
	public @ResponseBody String fileUpload(@RequestParam("file")MultipartFile multipartFile, HttpServletResponse response) throws IOException, TesseractException{
		
		byte[] file = multipartFile.getBytes();
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(file));
		ImageIO.write(img, "jpg", new File("image.jpg"));
		
		//ITesseract instance = new Tesseract();   // JNA Direct Mapping
	    ITesseract instance = new Tesseract1();// JNA Interface Mapping
	    
	    //download tesseract and install it according  to your  os
	    //https://github.com/tesseract-ocr/tesseract/wiki/Downloads
	    
	    instance.setDatapath("C:\\Program Files (x86)\\Tesseract-OCR\\tessdata");
	    instance.setLanguage("eng");
	    
	    String result = instance.doOCR(new File("image.jpg"));
		System.out.println(result);
		
		return result;
	}
}
