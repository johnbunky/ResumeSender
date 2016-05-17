import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by johnbunky on 15.05.16.
 */
public class ResumeSender{

public static void main (String[] argc){

        String[] AddressArray = new String[150];//init array of the email addresses
        int AddressCounter = 1;//init index of the AddressArray
        //String adr_pattern = "/^[-a-zA-Z0-9!#$%&'*+\/=?^_`{|}~]+(\.[-a-z0-9!#$%&'*+\/=?^_`{|}~]+)*@[a-z0-9-]+\.[a-z]{2,6}/";
        //open Firefox
        WebDriver driver = new FirefoxDriver();
        driver.manage().window();
        //open email
        driver.get("http://mail.ru");
        //input credetails
        driver.findElement(By.name("Login")).sendKeys("johnbunky");
        driver.findElement(By.name("Password")).sendKeys("*********");
        driver.findElement(By.id("mailbox__auth__button")).click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //loop opens pages with a list of the email addresses
        for (int i = 1; i > 0; i--) {
            String PageAddress = "https://e.mail.ru/messages/sent/?page=" + i;
            driver.get(PageAddress);
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            //loop pushes email addresses to the AddressArray
            for ( int j = 1; j <26; j++) {
                String ElementXPath = "(.//*[child::*[*[text()[contains(.,'СV  test engineer')]]]]/div[2])[" + j + "]";
                //check condition if the addresses less than 25 on the page
                try {
                       AddressArray[AddressCounter] = driver.findElement(By.xpath(ElementXPath)).getText();
                       System.out.println(AddressArray[AddressCounter]);
                       AddressCounter++;
                       driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
                }
                catch (NoSuchElementException e) {
                    break;
                }
            }
        }
        //loop sends the messages to the addresses from AddressArray
        for (int z = 1; z <= AddressCounter; z++) {
            driver.get("https://e.mail.ru/compose/14633807830000000116/drafts/");
            System.out.println(AddressArray[z]);
            // validation of the addresses with CheckWithRegExp function
            //if(CheckWitnRegExp(AddressArray[z])) {
            driver.findElement(By.xpath(".//*[@id='compose__header__content']/div[2]/div[2]/div[1]/textarea[2]")).sendKeys(AddressArray[z]);
            driver.findElement(By.xpath(".//*[@id='b-toolbar__right']/div[1]/div/div[2]/div[1]/div/span")).click();
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
           // }
            //else
             //   continue;

        }
    }

 /*   public static boolean CheckWitnRegExp (String Address){
        Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$");
        Matcher m = p.matcher(Address);
        return m.matches();

    }                       */
}