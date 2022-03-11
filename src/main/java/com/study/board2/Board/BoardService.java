package com.study.board2.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    //글작성 처리
    public void write(Board board, MultipartFile file) throws Exception{

        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files"; //저장할 경로

        UUID uuid = UUID.randomUUID(); //식별자 파일이름을 붙임 랜덤
        String fileName = uuid+ "_" + file.getOriginalFilename(); //파일 이름
        File saveFile = new File(projectPath,fileName);
        file.transferTo(saveFile);

        board.setFilename(fileName); //파일 이름
        board.setFilepath("/files/" + fileName); //파일 이름 경로

        boardRepository.save(board);
    }

    //게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){

        return boardRepository.findAll(pageable);
    }
     public  Page<Board> boardSearchList(String keyword,Pageable pageable)
     {
         return boardRepository.findByTitleContaining(keyword,pageable);
     }

    //게시글 불러오기
    public Board boardView(Integer id)
    {
        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id)
    {
        boardRepository.deleteById(id);
    }
}
