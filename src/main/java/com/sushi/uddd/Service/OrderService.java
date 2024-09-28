package com.sushi.uddd.Service;

import com.sushi.uddd.Domain.Cart;
import com.sushi.uddd.Domain.Line;
import com.sushi.uddd.Domain.Order;
import com.sushi.uddd.Domain.Response.ResLineDto;
import com.sushi.uddd.Domain.Response.ResOrderAdmin;
import com.sushi.uddd.Domain.User;
import com.sushi.uddd.Repository.OrderRepository;
import com.sushi.uddd.Util.SecurityUtil;
import com.sushi.uddd.Util.constant.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final LineService lineService;

    public Order saveOrderInDb(Order order){
        return this.orderRepository.save(order);
    }
    public ResOrderAdmin fetchAllOrder(int page, int size){
        String email = SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : null;
        User user = userService.fetchUserByEmail(email);
        ResOrderAdmin resOrderAdmin = new ResOrderAdmin();
        List<ResLineDto> resLineDtoList = new ArrayList<>();
        List<Cart> carts = user.getCarts();
        if (carts != null){
            for (Cart cart : carts){
                if (cart.getStatus() == StatusEnum.INACTIVE) {
                    List<Line> lines = cart.getLines();
                    if (lines != null){
                        for (Line line : lines){
                            ResLineDto resLineDto =lineService.convertToLineDto(line);
                            resLineDtoList.add(resLineDto);
                        }
                    }
                }
            }
            resOrderAdmin.setLines(resLineDtoList);
            return resOrderAdmin;
        }
        return resOrderAdmin;
    }

}
