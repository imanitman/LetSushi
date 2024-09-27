package com.sushi.uddd.Controller;

import com.sushi.uddd.Domain.*;
import com.sushi.uddd.Domain.Request.ReqAddFood;
import com.sushi.uddd.Domain.Request.ReqDeleteFood;
import com.sushi.uddd.Domain.Response.ResAddFood;
import com.sushi.uddd.Domain.Response.ResCartDto;
import com.sushi.uddd.Service.*;
import com.sushi.uddd.Util.SecurityUtil;
import com.sushi.uddd.Util.constant.StatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final FoodService foodService;
    private final UserService userService;
    private final LineService lineService;
    private final StockService stockService;

    @PostMapping("/cart")
    public ResponseEntity<ResAddFood> addToCart(@RequestBody ReqAddFood reqAddFood) {
        if (reqAddFood.getIdUser() == 0 || reqAddFood.getIdFood() == 0 || reqAddFood.getQuantity() == 0) {
            ResAddFood resAddFood = new ResAddFood();
            resAddFood.setMessage("Invalid input");
            resAddFood.setIsSuccess(false);
            return ResponseEntity.badRequest().body(resAddFood);
        }

        User user = userService.fetchUserById(reqAddFood.getIdUser());
        Food food = foodService.fetchFoodById(reqAddFood.getIdFood());
        Stock stock = food.getStock();

        if (reqAddFood.getQuantity() > stock.getQuantity()) {
            int gap = reqAddFood.getQuantity() - stock.getQuantity();
            ResAddFood resAddFood = new ResAddFood();
            resAddFood.setMessage("Cannot add " + food.getName() + ": exceeds stock by " + gap);
            resAddFood.setIsSuccess(false);
            return ResponseEntity.ok(resAddFood);
        }

        Cart activeCart = user.getCarts().stream()
                .filter(cart -> cart.getStatus() == StatusEnum.ACTIVE)
                .findFirst()
                .orElseGet(() -> createNewCartForUser(user));

        Line line = addFoodToCartLine(activeCart, food, reqAddFood.getQuantity());

        cartService.saveCartInDB(activeCart);
        lineService.saveLineInDb(line);
        ResAddFood resAddFood = new ResAddFood();
        resAddFood.setIdLine(line.getId());
        resAddFood.setIsSuccess(true);
        resAddFood.setMessage("Product added to cart successfully");
        return ResponseEntity.ok(resAddFood);
    }

    private Cart createNewCartForUser(User user) {
        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setStatus(StatusEnum.ACTIVE);
        newCart.setTotal_price(0);
        return newCart;
    }

    private Line addFoodToCartLine(Cart cart, Food food, int quantity) {
        List<Line> lines = cart.getLines() != null ? cart.getLines() : new ArrayList<>();
        Line existingLine = lines.stream()
                .filter(line -> line.getFood().getId() == food.getId())
                .findFirst()
                .orElse(null);

        if (existingLine != null) {
            existingLine.setQuantity(existingLine.getQuantity() + quantity);
            existingLine.setPrice(existingLine.getPrice() + food.getPrice() * quantity);
        } else {
            Line newLine = new Line();
            newLine.setFood(food);
            newLine.setQuantity(quantity);
            newLine.setPrice(food.getPrice() * quantity);
            newLine.setCart(cart);
            lines.add(newLine);
        }

        cart.setLines(lines);
        cart.setTotal_price(lines.stream().mapToLong(Line::getPrice).sum());

        return existingLine != null ? existingLine : lines.get(lines.size() - 1);
    }

    @DeleteMapping("/cart")
    public ResponseEntity<String> deleteFromCart(@RequestBody ReqDeleteFood reqDeleteFood) {
        User user = userService.fetchUserById(reqDeleteFood.getIdUser());
        List<Cart> carts = user.getCarts();

        for (Cart cart : carts) {
            if (cart.getStatus() != StatusEnum.INACTIVE) {
                Iterator<Line> iterator = cart.getLines().iterator();
                while (iterator.hasNext()) {
                    Line line = iterator.next();
                    if (line.getId() == reqDeleteFood.getIdLine()) {
                        iterator.remove();
                        cart.setTotal_price(cart.getLines().stream().mapToLong(Line::getPrice).sum());
                        // Save the updated cart back to the database
                        cartService.saveCartInDB(cart);
                        return ResponseEntity.ok("Successfully removed from cart");
                    }
                }
            }
        }
        return ResponseEntity.ok("Line not found in cart");
    }

    @GetMapping("/cart")
    public ResponseEntity<ResCartDto> getCart(@RequestParam(value = "page", defaultValue = "0") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size,
                                              @RequestParam("id") long idUser) {
        return ResponseEntity.ok(cartService.fetchAllCurrentCart(page, size, idUser));
    }
}
