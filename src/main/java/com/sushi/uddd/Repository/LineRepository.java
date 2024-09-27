package com.sushi.uddd.Repository;

import com.sushi.uddd.Domain.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineRepository extends JpaRepository<Line, Long> {
    Line save(Line line);

}
