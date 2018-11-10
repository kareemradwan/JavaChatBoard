package com.blogspot.noteoneverything.chatboard.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.context.SecurityContextHolder;
import java.security.Principal;
import com.blogspot.noteoneverything.chatboard.dao.UserRepository;
import com.blogspot.noteoneverything.chatboard.service.BoardService;
import com.blogspot.noteoneverything.chatboard.dao.UserImageRepository;
import com.blogspot.noteoneverything.chatboard.model.User;
import com.blogspot.noteoneverything.chatboard.model.UserImage;
import com.blogspot.noteoneverything.chatboard.model.Board;
import com.blogspot.noteoneverything.chatboard.model.BoardResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Controller
public class ChatBoardController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserImageRepository userImageRepository;
    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByName(principal.getUsername());
        model.addAttribute("user", user);
        List<Board> boards = boardService.findBoardsByUser(user, PageRequest.of(0, 5));
        model.addAttribute("boards", boards);
        return "boards/index";
    }

    @GetMapping(value = "/board")
    public String boards(@RequestParam("b_id") String b_id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByName(principal.getUsername());
        model.addAttribute("user", user);
        Board board = boardService.findBoardById(Long.parseLong(b_id, 10));
        model.addAttribute("board", board);
        List<BoardResponse> boardReponses = board.getBoardResponses();
        model.addAttribute("boardReponses", boardReponses);
        return "boards/board";
    }

    @PostMapping(value = "/delete")
    public String deleteCat(@RequestParam("name") String name, @RequestParam("id") Long id) {
        // catservice.deleteCat(name, id);
        // System.out.println("Cat named = " + name + "was removed from our database.
        // Hopefully he or she was adopted.");
        return "redirect:/";

    }

    @PostMapping(value = "/genkey")
    public String genkey(@RequestParam("name") String name,
            @RequestParam("rescued") @DateTimeFormat(pattern = "yyyy/MM/dd") Date rescued,
            @RequestParam("vaccinated") Boolean vaccinated, Model model) {
        // catservice.getGeneratedKey(name, rescued, vaccinated);
        // System.out.println("name = " + name + ",rescued = " + rescued + ", vaccinated
        // = " + vaccinated);
        return "redirect:/";
    }
}