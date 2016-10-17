
import org.openqa.selenium.By;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Configuration.timeout;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;


/**
 * Created by johnbunky on 15.05.16.
 */
public class ResumeSender {

    public static void main(String[] argc) {

        String[] addressArray = new String[150];
        int addressCounter = 1;

        open("http://mail.ru");
        inputCredetails();
        addressCounter = searchingOfAddresses(addressArray, addressCounter);
        sendToAddresses(addressArray, addressCounter);
    }

    private static void inputCredetails() {
        $(By.name("Login")).setValue("johnbunky");
        $(By.name("Password")).setValue("*******");
        $(By.id("mailbox__auth__button")).click();
    }

    private static int searchingOfAddresses(String[] addressArray, int addressCounter) {

        //loop opens pages with a list of the email addresses

        for (int i = 7; i > 5; i--) {
            //String pageAddress = "https://e.mail.ru/messages/sent/?page=" + i;
            open("https://e.mail.ru/messages/sent/?page=" + i);

            //loop pushes email addresses to the addressArray

            int addressesQuantity = $$(".b-datalist__item__info").filterBy(text("CV manual/automation test engineer ( remote )")).size();

            for (int j = 1; j <= addressesQuantity; j++) {
                addressArray[addressCounter] = $(By.xpath("(.//*[child::*[*[text()[contains ( . , 'CV manual/automation test engineer ( remote )' )]]]]/div[2])[" + j + "]")).getText();
                System.out.println(addressArray[addressCounter]);
                addressCounter++;
            }
        }
        return addressCounter;
    }

    private static void sendToAddresses(String[] addressArray, int addressCounter) {
        for (int z = 1; z <= addressCounter; z++) {
            open("https://e.mail.ru/compose/14766879130000000140/drafts/");
            System.out.println(addressArray[z]);
            $("textarea[data-original-name=\"To\"]").setValue(addressArray[z]);
            $(withText("Отправить")).click();
            timeout = 10000;
        }
    }
}