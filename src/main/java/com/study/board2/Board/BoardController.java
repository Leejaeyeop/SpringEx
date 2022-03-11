package com.study.board2.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write") //localhost:8080/board/write
    public String boardWriteFrom()
    {

        return "boardwrite";//html 파일명
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception //entity에서 받아옴
    {

        boardService.write(board,file);

        model.addAttribute("message","글 작성 완료");

        //model.addAttribute("message","실패");

        model.addAttribute("searchUrl","/board/list"); //페이지 이동

        return "message";

    }

    @GetMapping("/board/list")
    public String boardList(Model model,
                            @PageableDefault(page = 0,size=10,sort="id",direction = Sort.Direction.DESC) Pageable pageable,
                            String keyword) {
        Page<Board> list = null;

        if(keyword !=null) list = boardService.boardSearchList(keyword,pageable);
        else list = boardService.boardList(pageable);

        int nowPage=list.getPageable().getPageNumber()+1;
        int startPage= Math.max(nowPage-4,1);
        int endPage = Math.min(nowPage+5, list.getTotalPages());

        model.addAttribute("list",list);//"list" 라는 이름으로 넘긴다.
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "board/boardlist";
    }

    @GetMapping("/board/view") //localhost:8080/board/view?id=1
    public String boardView(Model model,Integer id)
    {
        model.addAttribute("board",boardService.boardView(id));
        return "board/boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id)
    {
        boardService.boardDelete(id);
        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model) //id 인식후 integer id로 변환
    {
        model.addAttribute("board",boardService.boardView(id));

        return "board/boardmodify";
    }

    @PostMapping("/board/update/{id}") //게시판 수정
    public String boardUpdate (@PathVariable("id") Integer id,Board board,Model model,MultipartFile file) throws Exception
    {
        Board boardTemp = boardService.boardView(id); //기존의 글
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());
        boardTemp.setFilepath(board.getFilepath());
        boardTemp.setFilepath(board.getFilename());

        boardService.write(boardTemp,file);
        model.addAttribute("message","글 수정 완료");
        model.addAttribute("searchUrl","/board/list");
        return "message";
    }
}
