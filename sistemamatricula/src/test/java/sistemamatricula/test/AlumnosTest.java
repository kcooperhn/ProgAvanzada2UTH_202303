package sistemamatricula.test;

import static java.time.Duration.ofSeconds;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

import java.io.File;
import java.io.IOException;
import java.util.Random;


public class AlumnosTest {
	
	private static String basePath = "C:\\projects\\testing\\sistemamatricula\\";
	
	@Test
	public void guardarTest() throws IOException {
		WebDriver driver = new ChromeDriver();
		try {
		
			driver.get("http://localhost:8080/alumnos");
			
			 new WebDriverWait(driver, ofSeconds(30), ofSeconds(1))
	         .until(titleIs("Alumnos"));
						
			WebElement camponombre = driver.findElement(By.xpath("//vaadin-text-field[@id='txtnombre']/input"));
			WebElement campotelefono = driver.findElement(By.xpath("//vaadin-text-field[@id='txttelefono']/input"));
			WebElement campoemail = driver.findElement(By.xpath("//vaadin-email-field[@id='txtemail']/input"));
			WebElement campoapellido = driver.findElement(By.xpath("//vaadin-text-field[@id='txtapellido']/input"));
			WebElement campogenero = driver.findElement(By.xpath("//vaadin-combo-box[@id='cbgenero']/input"));
			WebElement campocuenta = driver.findElement(By.xpath("//vaadin-text-field[@id='txtnumerocuenta']/input"));
			WebElement campocampus = driver.findElement(By.xpath("//vaadin-combo-box[@id='cbcampus']/input"));
			Random rd = new Random();
			campocuenta.click();
			int cuentaAleatorio = rd.nextInt() * 200000;
			cuentaAleatorio = Math.abs(cuentaAleatorio);
			campocuenta.sendKeys(cuentaAleatorio+"6789");
			
			camponombre.click();
			camponombre.sendKeys("Sofia Sarai");
			
			campoapellido.click();
			campoapellido.sendKeys("Perez Raudales");
			
			campoemail.click();
			campoemail.sendKeys("sofiasarai@gmail.com");
			
			campotelefono.click();
			
			int telAleatorio = rd.nextInt() * 900000;
			telAleatorio = Math.abs(telAleatorio);
			campotelefono.sendKeys(telAleatorio+"");
			
			campogenero.click();
			//SELECCIONAMOS EL PRIMER ELEMENTO DEL COMBO DE GENERO (ELEMENTO SUB 0)
			WebElement generoSeleccionar = driver.findElement(By.xpath("//vaadin-combo-box-item[@id='vaadin-combo-box-item-0']"));
			generoSeleccionar.click();
			
			campocampus.click();
			WebElement campusSeleccionar = driver.findElement(By.xpath("//vaadin-combo-box-item[3]"));
			campusSeleccionar.click();
			tomarCapturaPantalla(driver, "prueba1-1.png");
			
			WebElement botonGuardar = driver.findElement(By.xpath("//vaadin-button[@id='btnguardaralumno']"));
			botonGuardar.click();
			
			tomarCapturaPantalla(driver, "prueba1-2.png");
			
			WebElement botonCancelar = driver.findElement(By.xpath("//vaadin-button[@id='btncancelar']"));
			WebElement botonEliminar = driver.findElement(By.xpath("//vaadin-button[@id='btndelete']"));

		} finally {
	      //Ends the browser session
	      driver.quit();
	  }
	}
	
	public void tomarCapturaPantalla(WebDriver driver, String filePath) throws IOException {
		//CONVERTIR EL OBJETO WEBDRIVER EN UN OBJETO TAKESCREENSHOT
		TakesScreenshot screen = ((TakesScreenshot)driver);
		
		//LLAMAR AL METODO GETSCREENSHOTAS PARA CREAR LA IMAGEN
		File file = screen.getScreenshotAs(OutputType.FILE);
		
		//MOVER ESE ARCHIVO DE IMAGEN A LA CARPETA DONDE LO NECESITO
		File destFile = new File(basePath+filePath);
		
		//COPIAR EL ARCHIVO EN EL DESTINO
		FileUtils.copyFile(file, destFile);
	}
}
