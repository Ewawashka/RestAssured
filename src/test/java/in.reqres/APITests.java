package in.reqres;

import data.*;
import io.qameta.allure.Feature;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.ExtractableResponse;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.patch;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static specification.Specification.*;

public class APITests {
    @Feature("���������� ������� 2.1 �� Rest Assured")
    @DisplayName("���� �� ������������ ���� ������ �������� �������������")
    @Test
    public void checkingAvatarNamesForUniqueness() {
        installSpec(requestSpec(), responseSpec200());
        Resource resource = given()
                .when()
                .get("/api/users")
                .then()
                .extract().body().as(Resource.class);

        Set<String> avatarNames = new HashSet<>();
        for (User user : resource.getData()) {
            String avatarName = user.getAvatar().substring(user.getAvatar().lastIndexOf('/') + 1);
            if (avatarNames.contains(avatarName)) {
                Assert.fail("������� ������������� ��� ����� �������: " + avatarName);
            } else {
                avatarNames.add(avatarName);
            }
        }
    }

    @Feature("���������� ������� 2.2 �� Rest Assured")
    @DisplayName("���� �� �������� �����")
    @Test
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#setFulLoginParameters")
    public void successfulLogin(String email, String password) {
        Authorization authorization = new Authorization(email, password);
        installSpec(requestSpec(), responseSpec200());
        UserTokenResponse userTokenResponse = given()
                .body(authorization)
                .when()
                .post("/api/login")
                .then()
                .extract()
                .as(UserTokenResponse.class);
        Assert.assertTrue(!userTokenResponse.getToken().equals(null)&!userTokenResponse.getToken().isEmpty(),"��������� ��� ������������ ����� �� null � �� ������");
    }

    @Feature("���������� ������� 2.2 �� Rest Assured")
    @DisplayName("���� �� ����� � ������� ��-�� �� ��������� ������")
    @Test
    @ParameterizedTest(name = "{displayName}: {arguments}")
    @MethodSource("helpers.DataProvider#setEmailLoginParameters")
    public void failedLoginWithoutPassword(String email) {
        installSpec(requestSpec(), responseSpec400());
        Authorization authorization = new Authorization(email);
        ErrorResponse errorResponse = given()
                .body(authorization)
                .when()
                .post("/api/login")
                .then()
                .extract()
                .as(ErrorResponse.class);
        Assert.assertTrue(errorResponse.getError().equals("Missing password"), "��������� ��������� ������ ��-�� �� ��������� ���� ������");
    }

    @Feature("���������� ������� 2.3 �� Rest Assured")
    @DisplayName("���� ��� �������� �������� LIST <RESOURCE>, ������� ������ ���������� ������ ��������������� �� �����")
    @Test
    public void verifyDataSortedByYear() {
        installSpec(requestSpec(), responseSpec200());
        ColorResource colorResource = given()
                .when()
                .get("/api/unknown")
                .then()
                .extract().body().as(ColorResource.class);
        List<Color> colors = colorResource.getData();

        for (int i = 0; i < colors.size() - 1; i++) {
            int currentYear = colors.get(i).getYear();
            int nextYear = colors.get(i + 1).getYear();
            Assert.assertTrue(currentYear<=nextYear,"��������� ��� ������������ ������ ������������� �� �����");
        }
    }

    @Test
    public void verifyTagCount()  {
        installSpec(requestSpecXml(), responseSpec200());
        String xmlStr = given()
                .when()
                .get()
                .then()
                .extract().response().asString();
        try {
            // �������� ������� ��� �������� �������� �������
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // ������ XML �� ������
            Document document = builder.parse(new InputSource(new StringReader(xmlStr)));

            // ��������� ������ ���� ����� � ���������
            NodeList nodeList = document.getElementsByTagName("*");

            // ������� ���������� �����
            int tagCount = nodeList.getLength();
            Assert.assertEquals(tagCount,15,"���������� ����� ������������� ����������");
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            Assert.fail("�� ����� ��������� xml ��������� ������:\n" +
                    "Exception: " + e.getClass() + "\n" +
                    "Exception message: " + e.getLocalizedMessage() + "\n" +
                    "StackTrace:\n" + Arrays.toString(e.getStackTrace()));
        }
    }
}



