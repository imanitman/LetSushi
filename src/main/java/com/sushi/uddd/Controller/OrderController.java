package com.sushi.uddd.Controller;

import com.sushi.uddd.Domain.Cart;
import com.sushi.uddd.Domain.Order;
import com.sushi.uddd.Domain.Response.ResOrderAdmin;
import com.sushi.uddd.Domain.Response.ResOrderDto;
import com.sushi.uddd.Domain.User;
import com.sushi.uddd.Service.CartService;
import com.sushi.uddd.Service.OrderService;
import com.sushi.uddd.Service.UserService;
import com.sushi.uddd.Util.constant.OrderEnum;
import com.sushi.uddd.Util.constant.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    @PostMapping("/order")
    public ResponseEntity<ResOrderDto> orderPage(@RequestParam("id") long idUser){
        //
        User user = this.userService.fetchUserById(idUser);
        Order  currentOrder = new Order();
        ResOrderDto resOrderDto = new ResOrderDto();
        // find the cart is ordered
        List<Cart> carts = user.getCarts();
        if (carts != null){
            for (Cart cart : carts){
                if (cart.getStatus() == StatusEnum.ACTIVE){
                    cart.setStatus(StatusEnum.INACTIVE);
                    if (!cartService.isEnoughFood(cart).isEnough()){
                        resOrderDto.setMessage(cartService.isEnoughFood(cart).getMessage());
                        resOrderDto.setIsSuccess(false);
                        return ResponseEntity.ok().body(resOrderDto);
                    }
                    currentOrder.setCart(cart);
                    currentOrder.setUser(user);
                    currentOrder.setStatus(OrderEnum.PENDING);
                    currentOrder.setTotal_payment(cart.getTotal_price());
                    orderService.saveOrderInDb(currentOrder);
                    cart.setOrder(currentOrder);
                    cartService.saveCartInDB(cart);
                }
            }
            resOrderDto.setIsSuccess(true);
            resOrderDto.setMessage("Thanh toán thành công");
            return ResponseEntity.ok().body(resOrderDto);
        }
        return ResponseEntity.badRequest().body(null);
    }
    @GetMapping("/orders")
    public ResponseEntity<ResOrderAdmin> pageListOrder(@RequestParam(value = "page", defaultValue = "0") int page,
                                                       @RequestParam(value = "size", defaultValue = "10") int size){
        return ResponseEntity.ok().body(this.orderService.fetchAllOrder(page,size));
    }
}
