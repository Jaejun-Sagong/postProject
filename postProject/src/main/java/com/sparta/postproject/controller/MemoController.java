package com.sparta.postproject.controller;

import com.sparta.postproject.domain.Memo;
import com.sparta.postproject.domain.MemoRepository;
import com.sparta.postproject.domain.MemoRequestDto;
import com.sparta.postproject.domain.PwDto;
import com.sparta.postproject.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController // MemoController도 어딘가에서 쓰일 때 new MemoController 이렇게 해서 생성이 되고 사용되어야 하는데 이 어노테이션으로 그 작업을 생략하게 해줌
public class MemoController {  //생성 조회 변경 삭제가 필요한데 업데이트 -> service , 나머지 ->Repo가 필요함

    private final MemoRepository memoRepository;  // 필수적인 요소이기 때문에 final 선언
    private final MemoService memoService;

    @PostMapping("/api/memos")   //생성은 해당 주소로 post방식으로 들어올것고 그렇게 들어오면 아래를 실행한다.
    public Memo creatMemo(@RequestBody MemoRequestDto requestDto){   //메모를 생성하려면 데이터를 물고다닐 Dto가 필요하다.  // 날아오는 녀석을 그대로 requestDto에 넣어주기 위해서 해당 어노테이션을 씀
        Memo memo = new Memo(requestDto);     //Memo에 선언된 오버로딩 생성자로 인해 생성된 memo에 requestDto 내용이 들어감.
        return memoRepository.save(memo); //True가 반환되나??
    }
    @GetMapping("/api/memos")
    public List<Memo> readMemo(){
        return memoRepository.findAllByOrderByModifiedAtDesc();
    }

    @GetMapping("/api/memos/{id}")
    public Memo showMemo(@PathVariable Long id){
        return memoRepository.findAllById(id);
    }

    @PostMapping("/api/memos/{id}")
    public boolean checkPw(@PathVariable Long id,@RequestBody PwDto pwDto){
        return memoService.checkPw(id, pwDto);
    }

    @PutMapping("/api/memos/{id}")
    public boolean updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){   //RequestBody어노테이션을 써줘야만 Request 안에 Body를 requestDto에 넣어줘야하구나 를 Spring이 안다
        return memoService.update(id, requestDto);
    }

    @DeleteMapping("/api/memos/{id}")
    public boolean deleteMemo(@PathVariable long id, @RequestBody PwDto pwDto){    //어노테이션 Long id가 없으면 경로에 담긴 {id}를 아래id에서 인식을 못한다. //Pathvariable(경로에 있는 변수)
        if(memoService.checkPw(id, pwDto)) {
            memoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
