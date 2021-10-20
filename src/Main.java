import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
//
//        1. Launch Chrome browser

       System.setProperty("webdriver.chrome.driver", "C:\\Users\\kater\\Desktop\\Automation\\drivers\\chromedriver.exe");
       WebDriver driver=new ChromeDriver();

      driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

//        2. Navigate to http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx

        driver.get("http://secure.smartbearsoftware.com/samples/TestComplete12/WebOrders/Login.aspx");
        driver.manage().window().maximize();

//        3. Login using username Tester and password test

      driver.findElement(By.id("ctl00_MainContent_username")).sendKeys("Tester");
      driver.findElement(By.id("ctl00_MainContent_password")).sendKeys("test");
      driver.findElement(By.id("ctl00_MainContent_login_button")).click();

//         4. Click on Order link

       driver.findElement(By.xpath("//*[@id=\"ctl00_menu\"]/li[3]")).click();

//       5. Enter a random product quantity between 1 and 100

         WebElement quantity= driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtQuantity"));
         quantity.sendKeys(Keys.BACK_SPACE);
         String expectedQuantity=random();
         quantity.sendKeys(expectedQuantity);

//        6. Click on Calculate and verify that the Total value is correct.

        driver.findElement(By.xpath("//input[@type='submit']")).click();

         // Price per unit is 100.

        String pricePerUnitActual=driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtUnitPrice")).getAttribute("value");
        Assert.assertEquals(pricePerUnitActual,"100");

        String discountActual= driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtDiscount")).getAttribute("value");

        if(Integer.parseInt(expectedQuantity)>=10){
            Assert.assertEquals(discountActual,"8");
        }else
         Assert.assertEquals(discountActual,"0");

//        6. Generate and enter random first name and last name.

     WebElement customerName= driver.findElement(By.id("ctl00_MainContent_fmwOrder_txtName"));
     String expectedName=randomName();
     customerName.sendKeys(expectedName);

//        7. Generate and Enter random street address

     WebElement street= driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox2"));
     String expectedStreet=randomStreet();
     street.sendKeys(expectedStreet);

     //        8. Generate and Enter random city

     WebElement city= driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox3"));
     String expectedCity=randomCity();
     city.sendKeys(expectedCity);

     //        9. Generate and Enter random state

     WebElement state= driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox4"));
     String expectedState=randomState();
     state.sendKeys(expectedState);

     //        10. Generate and Enter a random 5 digit zip code

     WebElement zip= driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox5"));
     String expectedZip=randomZipCode();
     zip.sendKeys(expectedZip);

//        11. Select the card type randomly. On each run your script should select a random type.

     List<WebElement> radioButtons = driver.findElements(By.cssSelector("input[type='radio']"));
     int randomNo = (int)(Math.random()*radioButtons.size());
     radioButtons.get(randomNo).click();
     String expectedCardType="";


//        12. Generate and enter the random card number:

     WebElement cardNumber=driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox6"));
     String expectedCardNumber="";
     if(randomNo==0){
         long digits = (long) (Math.random() * 10000000000000000L);
         expectedCardNumber=""+4+digits;
         expectedCardType="Visa";

     }else if(randomNo==1){
         long digits = (long) (Math.random() * 10000000000000000L);
       expectedCardNumber=""+5+digits;
         expectedCardType="MasterCard";
     }else if(randomNo==2){
         long digits = (long) (Math.random() * 1000000000000000L);
         expectedCardNumber=""+3+digits;
         expectedCardType="American Express";
     }
        cardNumber.sendKeys(expectedCardNumber);

//        13. Enter a valid expiration date (newer than the current date)

     WebElement experation=driver.findElement(By.id("ctl00_MainContent_fmwOrder_TextBox1"));
     String expectedExpiration="10/24";
     experation.sendKeys(expectedExpiration);

//        14. Click on Process

     driver.findElement(By.id("ctl00_MainContent_fmwOrder_InsertButton")).click();

//        15. Verify that “New order has been successfully added” message appeared on the page.

     WebElement orderAdded= driver.findElement(By.xpath("//strong"));
     Assert.assertEquals(orderAdded.getText(),"New order has been successfully added.");
     Thread.sleep(1000);

//        16. Click on View All Orders link.

     driver.findElement(By.xpath("//a[text()='View all orders']")).click();

