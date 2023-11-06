import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

public class quests extends base{
    @Test
    public void ABTask(){
        Actions actions = new Actions(driver);
        driver.findElement(By.xpath("//ul//a[text()='A/B Testing']")).click();
        String stringAdresOtwartejStrony = driver.getCurrentUrl();
        String tekstNaStronie = driver.findElement(By.xpath("//p")).getText();
        Assert.assertTrue("zla strona", stringAdresOtwartejStrony.contains("http://the-internet.herokuapp.com/abtest"));
        Assert.assertTrue("zły tekst wewnątrz strony", tekstNaStronie.contains("Also known as split testing. This is a way in which businesses are able to simultaneously test and learn different versions of a page to see which text and/or functionality works best towards a desired outcome (e.g. a user action such as a click-through)."));
        driver.navigate().back();
    }
}
