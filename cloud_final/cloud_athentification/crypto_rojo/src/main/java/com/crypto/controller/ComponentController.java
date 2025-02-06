package com.crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.crypto.model.Component;
import com.crypto.repository.ComponentRepository;

import java.util.List;

@Controller
public class ComponentController {

    private final ComponentRepository componentRepo ;

    @Autowired
    public ComponentController(ComponentRepository componentRepo) {
        this.componentRepo = componentRepo;
    }

    @GetMapping("/components")
    public String listProduits(Model model) {
        List<Component> components= componentRepo.findAll();
        model.addAttribute("components", components);
        return "ListComponents"; // Retourne la vue HTML "components.jsp"
    }

    @PostMapping("/addComponent")
    public String addComponent(@RequestParam("nom") String nom, @RequestParam("prix") Double prix) {
        Component component = new Component();
        component.setNom(nom);
        component.setPrix(prix);
        componentRepo.insertComponent(component);
        return "redirect:/components"; // Rediriger vers la page de la liste après ajout
    }
}
