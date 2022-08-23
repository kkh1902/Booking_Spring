package com.example.springCrud.domain.repository;

import com.example.springCrud.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repository는 데이터 조작을 담당한다
// interface로 생성하며 JPArepository를 extend한다.
// JPARepository의 값은 매핑할 entity와 id의 타입이다.
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitleContaining(String keyword);
}