//    17. The placed order details appears on the first row of the orders table. Verify that the entire information contained on the row (Name, Product, Quantity, etc) matches the previously entered information in previous steps.

      WebElement nameVerify=driver.findElement(By.xpath("//table[@id='ctl00_MainContent_orderGrid']//tbody//td[2]"));
      Assert.assertEquals(nameVerify.getText(),expectedName);

      WebElement productVerify=driver.findElement(By.xpath("//table[@id='ctl00_MainContent_orderGrid']//tbody//td[3]"));
      Assert.assertEquals(productVerify.getText(),"MyMoney");

      WebElement quantityVerify=driver.findElement(By.xpath("//table[@id='ctl00_MainContent_orderGrid']//tbody//td[4]"));
      Assert.assertEquals(quantityVerify.getText(),expectedQuantity);

        LocalDate date=LocalDate.now().plusDays(1);
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String expectedDate = date.format(formatters);

        WebElement dateVerify=driver.findElement(By.xpath("//table[@id='ctl00_MainContent_orderGrid']//tbody//td[5]"));
        Assert.assertEquals(dateVerify.getText(),expectedDate);

        WebElement streetVerify=driver.findElement(By.xpath("//table[@id='ctl00_MainContent_orderGrid']//tbody//td[6]"));
        Assert.assertEquals(streetVerify.getText(),expectedStreet);

        WebElement cityVerify=driver.findElement(By.xpath("//table[@id='ctl00_MainContent_orderGrid']//tbody//td[7]"));
        Assert.assertEquals(cityVerify.getText(),expectedCity);

        WebElement stateVerify=driver.findElement(By.xpath("//table[@id='ctl00_MainContent_orderGrid']//tbody//td[8]"));
        Assert.assertEquals(stateVerify.getText(),expectedState);

        WebElement zipVerify=driver.findElement(By.xpath("//table[@id='ctl00_MainContent_orderGrid']//tbody//td[9]"));
        Assert.assertEquals(zipVerify.getText(),expectedZip);


      WebElement cardVerify=driver.findElement(By.xpath("//table[@id='ctl00_MainContent_orderGrid']//tbody//td[10]"));
      Assert.assertEquals(cardVerify.getText(),expectedCardType);

        WebElement cardNumVerify=driver.findElement(By.xpath("//table[@id='ctl00_MainContent_orderGrid']//tbody//td[11]"));
        Assert.assertEquals(cardNumVerify.getText(),expectedCardNumber);

        WebElement experationVerify=driver.findElement(By.xpath("//table[@id='ctl00_MainContent_orderGrid']//tbody//td[12]"));
        Assert.assertEquals(experationVerify.getText(),expectedExpiration);

//        18. Log out of the application.

        driver.findElement(By.id("ctl00_logout")).click();

        driver.quit();


    }
 public static String random(){
  return ""+(1+(int)(Math.random()*101));
 }

 public static String randomName(){
 String names[]={"Courtney Stigell" ,"Cheri Gidley","Thomasina Mellon",
               "Erich Galbraeth", "Stanwood Maffetti", "Cortie Windrass",
                "Valentina Milillo", "Mirella Deeson", "Iseabal Colyer",
                "Jaquith Lanceley"};
 int randomIndex = ((int)(Math.random()*names.length));
 return names[randomIndex];
}
 public static String randomStreet(){
  String street[]={"16 Mallard Street","8451 Onsgard Road","06302 Judy Plaza",
          "76 Hoffman Plaza", "9149 Stang Way","82594 Old Gate Plaza",
          "4 Superior Trail", "91762 Sunfield Alley", "90 Hintze Point","572 Sunnyside Trail"};
  int randomIndex = ((int)(Math.random()*street.length));
  return street[randomIndex];
 }
 public static String randomCity(){
  String city[]={"Yola","Marabahan","Ganshui","Pore", "Quvasoy", "Pabean", "Sanguansi", "Pontinha","Xinshi"};
  int randomIndex = ((int)(Math.random()*city.length));
  return city[randomIndex];
 }
 public static String randomState(){
  String state[]={"VA","MD","DE","FL","NY", "PE", "CA", "AL","AR"};
  int randomIndex = ((int)(Math.random()*state.length));
  return state[randomIndex];
 }
 public static String randomZipCode(){
  String zip[]={"09111","65221","98776","02134","65444", "00213", "31245", "76886","09888"};
  int randomIndex = ((int)(Math.random()*zip.length));
  return zip[randomIndex];
 }
}