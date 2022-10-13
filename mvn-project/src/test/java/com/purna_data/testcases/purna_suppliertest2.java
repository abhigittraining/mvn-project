package com.purna_data.testcases;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
import com.purna_data.pages.purna_supplierdashboardpage;
import com.purna_data.pages.purna_supplierpage;

public class purna_suppliertest2 extends BaseClass {

	purna_loginpage loginpage;
	purna_dashboardpage dashboardpage;
	purna_supplierdashboardpage supplierdashboardpage;
	purna_supplierpage supplierpage;
	Utilities utils;

	@BeforeMethod
	public void login() {
		initialization();
		loginpage = new purna_loginpage(driver);
		dashboardpage = new purna_dashboardpage(driver);
		supplierdashboardpage = new purna_supplierdashboardpage(driver);
		supplierpage = new purna_supplierpage(driver);
		utils = new Utilities();
		loginpage.loginpurna(prop.getProperty("username"), prop.getProperty("password"));
	}

	@DataProvider
	public Object[][] getSupplierData() {
		Object[][] data = ExcelUtils.getTestData("supp");
		return data;
	}

	@Test(dataProvider = "getSupplierData")
	public void adding_new_supplier(String vendorcode, String suppliername, String contactno, String address,
			String emailid, String contactperson, String contno, String gst, String pan, String bankname,
			String bankadd, String accno, String ifsc) throws InterruptedException {
		utils.clickElement(driver, dashboardpage.link_main);
		utils.clickElement(driver, dashboardpage.link_supplier);
		Thread.sleep(300);
		utils.clickElement(driver, supplierdashboardpage.btn_addnewsupplier);
		supplierpage.txt_sr_no.clear();
		supplierpage.txt_sr_no.sendKeys(vendorcode);
		supplierpage.txt_supplier_name.sendKeys(suppliername);
		supplierpage.txt_contact_no.sendKeys(contactno);
		supplierpage.txt_address.clear();
		supplierpage.txt_address.sendKeys(address);
		supplierpage.txt_email_id.sendKeys(emailid);
		supplierpage.txt_contact_person.sendKeys(contactperson);
		supplierpage.txt_cont_no.sendKeys(contno);
		supplierpage.txt_gst_no.sendKeys(gst);
		supplierpage.txt_pan_no.sendKeys(pan);
		supplierpage.txt_bank_name.sendKeys(bankname);
		supplierpage.txt_bank_addr.sendKeys(bankadd);
		supplierpage.txt_account_no.sendKeys(accno);
		supplierpage.txt_ifsc_code.sendKeys(ifsc);
		utils.clickElement(driver, supplierpage.btn_save_supplierdetails);
		utils.syncElement(driver, null, "alertPresent");
		utils.handleAlert(driver).accept();
		utils.clickElement(driver, supplierpage.btn_supplierdetails);
		Assert.assertEquals(driver.findElement(By.xpath("//td[text()='" + suppliername + "']")).getText(),
				suppliername);
		driver.findElement(By.xpath("//a[@href='view_supplier.php?sr_no=" + vendorcode + "']")).click();
		List<WebElement> txtbox_supplier_details = driver.findElements(By.xpath("//input[@readonly]"));
		for (int i = 0; i < txtbox_supplier_details.size(); i++) {
			if (i == 3) {
				String add = driver.findElement(By.id("address")).getText();
				System.out.println("address ---> " + add);
			}
			String s1 = txtbox_supplier_details.get(i).getAttribute("name");
			String s2 = txtbox_supplier_details.get(i).getAttribute("value");
			System.out.println(s1 + " ---> " + s2);
		}
	}

	@AfterMethod
	public void closeBrowser() throws InterruptedException {
		Thread.sleep(1000);
		driver.quit();
	}
}