package com.qa.chroma.testcase;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.qa.chroma.base.TestBase;
import com.qa.chroma.page.HomePage;
import com.qa.chroma.util.ExcelUtil;
import com.qa.chroma.constants.AppConstants;

public class HomePageTestCase extends TestBase {

	HomePage homepage;

	public HomePageTestCase() {
		super();

	}

	@BeforeClass
	public void setup() {
		initialazation();
		homepage = new HomePage();
	}

	@DataProvider
	public Object[][] geDataExcel() {
		Object data[][] = ExcelUtil.getTestData("Sheet1");
		return data;
	}

	@Test(priority = 1)
	public void verifytitlePageTest() {
		String HomePagetitle = homepage.getLoginPageTitle();
		Assert.assertEquals(HomePagetitle, AppConstants.HOME_PAGE_TITLE);
	}

	@Test(priority = 2, dataProvider = "geDataExcel")
	public void getSearchTest(String search1) {

		homepage.doSearch(search1);

		String SearchTitle = homepage.getLoginPageTitle();
		Assert.assertEquals(SearchTitle, AppConstants.SEARCH_PAGE_TITLE);

	}
	
	@Test(priority = 3)
	public void selectBrandDropdownTest() {
		homepage.applyBrandFilters();
		Assert.assertTrue(homepage.verifyFilteredBrands(),
                "Search results contain products from unselected brands!");
	}
	
	@Test(priority = 4)
	public void verifyDiscountSortingTest(){
		homepage.applyDiscountSort();
		Assert.assertTrue(homepage.isDiscountSortedDescending(),
                "Products are NOT sorted in descending order of discount!");
		
	}
	
	@Test(priority = 5)
	public void verifyAveragePriceOfTop10DiscountedProducts() {
		double averagePrice = homepage.calculateAverageTop10Prices();
        System.out.println("Average price of top 10 discounted products: ₹" + averagePrice);
	}
	
	

	@AfterClass
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
