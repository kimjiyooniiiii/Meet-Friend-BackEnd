package com.knucapstone.rudoori.service;

import com.knucapstone.rudoori.model.dto.Board.BoardRequest;
import com.knucapstone.rudoori.model.dto.Board.BoardResponse;
import com.knucapstone.rudoori.model.entity.Posts;
import com.knucapstone.rudoori.model.entity.UserInfo;
import com.knucapstone.rudoori.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;


    @Transactional
    public BoardResponse createBoard(BoardRequest request, UserInfo userinfo) {

//        System.out.println(userinfo.getUserId()); // 요청자의 userId 나옴

        Posts post = Posts.builder()
                .userId(userinfo) // 외래 키 값을 가진 객체
                .writer(userinfo.getNickname()) // 외래 키의 닉네임 값
                .title(request.getTitle())
                .content(request.getContent())
                .likeCount(0)
                .dislikeCount(0)
                .scrap(0)
                .build();


        boardRepository.save(post);

        return BoardResponse.builder()
                .postId(post.getPostId())
                .writer(post.getWriter())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .dislikeCount(post.getDislikeCount())
                .scrap(post.getScrap())
                .createdDt(post.getCreatedDt())
                .build();
    }

    @Transactional
    public BoardResponse getBoard(Long boardId) {
        Posts post = boardRepository.findById(boardId).orElseThrow(NullPointerException::new);
        return BoardResponse
                .builder()
                .postId(post.getPostId())
                .writer(post.getWriter())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .dislikeCount(post.getDislikeCount())
                .scrap(post.getScrap())
                .createdDt(post.getCreatedDt())
                .build();
    }

    @Transactional
    public BoardResponse updateBoard(Long boardId, BoardRequest boardRequest, UserInfo userinfo) throws Exception {

        var post = boardRepository.findById(boardId).orElseThrow(); // db 안에 저장된 값
        // boardRequest는 board를 수정 할 값들이 들어가있음 "title":"타이틀","body":"바디"

        // board의 작성자 userId랑 요청한 userId랑 같은 경우 수정 가능
        if (userinfo.getUserId().equals(post.getUserId().getUserId())) {
            post.setTitle(boardRequest.getTitle());
            post.setContent(boardRequest.getContent());
            // media 추가해야함
            return BoardResponse
                    .builder()
                    .postId(post.getPostId())
                    .writer(post.getWriter())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .likeCount(post.getLikeCount())
                    .dislikeCount(post.getDislikeCount())
                    .scrap(post.getScrap())
                    .createdDt(post.getCreatedDt())
                    .build();
        }

        throw new RuntimeException("자신의 게시글만 수정할 수 있습니다");

    }

    @Transactional
    public boolean deleteBoard(Long boardId, UserInfo userinfo) {
        var post = boardRepository.findById(boardId).orElseThrow(); // db 안에 저장된 값

        // board의 작성자 userId랑 요청한 userId랑 같은 경우 삭제 가능
        if (userinfo.getUserId().equals(post.getUserId().getUserId())) {
            boardRepository.deleteById(post.getPostId());
            return true;
        } else {
            throw new RuntimeException("자신의 게시글만 삭제할 수 있습니다");
        }
    }
}