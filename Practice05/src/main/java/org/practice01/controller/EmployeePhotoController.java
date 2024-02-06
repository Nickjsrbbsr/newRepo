package org.practice01.controller;

import org.practice01.entity.Photo;
import org.practice01.service.EmployeeService;
import org.practice01.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
@Controller
public class EmployeePhotoController {

@Autowired
    PhotoService photoService;
@Autowired
    EmployeeService employeeService;


//    @RequestMapping("/feb")
//    public String showUploadForm() {
//        return "index";
//    }

//    @GetMapping("/getAllBooksByLid/{lid}")
//    public   String findBooksByLibraryId(@RequestParam("lid")Integer lid, RedirectAttributes redirectAttributes){
////
//        List<Photo> fetchedbooksList;
//        try {
//
//            fetchedbooksList = photoService.findPhotosByEmployeeId(lid);
//            redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("message", "Error uploading the file!");
//        }
//        return "redirect:/feb";
//    }


    @RequestMapping("/")
    public String showUploadForm2() {
        return "employee";
    }
    @PostMapping("" +
            "")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, @RequestParam("eid")  Integer eid, @RequestParam("ename") String ename, @RequestParam("date") LocalDate date) throws IOException {

        try {
            employeeService.saveData(file,eid,ename,date);
            redirectAttributes.addFlashAttribute("message", "File uploaded successfully!");
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "Error uploading the file!");
        }
        return "redirect:/";
    }



    @GetMapping("/fetchPhotos")
    public String fetchPhotos(
            @RequestParam("eid") Integer eid,
            @RequestParam("ename") String ename,
            @RequestParam("fromDate") LocalDate fromDate,
            @RequestParam("toDate") LocalDate toDate,
            @RequestParam(value = "showHistory", required = false, defaultValue = "false") boolean showHistory,
            Model model
    ) {
        if (!showHistory) {
            // Logic to handle when "Show History" checkbox is not checked
            return "redirect:/getData"; // Redirect to the form page or any other appropriate action
        }


        List<Photo> photos = photoService.getPhotosByEmployeeAndDateRange(eid, fromDate, toDate);
        model.addAttribute("photos", photos);
        model.addAttribute("showHistory", showHistory);
        return "photoList"; // Create a new Thymeleaf template (photoList.html) to display the photos
    }

    @GetMapping("/getData")
    public String getData(){
        return "fetchPhotos";
    }

    @GetMapping("/fetch")
    @ResponseBody
    public String hello(@RequestParam("eid") Integer eid ,@RequestParam("name" ) String name,@RequestParam("date") LocalDate date){
        System.out.println(eid);;
        System.out.println();;
//        return "hello   :"+eid+"  "+name+" "+date;
        return  "hello "+eid+" "+name+" "+date;

    }



    @GetMapping("/showPhoto")
    public ResponseEntity<byte[]> showPhoto(@RequestParam("photoId") Integer photoId) {
        try {
            // Retrieve the photo by photoId from the database
            Photo photo = photoService.getPhotoById(photoId);

            // Check if the photo exists
            if (photo != null && photo.getPhoto() != null) {
                // Return the photo bytes and set the content type as image/jpeg
                return ResponseEntity.ok()
                        .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                        .body(photo.getPhoto());
            } else {
                // Photo not found, return a 404 Not Found response
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle exceptions, return a 500 Internal Server Error response
            return ResponseEntity.status(500).build();
        }
    }
}

