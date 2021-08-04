package com.udacity.jwdnd.c1.review;

import com.udacity.jwdnd.c1.review.model.ChatMessage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReviewApplicationTests {
	@LocalServerPort
	private Integer port;

	private static WebDriver driver;
	public String baseUrl;

	@BeforeAll
	public void beforeAll(){
		System.setProperty("webdriver.edge.driver", "C:\\Users\\KC135\\Downloads\\edgedriver_win64\\msedgedriver.exe");
		WebDriver driver = new EdgeDriver();
	}

	@BeforeEach
	public void beforeEach(){
		baseUrl = "http://localhost:" + port;
	}

	@AfterAll
	public void afterAll(){
		driver.quit();
		driver = null;
	}

	@Test
	//done in one test method to replicate sign in, login and chat(send message) in one operation in order
	public void testSignupLoginSubmitMessage(){
		String username = "kevcar";
		String password = "123";
		String messageText = "Howdy!";

		driver.get(baseUrl + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Kev", "Carmichael", username, password);

		driver.get(baseUrl + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		ChatPage chatPage = new ChatPage(driver);
		chatPage.sendChatMessage(messageText);

		ChatMessage sentMessage = chatPage.getFirstMessage();

		assertEquals(username, sentMessage.getUsername());
		assertEquals(messageText, sentMessage.getMessage());
	}

}
