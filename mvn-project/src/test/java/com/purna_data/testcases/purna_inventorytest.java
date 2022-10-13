package com.purna_data.testcases;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.purna_data.libraries.BaseClass;
import com.purna_data.libraries.ExcelUtils;
import com.purna_data.libraries.Utilities;
import com.purna_data.pages.purna_dashboardpage;
import com.purna_data.pages.purna_inventorydashboardpage;
import com.purna_data.pages.purna_inventorypage;
import com.purna_data.pages.purna_loginpage;

public class purna_inventorytest extends BaseClass{
	
	purna_loginpage loginpage;
	purna_dashboardpage dashboardpage;
	purna_inventorydashboardpage inventorydashboardpage;
	purna_inventorypage inventorypage;
	Utilities utils;

	@BeforeMethod
	public void login() {
		initialization();
		loginpage = new purna_loginpage(driver);
		dashboardpage = new purna_dashboardpage(driver);
		inventorydashboardpage = new purna_inventorydashboardpage(driver);
		inventorypage = new purna_inventorypage(driver);
		utils = new Utilities();
		loginpage.loginpurna(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getInventoryData(){
		Object[][] data = ExcelUtils.getTestData("inventory");
		return data;
	}
	@Test(dataProvider = "getInventoryData")
	public void adding_new_inventory(String SR_NO, String ITEM_CODE, String ITEM_NAME, String UNITS,
			String SUPPLIER_PRODUCT_CODE, String DAWING_NO, String SPECIFICATION_NO, String WEIGHT, String REMARKS,
			String RATE, String DISCOUNT, String QUANTITY) {
		utils.clickElement(driver, dashboardpage.link_main);
		utils.clickElement(driver, dashboardpage.link_inventory);
		utils.clickElement(driver, inventorydashboardpage.btn_addnewitem);
		inventorypage.txt_sr_no.clear();
		inventorypage.txt_sr_no.sendKeys(SR_NO);
		inventorypage.txt_itemcode.sendKeys(ITEM_CODE);
		inventorypage.txt_itemname.sendKeys(ITEM_NAME);
		utils.doDropDownSelectByVisibleText(inventorypage.dropdown_units, UNITS);
		inventorypage.txt_supp_product_code.sendKeys(SUPPLIER_PRODUCT_CODE);
		inventorypage.txt_drawing_no.sendKeys(DAWING_NO);
		inventorypage.txt_spec_no.sendKeys(SPECIFICATION_NO);
		inventorypage.txt_weight.sendKeys(WEIGHT);
		inventorypage.txt_packing.clear();
		inventorypage.txt_packing.sendKeys(REMARKS);
		inventorypage.txt_rate.sendKeys(RATE);
		inventorypage.txt_discount.clear();
		inventorypage.txt_discount.sendKeys(DISCOUNT);
		inventorypage.txt_qty.sendKeys(QUANTITY);
		utils.clickElement(driver, inventorypage.txt_amount);
		utils.clickElement(driver, inventorypage.btn_save_item);
		utils.clickElement(driver, inventorypage.btn_itemdetails);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='" + ITEM_CODE + "']")).getText(), ITEM_CODE);
		driver.findElement(By.xpath("//a[@href='view_purchase_item.php?sr_no="+SR_NO+"']")).click();
	}

	@AfterMethod
	public void closeBrowser() throws InterruptedException {
		Thread.sleep(3000);		driver.quit();
	}
}
