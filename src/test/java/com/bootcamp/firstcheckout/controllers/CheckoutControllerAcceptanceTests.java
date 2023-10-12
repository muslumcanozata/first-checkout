package com.bootcamp.firstcheckout.controllers;

import com.bootcamp.firstcheckout.controllers.requests.AddAnItemToCartRequest;
import com.bootcamp.firstcheckout.services.CartService;
import com.bootcamp.firstcheckout.services.CheckoutService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"spring.main.allow-bean-definition-overriding=true", "server.servlet.context-path=/"})
class CheckoutControllerAcceptanceTests {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    //@Test
    void testAddItemToCartSuccess() {
        //given
        AddAnItemToCartRequest request = TestDataCreator.createValidAddAnItemToCartRequest();

        //then
        given()
            .contentType(ContentType.JSON)
            .body(request)
            .when()
            .post("/checkouts/items")
            .then()
            .statusCode(200)
            .body("message", equalTo("Item added to cart successfully"))
            .body("result", equalTo(true));
    }
}