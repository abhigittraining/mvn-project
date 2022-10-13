package com.purna_data.testcases;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.purna_data.libraries.BaseClass;
import com.purna_data.libraries.Utilities;
import com.purna_data.pages.purna_dashboardpage;
import com.purna_data.pages.purna_loginpage;
import com.purna_data.pages.purna_supplierdashboardpage;
import com.purna_data.pages.purna_supplierpage;

public class purna_suppliertest extends BaseClass {

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
		Object[][] data = {
				{ "Paper Bag Manufactures", "7070707070", "City Pride", "paper_bag_manufacturers@gmail", "Vikas",
						"3030303030", "27ATGSTNOABC", "AABBCC231", "Kotak Bank", "Pune Law College Road", "8031503482",
						"KOTAK258428" },
				{ "Steel Enterprises", "7744774477", "Karve Nagar Pune", "steelenterprises@gmail.com", "Kumar",
						"9900990099", "27EYVM588OFR", "FFWW449W", "IBS Bank", "Tilak Road Pune", "4450319202",
						"IBS831582", },
				{ "Cement House", "8031590315", "SB Road Pune", "cementhouse@gmail.com", "Patel", "8016831588",
						"27FJBMSI99J2", "SPFFCMEO1", "Fedral Bank", "Deccan Branch Pune", "4823440337", "FED83182", } };
		return data;
	}

	@Test(dataProvider = "getSupplierData")
	public void adding_new_supplier(/* String srno, */ String suppliername, String contactno, String address,
			String emailid, String contactperson, String contno, String gst, String pan, String bankname,
			String bankadd, String accno, String ifsc) throws InterruptedException {
		utils.clickElement(driver, dashboardpage.link_main);
		utils.clickElement(driver, dashboardpage.link_supplier);
		utils.clickElement(driver, supplierdashboardpage.btn_addnewsupplier);
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
	}

	@AfterMethod
	public void closeBrowser() throws InterruptedException {
		driver.close();
	}
}