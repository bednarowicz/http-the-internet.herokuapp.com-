import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.checkerframework.common.initializedfields.qual.EnsuresInitializedFields;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class quests extends base {
    @Test
    public void ABTask() {
        //Actions actions = new Actions(driver);
        goToPage("A/B Testing");
        String stringAdresOtwartejStrony = driver.getCurrentUrl();
        String tekstNaStronie = driver.findElement(By.xpath("//p")).getText();
        Assert.assertTrue("zla strona", stringAdresOtwartejStrony.contains("http://the-internet.herokuapp.com/abtest"));
        Assert.assertTrue("zły tekst wewnątrz strony", tekstNaStronie.contains("Also known as split testing. This is a way in which businesses are able to simultaneously test and learn different versions of a page to see which text and/or functionality works best towards a desired outcome (e.g. a user action such as a click-through)."));
        driver.navigate().back();
    }

    @Test
    public void AddingRemovingElements() {
        goToPage("Add/Remove Elements");
        int liczbaDodanychElementow = 5;
        for (int i = 0; i < liczbaDodanychElementow; i++) {
            driver.findElement(By.xpath("//button[@onclick='addElement()']")).click();
        }
        Assert.assertTrue(driver.findElements(By.xpath("//button[@class='added-manually']")).size() == liczbaDodanychElementow);
        for (int i = 0; i < liczbaDodanychElementow; i++) {
            driver.findElement(By.xpath("//button[@onclick='deleteElement()']")).click();
        }
        Assert.assertTrue(driver.findElements(By.xpath("//button[@class='added-manually']")).size() == 0);
    }

    @Test
    public void basicCredentials() {
        HasAuthentication authentication = (HasAuthentication) driver;
        authentication.register(() -> new UsernameAndPassword("admin", "admin"));
        goToPage("Basic Auth");
        Assert.assertTrue(driver.findElement(By.cssSelector("p")).getText().contains("Congratulations! You must have the proper credentials."));
        driver.navigate().back();
    }

    @Test
    public void brokenImage() {
        goToPage("Broken Images");
        List<WebElement> images = driver.findElements(By.tagName("img"));
        List<WebElement> corruptImages = new LinkedList<>();
        List<WebElement> correctImages = new LinkedList<>();
        for (WebElement image : images) {
            if (image.getAttribute("naturalHeight").equals("0")) {
                corruptImages.add(image);
            } else {
                correctImages.add(image);
            }
        }
        if (corruptImages.size() != 0) {
            System.out.println("Below images are corrupted");
            for (WebElement image : corruptImages) {
                System.out.println(image.getAttribute("src"));
            }
        }
        if (corruptImages.size() != 0) {
            System.out.println("Below images are fine");
            for (WebElement image : correctImages) {
                System.out.println(image.getAttribute("src"));
            }
        }

    }

    @Test
    public void challengingDOM() {
        //mock up, will be completed later on
    }

    @Test
    public void Checkboxes() {
        goToPage("Checkboxes");
        List<WebElement> checkBoxes = driver.findElements(By.xpath("//input[@type='checkbox']"));
        for (WebElement checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                checkBox.click();
            }
        }
        checkBoxes.get(0).click();
        Assert.assertTrue(checkBoxes.get(0).isSelected());
        Assert.assertFalse(checkBoxes.get(1).isSelected());
    }

    @Test
    public void contextMenu() {
        goToPage("Context Menu");
        Actions actions = new Actions(driver);
        actions.contextClick(driver.findElement(By.id("hot-spot"))).perform();
        Assert.assertTrue(driver.switchTo().alert().getText().contains("You selected a context menu"));
        driver.switchTo().alert().accept();
        //driver.switchTo().alert().accept(); You selected a context menu
        Assert.assertFalse(isAlertPresent());

    }

    @Test
    public void digestAuthentication() {
        HasAuthentication authentication = (HasAuthentication) driver;
        authentication.register(() -> new UsernameAndPassword("admin", "admin"));
        goToPage("Digest Authentication");
        Assert.assertTrue(driver.findElement(By.xpath("//p")).getText().contains("Congratulations! You must have the proper credentials."));
    }

    @Test
    public void disappearingElements() {
        goToPage("Disappearing Elements");
        for (int i = 0; i < 5; i++) {
            try {
                driver.findElement(By.xpath("//a[text()='Gallery']")).click();
                break;
            } catch (Exception e) {
                System.out.println("Gallery was not visible. Let's refresh site.");
                driver.navigate().refresh();
            }
        }
        Assert.assertTrue(driver.getCurrentUrl().equals("http://the-internet.herokuapp.com/gallery/"));
    }

    @Test
    public void dragAndDrop() {
        goToPage("Drag and Drop");
        Actions actions = new Actions(driver);
        actions.dragAndDrop(driver.findElement(By.id("column-a")), driver.findElement(By.id("column-b"))).perform();
        Assert.assertTrue(driver.findElement(By.id("column-a")).getText().equals("B"));
        Assert.assertTrue(driver.findElement(By.id("column-b")).getText().equals("A"));
    }

    @Test
    public void dropDown() {
        goToPage("Dropdown");
        String chosenOption = "Option 2";
        driver.findElement(By.id("dropdown")).sendKeys(chosenOption);
        Assert.assertTrue(!driver.findElement(By.xpath("//select/option[@value='2']")).getAttribute("selected").isEmpty());
    }

    @Test
    public void dynamicContent() {
        goToPage("Dynamic Content");
        int tryAmount = 5;
        List<WebElement> images = driver.findElements(By.tagName("img"));
        for (int i = 0; i < tryAmount; i++) {
            System.out.println(images.get(3).getAttribute("src"));
            if (images.get(3).getAttribute("src").toString().endsWith("/img/avatars/Original-Facebook-Geek-Profile-Avatar-7.jpg")) {
                System.out.println("Female joker appeared in 3rd picture.");
                break;
            } else {
                if (i != (tryAmount - 1)) {
                    System.out.println("Looking for joker picture, driver will click button");
                    driver.findElement(By.xpath("//p//a[text()='click here']")).click();
                    images = driver.findElements(By.tagName("img"));
                }
                if (i == (tryAmount - 1)) {
                    System.out.println("Joker escaped.");
                }
            }
        }
    }
    @Test
    public void dynamicControls(){
        goToPage("Dynamic Controls");

        String xpathCheckbox = "//input[@type='checkbox']" ; //checking checkbox state
        WebElement checkbox = driver.findElement(By.xpath(xpathCheckbox));
        Assert.assertTrue(checkbox.isEnabled());

        String xpathRemoveButton = "//button[text()='Remove']"; //clicking remove button
        WebElement removeButton = driver.findElement(By.xpath(xpathRemoveButton));
        removeButton.click();

        String xpathLoading = "//div[@id='loading']"; // checking loading sign
        Assert.assertTrue(driver.findElement(By.xpath(xpathLoading)).isDisplayed());

        String xpathAddButton = "//button[text()='Add']"; // using add button
        WebElement addButton = driver.findElement(By.xpath(xpathAddButton));
        waitForElementToExist(driver, addButton);
        Assert.assertTrue(addButton.isEnabled());
        addButton.click();


        removeButton = driver.findElement(By.xpath(xpathRemoveButton)); // using remove button
        waitForElementToExist(driver,removeButton);
        removeButton.click();

        addButton = driver.findElement(By.xpath(xpathAddButton)); // ussing add button
        waitForElementToExist(driver, addButton);
        Assert.assertTrue(addButton.isEnabled());
        addButton.click();

        checkbox = driver.findElement(By.xpath(xpathCheckbox)); //using checkbox
        waitForElemntToBeClickable(driver, checkbox);
        checkbox.click();
        Assert.assertTrue(checkbox.isSelected());

        //let's take care of second part

        String xpathFillableField =  "//input[@type='text']";
        WebElement fillableField = driver.findElement(By.xpath(xpathFillableField));
        Assert.assertFalse(fillableField.isEnabled());

        String xpathButton = "//form[@id='input-example']/button";
        WebElement enableButton = driver.findElement(By.xpath(xpathButton));
        enableButton.click();

        String xpathEnableDisable = "//p[@id='message']";
        String xpathEnable = "//p[@id='message' and text()='Enable']";
        String xpathDisable = "//p[@id='message' and text()='Disable']";
        WebElement isEnabledText = driver.findElement(By.xpath(xpathEnableDisable));
        waitForElementToExist(driver, isEnabledText);
        Assert.assertTrue(isEnabledText.getText().contains("It's enabled!"));

        fillableField = driver.findElement(By.xpath(xpathFillableField));
        fillableField.sendKeys("Test");
        Assert.assertTrue(fillableField.getAttribute("value").equals("Test"));

        //String xpathDisableButton = "//button[text()='Disable']";

        WebElement disableButton = driver.findElement(By.xpath(xpathButton));
        Assert.assertTrue(disableButton.getText().equals("Disable"));
        disableButton.click();

        enableButton = driver.findElement(By.xpath(xpathButton));
        waitForElemntToBeClickable(driver, enableButton);
        Assert.assertFalse(fillableField.isEnabled());
    }
    @Test
    public void dynamicallyLoadedPageElements1st(){
        goToPage("Dynamic Loading");
        driver.findElement(By.xpath("//a[text()='Example 1: Element on page that is hidden']")).click();
        driver.findElement(By.xpath("//button[text()='Start']")).click();
        WebElement hiddenText = driver.findElement(By.xpath("//div[@id='finish']//h4"));
        waitForElementToExist(driver, hiddenText);
        Assert.assertTrue(hiddenText.getText().equals("Hello World!"));
    }
    @Test
    public void dynamicallyLoadedPageElements2nd() throws InterruptedException {
        goToPage("Dynamic Loading");
        driver.findElement(By.xpath("//a[text()='Example 2: Element rendered after the fact']")).click();
        driver.findElement(By.xpath("//button[text()='Start']")).click();
        TimeUnit.SECONDS.sleep(4);
        WebElement hiddenText = driver.findElement(By.xpath("//div[@id='finish']//h4"));
        waitForElementToExist(driver, hiddenText);
        Assert.assertTrue(hiddenText.getText().equals("Hello World!"));
    }
    @Test
    public void entryAdd(){      //Does not work
        goToPage("Entry Ad");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement clickable = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@class='modal-footer']")))); //(By.className("modal")); //p[text()='Close']
        Assert.assertTrue(driver.findElement(By.id("modal")).getAttribute("style").contains("block"));
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@class='modal-footer']"))); // for unkown to me reason only method with javascript worked; both method .click of WebElement & simulating clicking via actions did not work for me
        Assert.assertTrue(driver.findElement(By.id("modal")).getAttribute("style").contains("none"));
    }
    @Test
    public void exitIntent() throws InterruptedException, AWTException {
        goToPage("Exit Intent");
       // Actions actions = new Actions(driver);
       // actions.moveToElement(driver.findElement(By.id("flash-messages"))).moveByOffset(0, -15).perform(); //actions did not make a trick, let's try robot
        Robot robot = new Robot();
        robot.mouseMove(20,0);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement clickable = wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//div[@class='modal-footer']")))); //(By.className("modal")); //p[text()='Close']
        Assert.assertTrue(driver.findElement(By.id("ouibounce-modal")).getAttribute("style").contains("block"));
        JavascriptExecutor js= (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//div[@class='modal-footer']"))); // for unkown to me reason only method with javascript worked; both method .click of WebElement & simulating clicking via actions did not work for me
        Assert.assertTrue(driver.findElement(By.id("ouibounce-modal")).getAttribute("style").contains("none"));

    }

    @Test
    public void uploadFile() {
        goToPage("File Upload");
        driver.findElement(By.id("file-upload")).sendKeys("C:\\Users\\Rafi\\Desktop\\fryzjer.txt");
        driver.findElement(By.id("file-submit")).click();
        Assert.assertTrue(driver.findElement(By.id("uploaded-files")).getText().equals("fryzjer.txt"));
    }
    @Test
    public void floatingMenu(){
        goToPage("Floating Menu");
        driver.findElement(By.xpath("//a[@href='#home']")).click();
        Assert.assertTrue(driver.getCurrentUrl().endsWith("#home"));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,2250)", "");
        driver.findElement(By.xpath("//a[@href='#contact']")).click();
        Assert.assertTrue(driver.getCurrentUrl().endsWith("#contact"));
    }
    @Test
    public void loginPage(){
        goToPage("Form Authentication");
        driver.findElement(By.className("radius")).click();
        Assert.assertTrue(driver.findElement(By.id("flash")).getText().contains("Your username is invalid!"));
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.className("radius")).click();
        Assert.assertTrue(driver.findElement(By.id("flash")).getText().contains("Your password is invalid!"));
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();
        Assert.assertTrue(driver.findElement(By.id("flash")).getText().contains("You logged into a secure area!"));
    }
    @Test
    public void framesNestedFrame(){
        goToPage("Frames");
        driver.findElement(By.xpath("//a[text()='Nested Frames']")).click();
        driver.switchTo().frame(driver.findElement(By.xpath("//frame[@name='frame-top']")))
                .switchTo().frame(driver.findElement(By.xpath("//frame[@name='frame-middle']")));
        Assert.assertTrue(driver.findElement(By.id("content")).getText().equals("MIDDLE"));
    }
    @Test
    public void frames2ndTask() throws InterruptedException {
        goToPage("Frames");
        driver.findElement(By.xpath("//a[text()='iFrame']")).click();
        TimeUnit.SECONDS.sleep(1);
        WebElement frame = driver.findElement(By.id("mce_0_ifr")); // saving frame for input of text
        driver.switchTo().frame(frame);


        WebElement text = driver.findElement(By.id("tinymce"));
        waitForElemntToBeClickable(driver, text);
        text.clear();
        text.sendKeys("First line.");
        driver.switchTo().defaultContent(); // returning to default frame to use bolded text
        driver.findElement(By.xpath("//button[@class='tox-tbtn' and @title='Bold']")).click();
        driver.switchTo().frame(frame);
        text.sendKeys("Bolded text.");

        driver.switchTo().defaultContent();
        driver.findElement(By.xpath("//span[text()='Format']")).click();
        driver.findElement(By.xpath("//div[@role='menu']//div[text()='Align']")).click();
        driver.findElement(By.xpath("//div[@role='menu']//div[text()='Right']")).click();
        driver.switchTo().frame(frame);
        text.sendKeys("LastString");
    }
    @Test
    public void horizontalSlider(){
        goToPage("Horizontal Slider");
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(By.xpath("//div[@class='sliderContainer']//input")))
                .clickAndHold().keyDown(Keys.ARROW_RIGHT).keyUp(Keys.ARROW_RIGHT).keyDown(Keys.ARROW_RIGHT).keyUp(Keys.ARROW_RIGHT).perform(); // by default clicks on middle of range -> hence starts with value 2.5 and each right arrows increases value by 0.5 and each left arrow should decrease value by 0.5
        Assert.assertTrue(driver.findElement(By.id("range")).getText().equals("3.5"));
    }
    @Test @Ignore
    public void Inputs(){
        goToPage("Inputs");
        driver.findElement(By.xpath("//input[@type='number']")).sendKeys("14");
        //Assert.assertTrue(driver.findElement(By.xpath("//input[@type='number']")).getText().equals("14"));
        //there is no value visible in structure/buttons so with pure Selenium I am not able to get value of field and
        //I would probably use Javascript or some library to deal with it
    }


}
