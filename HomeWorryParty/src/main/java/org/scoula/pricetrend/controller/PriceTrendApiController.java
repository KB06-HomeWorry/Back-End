package org.scoula.pricetrend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.scoula.pricetrend.domain.PriceTrendVO;
import org.scoula.pricetrend.service.PriceTrendService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pricetrend")
@RequiredArgsConstructor
@Slf4j
public class PriceTrendApiController {

    //@RequiredArgsConstructor 어노테이션이 생성자를 만들어주고
    //final로 선언된 변수에자동주입됨.
    private final PriceTrendService service;

    /**
     *
     * 문제 4번 : 게시판 전체 목록을 검색해서
     *            json array로 전송할 수 있는
     *            컨트롤러 메서드 코드 구현
     */
    @GetMapping("")
    public List<PriceTrendVO> getList() {
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
    public ResponseEntity<PriceTrendVO> get(@PathVariable int id) {
        return ResponseEntity.ok(service.get(id));
    }
}
