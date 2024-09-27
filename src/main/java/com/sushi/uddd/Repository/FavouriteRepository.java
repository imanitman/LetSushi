package com.sushi.uddd.Repository;

import com.sushi.uddd.Domain.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    Favourite findById(long id);
}
