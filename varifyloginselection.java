package DeepDiveselenium.Guru99Bank;


import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class varifyloginselection {
	WebDriver driver;

// testdata	
	@DataProvider(name = "GuruData")
	public Object[][] testData() {

		Object[][] data = new Object[4][2];

		// 1st row
		data[0][0] ="valid";
		data[0][1] = "valid";
		//2nd row
		data[1][0] = "invalid";
		data[1][1] = "valid";
		//3rd row
		data[2][0] = "valid";
		data[2][1] = "invalid";
		//4th row
		data[3][0] = "invalid";
		data[3][1] = "invalid";
		return data;
	}
	
	//reach the website
	@BeforeTest
	
	public void setup() {
		driver=new FirefoxDriver();
		driver.manage().window().maximize();
		driver.navigate().to("https://www.demo.guru99.com/V4/");
		driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
	}
	
	

	
	
	@Test(alwaysRun = true,dataProvider = "GuruData")
	public void VerifyLoginValid(String userid, String passwordid ) {
		WebElement UserID= driver.findElement(By.name("uid"));
		WebElement PasswordID= driver.findElement(By.name("password"));

		UserID.sendKeys(userid);
		PasswordID.sendKeys(passwordid);
		WebElement loginptn= driver.findElement(By.name("btnLogin"));
		loginptn.click();
		
//assert that the dynamic context is displayed
		WebElement MNGRID= driver.findElement(By.cssSelector("tr.heading3 > td:nth-child(1)"));
		String expected = MNGRID.getText();
		
		assertTrue(expected.matches("Manger Id : mngr([0-9]+)"));
	}
	
	// take a screenshot for the dynamic context(manager id:+digits)
	@AfterMethod
	private void TakeScreenShot(ITestResult Result) throws IOException {
		if(ITestResult.SUCCESS ==Result.getStatus())
		{ //create refrence of screenshots 			
			TakesScreenshot ts= (TakesScreenshot)driver;
			
			File source=ts.getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(source, new File("./ScreenShots/"+Result.getName()+".png"));
		}
		
		

	}
 
	//close the driver
	
	@AfterTest
	public void closedriver(){
		driver.quit();
		
		
	}
	

}
