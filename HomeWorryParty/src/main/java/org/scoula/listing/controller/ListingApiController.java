package org.scoula.listing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.listing.domain.ListingVO;
import org.scoula.listing.service.ListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/listing")
@RequiredArgsConstructor
@Slf4j
public class ListingApiController {

    //@RequiredArgsConstructor 어노테이션이 생성자를 만들어주고
    //final로 선언된 변수에자동주입됨.
    private final ListingService service;

    /**
     *
     * 문제 4번 : 게시판 전체 목록을 검색해서
     *            json array로 전송할 수 있는
     *            컨트롤러 메서드 코드 구현
     */
    @GetMapping("")
    public List<ListingVO> getList() {
        return service.getList();
    }


    /**
     *
     * 문제 5번 : 주소에 포함된 no파라메터 값을 추출하여
     *           no의 값을 조건으로 db로 부터 검색해온
     *           게시판 상세정보를 json로 전송할 수 있는
     *           컨트롤러 메서드 코드 구현
     */

    @GetMapping("/{id}")
//    public ResponseEntity<BoardVO> get(@PathVariable Long no) {
    public ResponseEntity<ListingVO> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }
}
