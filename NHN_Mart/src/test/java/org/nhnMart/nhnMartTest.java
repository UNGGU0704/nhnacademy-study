package org.nhnmart;

import java.io.ByteArrayInputStream;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class nhnMartTest {

    @Test
    @DisplayName("전체 기능 작동 테스트 ")
    public void martCreateTest() {
        System.setIn(new ByteArrayInputStream("양파 2 계란 2 파 4".getBytes()));
        NhnMart.main(null);
    }


    @Test
    @DisplayName("제품의 수량 미입력 테스트 ")
    public void TokenizerTest() {
        System.setIn(new ByteArrayInputStream("양파 2 계란 ".getBytes()));

        Assertions.assertThrows(NoSuchElementException.class,
                () ->NhnMart.main(null)); //
    }
}


