package net.datasa.exam3.repository;

import net.datasa.exam3.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {

    List<BoardEntity> findAllByOrderByCreateDateDesc();

    List<BoardEntity> findByCategoryOrderByCreateDateDesc(String category);

}
