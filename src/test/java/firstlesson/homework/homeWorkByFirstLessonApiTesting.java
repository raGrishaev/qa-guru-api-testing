package firstlesson.homework;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class homeWorkByFirstLessonApiTesting {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    Faker faker = new Faker();

    @Test
    void checkSuccessCreateUser(){
        String name = faker.name().firstName();
        String position = faker.job().position();
        String data = "{\"name\":\"" + name + "\",\"job\":\"" + position + "\"}";

        given()
                .body(data)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                    .post("/users")

                .then()
                    .log().body()
                    .log().status()
                    .statusCode(201)
                    .body("name", is(name))
                    .body("job", is(position))
                    .body("id", notNullValue());
    }

    @Test
    void сreateUserWhithOutName(){
        String data = "{\"name\":null,\"job\":\"QA\"}";

        given()
                .body(data)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                    .post("/users")

                .then()
                    .log().body()
                    .log().status()
                    .statusCode(201)
                    .body("name", is(nullValue()))
                    .body("job", is("QA"));
    }

    @Test
    void successUdateUserName(){
        String name = faker.name().firstName();
        String data = "{\"name\":\"" + name + "\"}";

        given()
                .body(data)
                .contentType(ContentType.JSON)
                .log().uri()

                .when()
                    .put("/users/1")

                .then()
                    .log().body()
                    .log().status()
                    .statusCode(200)
                    .body("name", is(name));
    }

    @Test
    void checkColorFirstUser(){

        given()
                .log().uri()

                .when()
                    .get("/unknown")

                .then()
                    .log().body()
                    .log().status()
                    .statusCode(200)
                    .body("data[0].color", is("#98B2D1"));
    }

    @Test
    void checkTotalUsers(){

        given()
                .log().uri()

                .when()
                    .get("/unknown")

                .then()
                    .log().body()
                    .log().status()
                    .statusCode(200)
                    .body("total", is(12));
    }
}
