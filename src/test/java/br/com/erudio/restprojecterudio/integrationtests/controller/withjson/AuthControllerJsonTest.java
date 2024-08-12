package br.com.erudio.restprojecterudio.integrationtests.controller.withjson;

import br.com.erudio.restprojecterudio.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.restprojecterudio.integrationtests.vo.AccountCredentialsVO;
import br.com.erudio.restprojecterudio.integrationtests.vo.TokenVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import configs.TestConfigs;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerJsonTest extends AbstractIntegrationTest {

    private static TokenVO tokenVO;
    @Test
    @Order(1)
    public void testSignin() throws JsonMappingException, JsonProcessingException {
        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "12345");

         tokenVO = given()
                .basePath("/api/v1/auth/signin")
                    .port(TestConfigs.SERVER_PORT)
                    .contentType(TestConfigs.CONTENT_TYPE_JSON)
                .body(user)
                    .when()
                .post()
                    .then()
                        .statusCode(200)
                            .extract()
                            .body()
                                .as(TokenVO.class);

         assertNotNull(tokenVO.getAccessToken());
         assertNotNull(tokenVO.getRefreshToken());
    }

    @Test
    @Order(2)
    public void testRefresh(){
        AccountCredentialsVO user = new AccountCredentialsVO("leandro", "12345");

        var newTokenVO = given()
                .basePath("/api/v1/auth/refresh")
                .port(TestConfigs.SERVER_PORT)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .pathParam("username", tokenVO.getUsername())
                    .header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + tokenVO.getRefreshToken())
                .when()
                    .put("{username}")
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .as(TokenVO.class);

        assertNotNull(newTokenVO.getAccessToken());
        assertNotNull(newTokenVO.getRefreshToken());
    }
}
