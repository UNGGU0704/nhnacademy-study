package com.nhnacademy.mart;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * nhn Mart is selling Food.
 *
 * @author kIm kyu hyeong
 */
public class NhnMart {

    private final FoodStand foodStand = new FoodStand();
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);

    public void prepareMart() {
        fillFoodStand();
    }

    // 음식 세팅
    private void fillFoodStand() {
        for (int i = 0; i < 2; i++) {
            foodStand.add(new Food("양파", 1_000));
        }
        for (int i = 0; i < 5; i++) {
            foodStand.add(new Food("계란", 5_000));
        }
        for (int i = 0; i < 10; i++) {
            foodStand.add(new Food("파", 500));
        }
        for (int i = 0; i < 20; i++) {
            foodStand.add(new Food("사과", 2_000));
        }
        logger.info("FoodStand 정상 셋팅 완료.");
    }

    public Basket provideBasket() {
        return new Basket();
    }

    public FoodStand getFoodStand() {
        return foodStand;
    }

    public FoodStand getCounter() {
        return foodStand;
    }


}
