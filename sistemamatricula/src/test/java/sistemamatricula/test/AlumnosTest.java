package sistemamatricula.test;

import static java.time.Duration.ofSeconds;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AlumnosTest {

	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		try {
		
			driver.get("http://localhost:8080/alumnos");
			
			 new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
	         .until(titleIs("Alumnos"));
			
			//WebElement campocuenta = driver.findElement(By.xpath("//input[@id='input-vaadin-text-field-22']"));
			WebElement campocuenta = driver.findElement(By.xpath("//vaadin-text-field[@id='txtnumerocuenta']/input"));
			WebElement camponombre = driver.findElement(By.xpath("//vaadin-text-field[@id='txtnombre']/input"));
			
			campocuenta.click();
			campocuenta.sendKeys("2023123456789");
			Thread.sleep(1000);
			camponombre.click();
			camponombre.sendKeys("pedro perez");
			Thread.sleep(1000);
			WebElement boton = driver.findElement(By.xpath("//vaadin-button[@id='btnguardaralumno']"));
			boton.click();
			
			Thread.sleep(5000);

		} finally {
	      //Ends the browser session
	      driver.quit();
	  }
	}
	
	@Test
	public void guardarTest() {
		
	}
}
