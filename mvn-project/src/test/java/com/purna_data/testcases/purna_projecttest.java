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
import com.purna_data.pages.purna_loginpage;
import com.purna_data.pages.purna_projectdashboardpage;
import com.purna_data.pages.purna_projectpage;

public class purna_projecttest extends BaseClass {

	purna_loginpage loginpage;
	purna_dashboardpage dashboardpage;
	purna_projectdashboardpage projectdashboardpage;
	purna_projectpage projectpage;
	Utilities utils;

	@BeforeMethod
	public void login() {
		initialization();
		loginpage = new purna_loginpage(driver);
		dashboardpage = new purna_dashboardpage(driver);
		projectdashboardpage = new purna_projectdashboardpage(driver);
		projectpage = new purna_projectpage(driver);
		utils = new Utilities();
		loginpage.loginpurna(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getProjectData() {
		Object[][] data = ExcelUtils.getTestData("project");
		return data;
	}

	@Test(dataProvider = "getProjectData")
	public void adding_new_project(String SR_NO, String PROJECT_CODE, String PROJECT_NAME, String REMARK,
			String BUDGET_AMOUNT, String CUSTOMER_NAME) throws InterruptedException {
		utils.clickElement(driver, dashboardpage.link_main);
		utils.clickElement(driver, dashboardpage.link_project);
		Thread.sleep(300);
		utils.clickElement(driver, projectdashboardpage.btn_addnewproject);
		projectpage.txt_sr_no.clear();
		projectpage.txt_sr_no.sendKeys(SR_NO);
		projectpage.txt_project_code.sendKeys(PROJECT_CODE);
		projectpage.txt_project_name.sendKeys(PROJECT_NAME);
		projectpage.txt_remark.sendKeys(REMARK);
		projectpage.txt_budget_amount.sendKeys(BUDGET_AMOUNT);
		Thread.sleep(200);
		utils.doDropDownSelectByValue(projectpage.dropdown_cust_name, CUSTOMER_NAME);
		projectpage.btn_save_projectdetails.click();
		utils.syncElement(driver, null, "alertPresent");
		utils.handleAlert(driver).accept();
		utils.clickElement(driver, projectpage.btn_projectdetails);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='" + PROJECT_NAME + "']")).getText(), PROJECT_NAME);
		//td[text()='Residential Building']				view_project.php?sr_no=6
		driver.findElement(By.xpath("//a[@href='view_project.php?sr_no="+SR_NO+"']")).click();
	}

	@AfterMethod
	public void closeBrowser() throws InterruptedException {
		driver.quit();
	}
}

// project module is depended on customer model