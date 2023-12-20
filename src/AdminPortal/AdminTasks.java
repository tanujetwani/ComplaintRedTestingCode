package AdminPortal;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import utils.DBUtil;

public class AdminTasks {

	
    WebDriver wd=null;
	
	@Before
	public void init() {
		
		System.setProperty("webdriver.chrome.driver","C:\\Users\\tanuj\\OneDrive\\Desktop\\tanu\\full stack\\Capstone Projects\\chromedriver-win32\\chromedriver-win32\\chromedriver.exe");
		wd=new ChromeDriver();
		
		wd.get("http://localhost:4200");
		wd.manage().window().maximize();
		
		
	}

	//Registering Admin user
	@Test
	public void testA() throws InterruptedException {
		String actualHome=wd.findElement(By.tagName("h1")).getText();
  	    String expectedHome="Welcome to ABC Telecom Complaint Redressal System";
  	    
  	    Thread.sleep(5000);
  	    assertEquals(expectedHome,actualHome);
  	    
  	     wd.findElement(By.linkText("Register")).click();
  	     Thread.sleep(5000);
  	     
  	   String actualRegister=wd.findElement(By.xpath("/html/body/app-root/html/body/app-register/html/body/h1")).getText();
       String expectedRegister="Registration form";
       
       assertEquals(expectedRegister,actualRegister);
      
       wd.findElement(By.name("username")).sendKeys("admin1");
       Thread.sleep(1000);
       wd.findElement(By.name("pwd")).sendKeys("ad123");
       Thread.sleep(1000);
       WebElement role=wd.findElement(By.name("role"));
       
       Select r1=new Select(role);
       
       r1.selectByVisibleText("Admin");
       
       wd.findElement(By.tagName("button")).click();
       
       Thread.sleep(5000);
       
       String msgExpected="Hi admin1 you are registered successfully";
       String msgActual=wd.findElement(By.xpath("/html/body/app-root/html/body/app-register/html/body/b")).getText();
       
       assertEquals(msgExpected,msgActual);
       Thread.sleep(4000);
	}
	
	
	//Creating Customer,Manager,Engineer user by Admin
	@Test
	public void testB() throws InterruptedException {
		
		wd.findElement(By.linkText("Login")).click();
		
		Thread.sleep(5000);
		String msgExpected="Login Page";
		String msgActual=wd.findElement(By.xpath("/html/body/app-root/html/body/app-login/h1")).getText();
		
		assertEquals(msgExpected,msgActual);
		
		wd.findElement(By.name("usernm")).sendKeys("admin1");
		wd.findElement(By.name("pwd")).sendKeys("ad123");
		Thread.sleep(3000);
		wd.findElement(By.tagName("button")).click();
		
		Thread.sleep(5000);
		String adminExp="Welcome to admin dashboard admin1";
		String adminAct=wd.findElement(By.xpath("/html/body/app-root/html/body/app-adminpage/h1")).getText();
	    
		assertEquals(adminExp,adminAct);
		Thread.sleep(5000);
		wd.findElement(By.xpath("/html/body/app-root/html/body/app-adminpage/button[1]")).click();
		
		String exp="Registration form";
		String act=wd.findElement(By.xpath("/html/body/app-root/html/body/app-register/html/body/h1")).getText();
		
		assertEquals(exp,act);
		
	//Creating Cust user	
		wd.findElement(By.name("username")).sendKeys("Cust1");
		Thread.sleep(1000);
		wd.findElement(By.name("pwd")).sendKeys("cust1");
		
		WebElement role=wd.findElement(By.name("role"));
	       
	       Select r1=new Select(role);
	       
	       r1.selectByVisibleText("Customer");
	       
	       wd.findElement(By.tagName("button")).click();
	       
	       Thread.sleep(5000);
	       
	       String exp1="Hi Cust1 you are registered successfully";
	       String act1=wd.findElement(By.xpath("/html/body/app-root/html/body/app-register/html/body/b")).getText();
	       
	       assertEquals(exp1,act1);
	       
	    Thread.sleep(4000);  
	       
	       wd.findElement(By.xpath("/html/body/app-root/html/body/app-register/html/body/div/button")).click();
	       Thread.sleep(5000);
	       String exp2="Welcome to admin dashboard admin1";
	       String act2=wd.findElement(By.xpath("/html/body/app-root/html/body/app-adminpage/h1")).getText();
	       
	       assertEquals(exp2,act2);
	       
		
	       //Creating Manager User
	       wd.findElement(By.xpath("/html/body/app-root/html/body/app-adminpage/button[1]")).click();
	       Thread.sleep(5000);
			
			String exp3="Registration form";
			String act3=wd.findElement(By.xpath("/html/body/app-root/html/body/app-register/html/body/h1")).getText();
			
			assertEquals(exp3,act3);
			
			wd.findElement(By.name("username")).sendKeys("Manager1");
			Thread.sleep(1000);
			wd.findElement(By.name("pwd")).sendKeys("man1");
			
			WebElement role2=wd.findElement(By.name("role"));
		       
		       Select r2=new Select(role2);
		       
		       r2.selectByVisibleText("Manager");
		       
		       Thread.sleep(1000);
		       
		       wd.findElement(By.tagName("button")).click();
		    
		       Thread.sleep(5000);
		       String exp4="Hi Manager1 you are registered successfully";
		       String act4=wd.findElement(By.xpath("/html/body/app-root/html/body/app-register/html/body/b")).getText();
		       
		       assertEquals(exp4,act4);
		
		      Thread.sleep(5000);       
		       //Clicking on back button
		       
		       wd.findElement(By.xpath("/html/body/app-root/html/body/app-register/html/body/div/button")).click();
		       
		       Thread.sleep(5000);
		       String exp5="Welcome to admin dashboard admin1";
		       String act5=wd.findElement(By.xpath("/html/body/app-root/html/body/app-adminpage/h1")).getText();
		       
		       assertEquals(exp5,act5);
		       
		       
		       //Creating Engineer user
		       wd.findElement(By.xpath("/html/body/app-root/html/body/app-adminpage/button[1]")).click();
		       
		       Thread.sleep(5000);
				
				String exp6="Registration form";
				String act6=wd.findElement(By.xpath("/html/body/app-root/html/body/app-register/html/body/h1")).getText();
				
				assertEquals(exp6,act6);
				
				wd.findElement(By.name("username")).sendKeys("Engineer1");
				Thread.sleep(1000);
				wd.findElement(By.name("pwd")).sendKeys("eng1");
				
				WebElement role3=wd.findElement(By.name("role"));
			       
			       Select r3=new Select(role3);
			       
			       r3.selectByVisibleText("Engineer");
			       Thread.sleep(1000);
			       
			       wd.findElement(By.tagName("button")).click();
			       
			       Thread.sleep(5000);
			       
			       String exp7="Hi Engineer1 you are registered successfully";
			       String act7=wd.findElement(By.xpath("/html/body/app-root/html/body/app-register/html/body/b")).getText();
			       
			       assertEquals(exp7,act7);

                    Thread.sleep(5000);	
                    
                    
                    //Clicking on back button
                    wd.findElement(By.xpath("/html/body/app-root/html/body/app-register/html/body/div/button")).click();
                    
                    Thread.sleep(5000);
     		       String exp8="Welcome to admin dashboard admin1";
     		       String act8=wd.findElement(By.xpath("/html/body/app-root/html/body/app-adminpage/h1")).getText();
     		       
     		       assertEquals(exp8,act8);
		       
     		       
     		       //Creating another enginneer
     		      wd.findElement(By.xpath("/html/body/app-root/html/body/app-adminpage/button[1]")).click();
     		      
     		      Thread.sleep(5000);
  				
  				String exp9="Registration form";
  				String act9=wd.findElement(By.xpath("/html/body/app-root/html/body/app-register/html/body/h1")).getText();
  				
  				assertEquals(exp9,act9);
  				
  				wd.findElement(By.name("username")).sendKeys("Engineer2");
  				Thread.sleep(1000);
  				wd.findElement(By.name("pwd")).sendKeys("eng2");
  				
  				WebElement role4=wd.findElement(By.name("role"));
  			       
  			       Select r4=new Select(role4);
  			       
  			       r4.selectByVisibleText("Engineer");
  			       Thread.sleep(1000);
  			       
  			       wd.findElement(By.tagName("button")).click();
  			       
  			       Thread.sleep(5000);
  			       
  			       String exp10="Hi Engineer2 you are registered successfully";
  			       String act10=wd.findElement(By.xpath("/html/body/app-root/html/body/app-register/html/body/b")).getText();
  			       
  			       assertEquals(exp10,act10);

                      Thread.sleep(3000);	
	}
	

