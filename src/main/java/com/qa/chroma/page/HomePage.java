package com.qa.chroma.page;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.chroma.base.TestBase;
import com.qa.chroma.constants.AppConstants;
import com.qa.chroma.util.ElementUtil;



public class HomePage extends TestBase {

	ElementUtil eleUtil;
	
	

	@FindBy(xpath ="(//*[@name='search'])[4]")
	WebElement txtSearch;
	
	@FindBy(xpath ="(//*[@id='panel1bh-header'])[1]")
	WebElement drpBrand;
	
	@FindBy(xpath = "//*[@for='SG-ManufacturerDetails-Brand-LG']")
	WebElement chkLG;
	
	@FindBy(xpath = "//*[@for='SG-ManufacturerDetails-Brand-Samsung']")
	WebElement chkSamsung;
	
	@FindBy(xpath = "//*[@for='SG-ManufacturerDetails-Brand-Whirlpool']")
	WebElement chkWhirlpool;
	
	@FindBy(id="applied-filters-mobile-desktop")
	List<WebElement> productTitles;
	
	@FindBy(xpath = "(//*[text()='Featured'])[1]")
	WebElement drpsortby;
	
	@FindBy(xpath = "//*[text()='Discount (Descending)']")
	WebElement selectDiscount;
	
	@FindBy(xpath = "//*[@class='discount discount-mob-plp discount-newsearch-plp']")
	List<WebElement> discountLabels;
	
	

	public HomePage() {
		PageFactory.initElements(driver, this);
		eleUtil = new ElementUtil();
	}
	
	
	public String getLoginPageTitle() {

		String homePageTitle = eleUtil.waitForTitleIs(AppConstants.HOME_PAGE_TITLE, AppConstants.MAX_TIME_OUT);
		System.out.println("Login page title==>" + homePageTitle);
		return homePageTitle;
	}

	public String getLoginPageUrl() {
		String loginCurrentUrl = driver.getCurrentUrl();
		return loginCurrentUrl;
	}
	
	public void doSearch(String search) {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    wait.until(ExpectedConditions.elementToBeClickable(txtSearch));
	    
		txtSearch.click();
		txtSearch.sendKeys(search);		
		txtSearch.sendKeys(Keys.ENTER);
		
	}
	
	public void applyBrandFilters() {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.elementToBeClickable(drpBrand)).click();
        wait.until(ExpectedConditions.elementToBeClickable(chkLG)).click();
        wait.until(ExpectedConditions.elementToBeClickable(chkSamsung)).click();
        wait.until(ExpectedConditions.elementToBeClickable(chkWhirlpool)).click();
        drpBrand.click();	
        
        try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean verifyFilteredBrands() {
		
        List<WebElement> products = productTitles;
        for (WebElement product : products) {
            String title = product.getText().toLowerCase();
            if (!(title.contains("samsung") || title.contains("lg") || title.contains("whirlpool"))) {
                System.out.println("Invalid brand found: " + title);
                return false;
            }
        }
        return true;
    }
	
	
	public void applyDiscountSort() {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(drpsortby));
        drpsortby.click();
        wait.until(ExpectedConditions.elementToBeClickable(selectDiscount)).click();
        try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Integer> getDiscountPercentages() {
		
        List<Integer> discounts = new ArrayList<>();
        for (WebElement label : discountLabels) {
            String text = label.getText().replace("% Off", "").trim();
            try {
                discounts.add(Integer.parseInt(text));
            } catch (NumberFormatException e) {
                System.out.println("Skipping invalid discount: " + text);
            }
        }
        return discounts;
    }
	
	public boolean isDiscountSortedDescending() {
		
        List<Integer> actual = getDiscountPercentages();
        List<Integer> sorted = new ArrayList<>(actual);
        Collections.sort(sorted, Collections.reverseOrder());
        return actual.equals(sorted);
    }
	
	public double calculateAverageTop10Prices() {
		
		double total = 0;
        int count = Math.min(discountLabels.size(), 10);

        for (int i = 0; i < count; i++) {
            String priceText = discountLabels.get(i).getText().replaceAll("[^0-9]", "");
            double price = Double.parseDouble(priceText);
            total += price;
        }
        return total / count;
	}
	
	
	
}
