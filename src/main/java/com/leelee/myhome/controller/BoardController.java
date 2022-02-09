package com.leelee.myhome.controller;

import com.leelee.myhome.model.Board;
import com.leelee.myhome.repository.BoardRepository;
import com.leelee.myhome.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
//    제목 클릭시 수정페이지로. 수정을 하려면 '특정'게시물이어야함. 수정하고자하는 게시글의 id 파라미터를 전달받아야함
//    RequestParam으로 전달을 받음. required 속성=필수인지 아닌지! (새 글 작성시 필수X so false!) 필요한 경우에만 이 id값을 쓰겠다~!
    public String form(Model model, @RequestParam(required = false) Long id) {
        if(id == null){
            model.addAttribute("board", new Board());
        } else {
//                                          id가 없거나 잘못된값일때▼
            Board board = boardRepository.findById(id).orElse( null);
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    // @ModelAttribute -> @Valid : 검증작업위해서
    public String greetingSubmit(@Valid Board board, BindingResult bindingResult) {
                                                // model board에서 선언한 @Size(조건)에 부합여부
        boardValidator.validate(board, bindingResult);
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        boardRepository.save(board);
        return "redirect:/board/list";
    }
    // 위 save메소드가 board의 키값을 보는데 전달받은 게 X
    // 그렇기때문에 새로운값으로 db에 save되는거임
    // 수정하고 싶은 글의 id를 지정해줘야함 ▼▼▼ form.html
    // <input type="hidden" th:field="*{id}">

}