	//Assigning Pin Codes to manager
	@Test
	public void testC() throws InterruptedException, ClassNotFoundException, SQLException {
		
        wd.findElement(By.linkText("Login")).click();
		
        Thread.sleep(5000);
		String msgExpected="Login Page";
		String msgActual=wd.findElement(By.xpath("/html/body/app-root/html/body/app-login/h1")).getText();
		
		assertEquals(msgExpected,msgActual);
		
		wd.findElement(By.name("usernm")).sendKeys("admin1");
		wd.findElement(By.name("pwd")).sendKeys("ad123");
		Thread.sleep(3000);
		wd.findElement(By.tagName("button")).click();
		
		Thread.sleep(5000);
		String adminExp="Welcome to admin dashboard admin1";
		String adminAct=wd.findElement(By.xpath("/html/body/app-root/html/body/app-adminpage/h1")).getText();
	
		assertEquals(adminExp,adminAct);
		
		Thread.sleep(2000);
		
		//Click on Assign Pin Codes to Manager
		wd.findElement(By.xpath("/html/body/app-root/html/body/app-adminpage/button[5]")).click();
		Thread.sleep(6000);
		
		String exp="Assign Pin codes To Manager";
		String act=wd.findElement(By.xpath("/html/body/app-root/html/body/app-assign-pincodes/h1")).getText();
		
		assertEquals(exp,act);
		
		List<WebElement> managers=wd.findElements(By.className("two"));
		
		for(int i=2;i<=managers.size()+1; i++) {
			
		if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-assign-pincodes/table/tr["+i+"]/td[1]")).getText().contains("Manager1")){
		
		wd.findElement(By.xpath("/html/body/app-root/html/body/app-assign-pincodes/table/tr["+i+"]/td[3]/form/input")).sendKeys("201014");
		
		wd.findElement(By.xpath("/html/body/app-root/html/body/app-assign-pincodes/table/tr["+i+"]/td[4]/button")).click();
		Thread.sleep(5000);
		
		}//End of if 
		
		}//End of for
		
		String assignPinExp="Pincode 201014 for manager Manager1 has been set.";
		String assignPinAct=wd.findElement(By.xpath("/html/body/app-root/html/body/app-assign-pincodes/b")).getText();
	
	    assertEquals(assignPinExp,assignPinAct);
	    
	    
	    Connection con=DBUtil.getConnection();
		String sql2="select * from users where username='Manager1'";
		PreparedStatement ps2=con.prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE);
		ResultSet rs2=ps2.executeQuery();
		int man_id=0;
		while(rs2.next()) {
			
			man_id=rs2.getInt("userid");
		}
		
	       String sql="select * from manager_pin_code where manager_id=?";
	       PreparedStatement ps=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE);
	    //   ps.setInt(1, rs2.getInt("userid"));
	       ps.setInt(1, man_id);
	       ResultSet rs=ps.executeQuery();
	       
	       String pincode="";
	       while(rs.next()) {
	    	      
	    	   pincode=rs.getString("pin_code");
	       }
	 
	       
	       assertEquals("201014",pincode);
	}

}
