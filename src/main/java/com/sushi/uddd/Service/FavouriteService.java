package com.sushi.uddd.Service;

import com.sushi.uddd.Repository.FavouriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavouriteService {
    FavouriteRepository favouriteRepository;

}
