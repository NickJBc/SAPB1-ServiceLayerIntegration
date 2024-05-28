package com.example.sapbusinessone.controller;

import com.example.sapbusinessone.service.BusinessOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sap")
public class BusinessOneController {

    @Autowired
    private BusinessOneService businessOneService;

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("UserName");
        String password = credentials.get("Password");
        String companyDB = credentials.get("CompanyDB");
        return businessOneService.login(username, password, companyDB);
    }

    @GetMapping("/test")
    public String testConnection() {
        return businessOneService.testConnection();
    }

    @GetMapping("/orders")
    public String getOrders(@RequestParam(required = false) String filter) {
        return businessOneService.getOrders(filter);
    }

    @GetMapping("/udo")
    public String getAllUserDefinedObjects() {
        return businessOneService.getAllUserDefinedObjects();
    }

    @GetMapping("/udo/{id}")
    public String getUserDefinedObjectById(@PathVariable String id) {
        return businessOneService.getUserDefinedObjectById(id);
    }

    @GetMapping("/users")
    public List<String> getUsers(@RequestParam(required = false) String filter) {
        return businessOneService.getUsers(filter);
    }

    @GetMapping("/users/selected")
    public List<String> getUsersSelectedFields(@RequestParam(required = false) String select) {
        String filter = null;
        if (select != null && !select.isEmpty()) {
            filter = "$select=" + select;
        }
        return businessOneService.getUsers(filter);
    }
}
