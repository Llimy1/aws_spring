package com.example.aws_spring.web;

import com.example.aws_spring.config.auth.LoginMember;
import com.example.aws_spring.config.auth.dto.SessionMember;
import com.example.aws_spring.service.posts.PostsService;
import com.example.aws_spring.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model, @LoginMember SessionMember member) {
        model.addAttribute("posts", postsService.findAllDesc());

        if (member != null) {

            model.addAttribute("memberName", member.getName());

        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }
}
