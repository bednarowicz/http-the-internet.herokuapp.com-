import org.checkerframework.common.initializedfields.qual.EnsuresInitializedFields;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.LinkedList;
import java.util.List;

public class quests extends base{
    @Test
    public void ABTask(){
        Actions actions = new Actions(driver);
        goToPage("A/B Testing");
        String stringAdresOtwartejStrony = driver.getCurrentUrl();
        String tekstNaStronie = driver.findElement(By.xpath("//p")).getText();
        Assert.assertTrue("zla strona", stringAdresOtwartejStrony.contains("http://the-internet.herokuapp.com/abtest"));
        Assert.assertTrue("zły tekst wewnątrz strony", tekstNaStronie.contains("Also known as split testing. This is a way in which businesses are able to simultaneously test and learn different versions of a page to see which text and/or functionality works best towards a desired outcome (e.g. a user action such as a click-through)."));
        driver.navigate().back();
    }
    @Test
    public void AddingRemovingElements(){
        goToPage("Add/Remove Elements");
        int liczbaDodanychElementow = 5;
        for (int i = 0; i < liczbaDodanychElementow; i++){
            driver.findElement(By.xpath("//button[@onclick='addElement()']")).click();
        }
        Assert.assertTrue(driver.findElements(By.xpath("//button[@class='added-manually']")).size() == liczbaDodanychElementow);
        for (int i = 0; i < liczbaDodanychElementow; i++){
            driver.findElement(By.xpath("//button[@onclick='deleteElement()']")).click();
        }
        Assert.assertTrue(driver.findElements(By.xpath("//button[@class='added-manually']")).size() == 0);
    }
    @Test
    public void basicCredentials(){
        HasAuthentication authentication = (HasAuthentication) driver;
        authentication.register(()-> new UsernameAndPassword("admin", "admin"));
        goToPage("Basic Auth");
        Assert.assertTrue(driver.findElement(By.cssSelector("p")).getText().contains("Congratulations! You must have the proper credentials."));
        driver.navigate().back();
    }
    @Test
    public void brokenImage(){
        goToPage("Broken Images");
        List<WebElement> images = driver.findElements(By.tagName("img"));
        List<WebElement> corruptImages = new LinkedList<>();
        List<WebElement> correctImages = new LinkedList<>();
        for(WebElement image:images){
            if (image.getAttribute("naturalHeight").equals("0")){
                corruptImages.add(image);
            } else {
                correctImages.add(image);
            }
        }
        if (corruptImages.size() != 0){
            System.out.println("Below images are corrupted");
            for (WebElement image:corruptImages){
                System.out.println(image.getAttribute("src"));
            }
        }
        if (corruptImages.size() != 0){
            System.out.println("Below images are fine");
            for (WebElement image:correctImages){
                System.out.println(image.getAttribute("src"));
            }
        }

    }
    @Test
    public void challengingDOM(){
        //mock up, will be completed later on
    }
    @Test
    public void Checkboxes(){
        goToPage("Checkboxes");
        List<WebElement> checkBoxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
        for (WebElement checkBox:checkBoxes){
            if (checkBox.isSelected()){
                checkBox.click();
            }
        }
        checkBoxes.get(0).click();
        Assert.assertTrue(checkBoxes.get(0).isSelected());
        Assert.assertFalse(checkBoxes.get(1).isSelected());
    }
    @Test
    public void contextMenu(){
        goToPage("Context Menu");
        Actions actions = new Actions(driver);
        actions.contextClick(driver.findElement(By.id("hot-spot"))).perform();
        Assert.assertTrue(driver.switchTo().alert().getText().contains("You selected a context menu"));
        driver.switchTo().alert().accept();
        //driver.switchTo().alert().accept(); You selected a context menu
        Assert.assertFalse(isAlertPresent());

    }
}
