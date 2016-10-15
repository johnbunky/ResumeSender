
import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;


/**
 * Created by johnbunky on 15.05.16.
 */
public class ResumeSender{

public static void main (String[] argc){

        String[] addressArray = new String[150];
        int addressCounter = 1;

        //open email

        open("http://mail.ru");

        //input credetails

        $(By.name("Login")).setValue("johnbunky");
        $(By.name("Password")).setValue("*********");
        $(By.id("mailbox__auth__button")).click();

        //loop opens pages with a list of the email addresses

        for (int i = 8; i > 7; i--) {
            String pageAddress = "https://e.mail.ru/messages/sent/?page=" + i;
            open(pageAddress);

            //loop pushes email addresses to the addressArray

            int addressesQuantity = $$(".b-datalist__item__info").filterBy(text("CV test engineer")).size();

            for (int j = 1; j <= addressesQuantity; j++) {
                String elementXPath = "(.//*[child::*[*[text()[contains ( . , 'CV test engineer' )]]]]/div[2])[" + j + "]";
                addressArray[addressCounter] = $(By.xpath(elementXPath)).getText();
                System.out.println(addressArray[addressCounter]);
                addressCounter++;
            }
        }

        //loop sends the message to the addresses from addressArray

        for (int z = 1; z <= addressCounter; z++) {
            open("https://e.mail.ru/compose/14765379520000000810/drafts/");
            System.out.println(addressArray[z]);
            $("textarea[data-original-name=\"To\"]").setValue(addressArray[z]);
            $(withText("Отправить")).shouldBe();
        }
    }
}