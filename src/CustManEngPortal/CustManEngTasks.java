package CustManEngPortal;

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

public class CustManEngTasks {
       
	
	
   WebDriver wd=null;
	
	@Before
	public void init() {
		
		System.setProperty("webdriver.chrome.driver","C:\\Users\\tanuj\\OneDrive\\Desktop\\tanu\\full stack\\Capstone Projects\\chromedriver-win32\\chromedriver-win32\\chromedriver.exe");
		wd=new ChromeDriver();
		
		wd.get("http://localhost:4200");
		wd.manage().window().maximize();
		
	}
	
	//Create Ticket
	@Test
	public void testA() throws ClassNotFoundException, SQLException, InterruptedException {
	          
		//Login to the portal
		
		wd.findElement(By.linkText("Login")).click();
		String act1=wd.findElement(By.xpath("/html/body/app-root/html/body/app-login/h1")).getText();
		String exp1="Login Page";
		
		assertEquals(exp1,act1);
		
		wd.findElement(By.name("usernm")).sendKeys("Cust1");
		wd.findElement(By.name("pwd")).sendKeys("cust1");
		Thread.sleep(3000);
		wd.findElement(By.tagName("button")).click();
		
		//String act2=wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/h1")).getText();
		//String act2=wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/h2")).getText();
		//String exp2="Welcome to Customer Page";
		//String exp2="Create Ticket";
		
		//assertEquals(exp2,act2);
		Thread.sleep(6000);
		String exp3="Create Ticket";
		//String act3=wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/h2")).getText();
		String act3=wd.findElement(By.xpath("//app-customerpage/h2")).getText();
		assertEquals(exp3,act3);
		
		//Create Ticket
		
		wd.findElement(By.name("fullname")).sendKeys("Rama Prasad");
		wd.findElement(By.name("address")).sendKeys("Shakti Khand,Indirapuram");
		wd.findElement(By.name("pincode")).sendKeys("201014");
		wd.findElement(By.name("phone")).sendKeys("77777");
		WebElement typeOfproblem=wd.findElement(By.name("problem"));
	       
	       Select p1=new Select(typeOfproblem);
	       
	       p1.selectByVisibleText("Cannot make a call But recieve a call");
	       
	       
	    wd.findElement(By.name("details")).sendKeys("xyz");
	    
	    Thread.sleep(4000);
	    
	       wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/form/button")).click();
	      Thread.sleep(2000); 
	       
	      /* String ticExp="Your ticket is generated successfully. Your ticket no is 1";
	       String ticAct=wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/b")).getText();
	       
	       assertEquals(ticExp,ticAct);*/
	       
	       //Verifying the result from Db if the ticket is created or not
	      
	      
	       Connection con=DBUtil.getConnection();
	       
	       String sql2="select * from users where username='Cust1'";
			PreparedStatement ps2=con.prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE);
			ResultSet rs2=ps2.executeQuery();
			rs2.next();
			int custId=rs2.getInt("userid");
			
	       String sql="select * from ticket where cust_id=?";
	       PreparedStatement ps=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE);
	       ps.setInt(1, custId);
	       ResultSet rs=ps.executeQuery();
	       String status="";
	       int ticketId=0;
	       
	       while(rs.next()) {
	    	   
	    	    status=rs.getString("status");
	    	    ticketId=rs.getInt("ticket_id");
	       }
	       
		  String statusExp="RAISED";
		  String statusAct=status;
		  
		  assertEquals(statusExp,statusAct);
		  
		  String ticExp="Your ticket is generated successfully. Your ticket no is "+ticketId;
		  String ticAct=wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/b")).getText();
		  
		  
		  
	}
	
	
	
	//View Tickets and their status
	
	@Test
	public void testB() throws ClassNotFoundException, SQLException, InterruptedException {
		
		wd.findElement(By.linkText("Login")).click();
		String act1=wd.findElement(By.xpath("/html/body/app-root/html/body/app-login/h1")).getText();
		String exp1="Login Page";
		
		assertEquals(exp1,act1);
		
		wd.findElement(By.name("usernm")).sendKeys("Cust1");
		wd.findElement(By.name("pwd")).sendKeys("cust1");
		Thread.sleep(3000);
		wd.findElement(By.tagName("button")).click();
		
		/*String act2=wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/h1")).getText();
		String exp2="Welcome to Customer Page";
		
		assertEquals(exp2,act2);*/
		Thread.sleep(5000);
		String exp3="View Tickets";
		//String act3=wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div[1]/h2")).getText();
		String act3=wd.findElement(By.xpath("//app-customerpage/div[1]/h2")).getText();
		assertEquals(exp3,act3);
		
		//Click on View Tickets button
		wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div[1]/button")).click();
		
		Thread.sleep(6000);
		
		List<WebElement> results=wd.findElements(By.className("one"));
		
		//Verifying the results from Database
		Connection con=DBUtil.getConnection();
		String sql2="select * from users where username='Cust1'";
		PreparedStatement ps2=con.prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE);
		
		ResultSet rs2=ps2.executeQuery();
		rs2.next();
		
		int custId=rs2.getInt("userid");
		
	       String sql="select * from ticket where cust_id=? ";
	       PreparedStatement ps=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE);
	       ps.setInt(1, custId);
	       ResultSet rs=ps.executeQuery();
	       //String status="";
	       int count=0;
	       
	       while(rs.next()) {
	    	   
	    	    count++;
	       }
	       
	       boolean pass=true;
	    	
	    	if(count!=results.size()) {
	    		
	    		System.out.println("Number of records in database and frontend are not same .Count:"+count);
	    		System.out.println("Results size: "+ results.size());
	    		pass=false;
	    	}
	    	
	    	for(int i=0;i<results.size();i++) {
	    		
	    		if(!results.get(i).getText().contains("Rama Prasad")) {
	    			
	    			System.out.println("Incorrect search results");
	    			pass=false;
	    		}
	    		
	    	}//End of for
	    	System.out.println("Results size: "+ results.size());
	    	System.out.println("Count:"+count);
	    	
             assertEquals(true,pass);
		
		
	           	
	}
	
	//Manager Tasks
	@Test
	public void testC() throws ClassNotFoundException, SQLException, InterruptedException{
	
		wd.findElement(By.linkText("Login")).click();
		String act1=wd.findElement(By.xpath("/html/body/app-root/html/body/app-login/h1")).getText();
		String exp1="Login Page";
		
		assertEquals(exp1,act1);
		
		wd.findElement(By.name("usernm")).sendKeys("Manager1");
		wd.findElement(By.name("pwd")).sendKeys("man1");
		Thread.sleep(3000);
		wd.findElement(By.tagName("button")).click();
		
		
		Thread.sleep(6000);
		String exp2="Welcome to Manager Page";
		//String act2=wd.findElement(By.xpath("/html/body/app-root/html/body/app-managerpage/h1[1]")).getText();
		String act2=wd.findElement(By.xpath("//app-managerpage/h1[1]")).getText();
		
		assertEquals(exp2,act2);
		Thread.sleep(2000);
		//Click on assign Tickets button
		wd.findElement(By.xpath("/html/body/app-root/html/body/app-managerpage/button")).click();
		
		//Getting ticket_id from db
		//Getting ticketid from db
				Connection con=DBUtil.getConnection();
				
				String sql2="select * from users where username='Cust1'";
				PreparedStatement ps2=con.prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE);
				
				ResultSet rs2=ps2.executeQuery();
				rs2.next();
				
				int custId=rs2.getInt("userid");

			       String sql="select * from ticket where cust_id=? ";
			       PreparedStatement ps=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE);
			       ps.setInt(1, custId);
			       ResultSet rs=ps.executeQuery();
			      // String status="";
			       String ticketid="";
			       
			       while(rs.next()) {
			    	//rs.next();   
			    	   // status=rs.getString("status");
			    	   ticketid=rs.getString("ticket_id");
			       }
			       
		System.out.println("TicketId:"+ ticketid);
		
		Thread.sleep(5000);
		
		List<WebElement> tickets=wd.findElements(By.className("three"));
		
		//for(int i=2;i<=tickets.size()+1;i++) {
		
		//Click on Assign To Engineer button
		int i=2; boolean found=false;
		do {
			
			if(/*wd.findElement(By.xpath("//app-managerpage/table/tr["+i+"]/td[2]")).getText().contains("201014")&& */ 
					wd.findElement(By.xpath("//app-managerpage/table/tr["+i+"]/td[1]")).getText().contains(ticketid)) {
		
			//wd.findElement(By.xpath("/html/body/app-root/html/body/app-managerpage/table/tr["+i+"]/button")).click();
			wd.findElement(By.xpath("//app-managerpage/table/tr["+i+"]/td[6]/button")).click();
			System.out.println("Clicked Assign To Engineer button");
			found=true;
			//Thread.sleep(4000);
			}
			i++;
			
			System.out.println("Found:"+ found);
		}while(found==false && i<=tickets.size()+1);
		
		
		Thread.sleep(5000);
		
		String exp3="List of Engineers";
		String act3=wd.findElement(By.xpath("/html/body/app-root/html/body/app-assign-to-engineer/div/h1")).getText();
		
		assertEquals(exp3,act3); 
		
		Thread.sleep(5000);
		
		List<WebElement> engrs=wd.findElements(By.name("engineers"));
		//List<WebElement> engrs=wd.findElements(By.className("four"));
		
		//Thread.sleep(3000);
		
		System.out.println("Engineers size:"+ engrs.size());
		
		for(int j=0;j<engrs.size();j++) {
			
			//System.out.println("Engineers:"+engrs.get(j).getText());
			System.out.println("Engineers:"+engrs.get(j).getAttribute("value"));
		}
		
		int b=0; boolean found2=false;
		do {
			
			if(engrs.get(b).getAttribute("value").contains("Engineer1")) {
			
				engrs.get(b).click();
				found2=true;
			}
			b++;
			//Thread.sleep(2000);
			
		}while(found2==false && b<engrs.size()); 
		
		/*int c=1; boolean found2=false;
		do {
			//if(wd.findElement(By.xpath("//app-assign-to-engineer/div/ul/li["+c+"]/label/input[@name='engineers']")).getText().contains("Engineer1")){
		Thread.sleep(20000);
			System.out.println("Element :"+ wd.findElement(By.xpath("/html/body/app-root/html/body/app-assign-to-engineer/div/ul/li["+c+"]/label/input[@name='engineers']")));
				if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-assign-to-engineer/div/ul/li["+c+"]/label/input")).getText().contains("Engineer1")){
					
			//if(wd.findElement(By.cssSelector("input[id='four']")).getText().contains("Engineer1")) {
			
			//if(wd.findElement(By.xpath("//input)))
			           wd.findElement(By.xpath("//app-assign-to-engineer/div/ul/li["+c+"]/label/input")).click();
			           found2=true;
			           System.out.println("Clicked on Engineer1");
			
			}
			c++;
			System.out.println("FoundEngineer:"+ found2);
		}while(found2==false && c<4 ); */
		
		//List<WebElement> engrs=wd.findElements(By.xpath("//input[@type='radio']"));
		
		//System.out.println("Engr Size:"+ engrs.size());
		
		//wd.findElement(By.name("engineers")).click();
		
		//Thread.sleep(5000);
		//Clicking on "Engineer1 radio button
	/* now wd.findElement(By.xpath("/html/body/app-root/html/body/app-assign-to-engineer/div/ul/li/label/input")).click(); */
		Thread.sleep(3000);
		
		//Click on Assign button
		wd.findElement(By.xpath("/html/body/app-root/html/body/app-assign-to-engineer/div/button")).click();
		
		Thread.sleep(3000);
		/*//Getting ticketid from db
		Connection con=DBUtil.getConnection();
	       String sql="select * from ticket where fullname='Rama Prasad' ";
	       PreparedStatement ps=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE);
	       ResultSet rs=ps.executeQuery();
	      // String status="";
	       int ticketid=0;
	       
	       while(rs.next()) {
	    	//rs.next();   
	    	   // status=rs.getString("status");
	    	   ticketid=rs.getInt("ticket_id");
	       }
	       */
		  
		String exp4="Ticket with id "+ticketid+" assigned to Engineer Engineer1";
		//String exp4="Ticket with id "+ticketid+" assigned to Engineer Engineer1"
		String act4=wd.findElement(By.xpath("/html/body/app-root/html/body/app-assign-to-engineer/b")).getText();
		
		Thread.sleep(3000);
		assertEquals(exp4,act4);
		
		//Click on Assign other Tickets to Engineers
		wd.findElement(By.xpath("/html/body/app-root/html/body/app-assign-to-engineer/button")).click();
		
		Thread.sleep(4000);
		String exp5="Welcome to Manager Page";
		String act5=wd.findElement(By.xpath("/html/body/app-root/html/body/app-managerpage/h1[1]")).getText();
		
		assertEquals(exp5,act5);
		
		
		//Click on "Assign Tickets" button.
		wd.findElement(By.xpath("/html/body/app-root/html/body/app-managerpage/button")).click();
		
		Thread.sleep(4000);
		
		//List of all RAISED(active) tickets
		List<WebElement> tickets1= wd.findElements(By.className("three"));
		
		boolean pass=false;
		
		for(int j=2;j<=tickets1.size()+1;j++)
		{
		     if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-managerpage/table/tr["+j+"]/td[1]")).getText().contains(ticketid)) {
		    	 
		    	//if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-managerpage/table/tr["+j+"]/text()")).contains("Engineer1")) {
		    	 if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-managerpage/table/tr["+j+"]")).getText().contains("Engineer1")) {
	                  pass=true;	    		
		    		System.out.println("Pass:"+ pass);
		    		
		    	}
		     }
			
			System.out.println("J:"+ j);
			
			
		}//end of for
		
		
		assertEquals(true,pass);
	}	
	
	
	
    @Test	
	public void testD() throws InterruptedException, ClassNotFoundException, SQLException {
    	
    	wd.findElement(By.linkText("Login")).click();
		String act1=wd.findElement(By.xpath("/html/body/app-root/html/body/app-login/h1")).getText();
		String exp1="Login Page";
		
		assertEquals(exp1,act1);
		
		wd.findElement(By.name("usernm")).sendKeys("Engineer1");
		wd.findElement(By.name("pwd")).sendKeys("eng1");
		Thread.sleep(3000);
		wd.findElement(By.tagName("button")).click();
	
		Thread.sleep(5000);
		
		String exp="Welcome to Engineer Dashboard Engineer1";
		String act=wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/h1")).getText();
		
		assertEquals(exp,act);
		
		
		//Click on "View the tickets assigned to you" button
		wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/button[1]")).click();
		
		//Getting ticket_id from db
				//Getting ticketid from db
						Connection con=DBUtil.getConnection();
						
						String sql2="select * from users where username='Cust1'";
						PreparedStatement ps2=con.prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE);
						
						ResultSet rs2=ps2.executeQuery();
						rs2.next();
						
						int custId=rs2.getInt("userid");

					       String sql="select * from ticket where cust_id=? ";
					       PreparedStatement ps=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE);
					       ps.setInt(1, custId);
					       ResultSet rs=ps.executeQuery();
					      // String status="";
					       String ticketid="";
					       
					       while(rs.next()) {
					    	//rs.next();   
					    	   // status=rs.getString("status");
					    	   ticketid=rs.getString("ticket_id");
					       }
					       
				System.out.println("TicketId:"+ ticketid);
				
	             Thread.sleep(5000);
	             
	             List<WebElement> tickets=wd.findElements(By.className("four"));
	             
	             int i=2; boolean found=false;
	     		do {
	     			
	     			if(/*wd.findElement(By.xpath("//app-managerpage/table/tr["+i+"]/td[2]")).getText().contains("201014")&& */ 
	     					wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/table/tr["+i+"]/td[1]")).getText().contains(ticketid)) {
	     		
	     			//wd.findElement(By.xpath("/html/body/app-root/html/body/app-managerpage/table/tr["+i+"]/button")).click();
	     			wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/table/tr["+i+"]/button[1]")).click();
	     			System.out.println("Clicked Change Status button");
	     			found=true;
	     			//Thread.sleep(4000);
	     			}
	     			i++;
	     			
	     			System.out.println("Found:"+ found);
	     		}while(found==false && i<=tickets.size()+1);
	     		
	     		
	     		
	     		Thread.sleep(6000);
	     		
	     		WebElement chstatus=wd.findElement(By.name("status"));
			       
			       Select ch=new Select(chstatus);
			       
			       ch.selectByVisibleText("WIP");
			       Thread.sleep(4000);
			       
			       //Click on Change Status button
			       wd.findElement(By.xpath("/html/body/app-root/html/body/app-change-status/form/button")).click();
	             
	             Thread.sleep(5000);
	             
	             String exp2="Status assigned as WIP";
	             String act2=wd.findElement(By.xpath("/html/body/app-root/html/body/app-change-status/b")).getText();
	             
	             assertEquals(exp2,act2);
	             
	             //Click on "set status of other tickets" button
	             wd.findElement(By.xpath("/html/body/app-root/html/body/app-change-status/button")).click();
	             
	             Thread.sleep(6000);
	             
	             String exp3="Welcome to Engineer Dashboard Engineer1";
	             String act3=wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/h1")).getText();
	             
	             assertEquals(exp3,act3);
	             
	             //Click on "View the tickets assigned to u" button
	             wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/button[1]")).click();
	             
	             Thread.sleep(6000);
	             
	             List<WebElement> tickets1=wd.findElements(By.className("four"));
	             boolean pass=false;
	             
	             for(int j=2;j<=tickets1.size()+1;j++) {
	            	 
	            	 if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/table/tr["+j+"]/td[1]")).getText().contains(ticketid)) {
	            
	            	     if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/table/tr["+j+"]/td[6]")).getText().contains("WIP")) {
	            	    	 
	            	    	 pass=true;
	            	    	 System.out.println("Pass:"+ pass);
	            	    	 
	            	    	 
	            	     }
	            	 
	            	 
	            	 
	            	}//End of outer if
	            	 
	            	 System.out.println("J:"+ j);
	            	 
	           }//End of for
	
	             assertEquals(true,pass);
	             
	             
	             
	             
	             //Change Status to RESOLVED
                List<WebElement> tickets2=wd.findElements(By.className("four"));
	             
	             int k=2; boolean found2=false;
	     		do {
	     			
	     			if(/*wd.findElement(By.xpath("//app-managerpage/table/tr["+i+"]/td[2]")).getText().contains("201014")&& */ 
	     					wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/table/tr["+k+"]/td[1]")).getText().contains(ticketid)) {
	     		
	     			//wd.findElement(By.xpath("/html/body/app-root/html/body/app-managerpage/table/tr["+i+"]/button")).click();
	     			wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/table/tr["+k+"]/button[1]")).click();
	     			System.out.println("Clicked Change Status button");
	     			found=true;
	     			//Thread.sleep(4000);
	     			}
	     			k++;
	     			
	     			System.out.println("Found:"+ found);
	     		}while(found2==false && k<=tickets2.size()+1);
	             
	             
	     		
                Thread.sleep(6000);
	     		
	     		WebElement chstatus2=wd.findElement(By.name("status"));
			       
			       Select ch2=new Select(chstatus2);
			       
			       ch2.selectByVisibleText("RESOLVED");
			       Thread.sleep(4000);
			       
			       //Click on Change Status button
			       wd.findElement(By.xpath("/html/body/app-root/html/body/app-change-status/form/button")).click();
	             
	             Thread.sleep(5000);
	             
	             String exp4="Status assigned as RESOLVED";
	             String act4=wd.findElement(By.xpath("/html/body/app-root/html/body/app-change-status/b")).getText();
	             
	             assertEquals(exp4,act4);
	             
	             
	           //Click on "set status of other tickets" button
	             wd.findElement(By.xpath("/html/body/app-root/html/body/app-change-status/button")).click();
	             
	             Thread.sleep(6000);
	             
	             String exp5="Welcome to Engineer Dashboard Engineer1";
	             String act5=wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/h1")).getText();
	             
	             assertEquals(exp5,act5);
	             
	             //Click on "View the tickets assigned to u" button
	             wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/button[1]")).click();
	             
	             Thread.sleep(6000);
	             
	             List<WebElement> tickets3=wd.findElements(By.className("four"));
	             boolean pass2=false;
	             
	             for(int j=2;j<=tickets3.size()+1;j++) {
	            	 
	            	 if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/table/tr["+j+"]/td[1]")).getText().contains(ticketid)) {
	            
	            	     if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-engineerpage/table/tr["+j+"]/td[6]")).getText().contains("RESOLVED")) {
	            	    	 
	            	    	 pass2=true;
	            	    	 System.out.println("Pass:"+ pass2);
	            	    	 
	            	    	 
	            	     }
	            	 
	            	 
	            	 
	            	}//End of outer if
	            	 
	            	 System.out.println("J:"+ j);
	            	 
	           }//End of for
	
	             assertEquals(true,pass2);
	             
	             
	             
	}//End of TestD

    
    //Submitting feedback and Register Complaint
    @Test
    public void testE() throws InterruptedException, ClassNotFoundException, SQLException {
    	
          //Login by Customer
    	wd.findElement(By.linkText("Login")).click();
    	
    	Thread.sleep(5000);
    	
    	String exp="Login Page";
    	String act=wd.findElement(By.xpath("/html/body/app-root/html/body/app-login/h1")).getText();
    	
    	assertEquals(exp,act);
    	
    	wd.findElement(By.name("usernm")).sendKeys("Cust1");
		wd.findElement(By.name("pwd")).sendKeys("cust1");
		Thread.sleep(3000);
		wd.findElement(By.tagName("button")).click();
	
		Thread.sleep(5000);
		
		String exp2="Welcome to Customer Page";
		String act2=wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/h1")).getText();
		
		assertEquals(exp2,act2);
		
		Thread.sleep(3000);
		String exp3="View Tickets";
		String act3=wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div[1]/h2")).getText();
		
		assertEquals(exp3,act3);
		
		//Click on View Tickets button
          wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div[1]/button")).click();
		
		Thread.sleep(6000);
		
		//List<WebElement> results=wd.findElements(By.className("one"));
		
		
		//Get the ticketid of last ticket
		Connection con=DBUtil.getConnection();
		
		String sql2="select * from users where username='Cust1'";
		PreparedStatement ps2=con.prepareStatement(sql2,ResultSet.TYPE_SCROLL_INSENSITIVE);
		
		ResultSet rs2=ps2.executeQuery();
		rs2.next();
		
		int custId=rs2.getInt("userid");

	       String sql="select * from ticket where cust_id=? ";
	       PreparedStatement ps=con.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE);
	       ps.setInt(1, custId);
	       ResultSet rs=ps.executeQuery();
	      // String status="";
	       String ticketid="";
	       
	       while(rs.next()) {
	    	//rs.next();   
	    	   // status=rs.getString("status");
	    	   ticketid=rs.getString("ticket_id");
	       }
	       
              System.out.println("TicketId:"+ ticketid);

              Thread.sleep(5000);
              
              List<WebElement> results=wd.findElements(By.className("one"));
              
              for(int i=2;i<=results.size()+1;i++) {
            	  
            	  
            	  if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div[2]/table/tr["+i+"]/td[2]")).getText().contains(ticketid)) {
            		 
            		  if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div[2]/table/tr["+i+"]/td[5]")).getText().contains("RESOLVED")) {
            			 
            			  wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div[2]/table/tr["+i+"]/button[1]")).click();
            			  System.out.println("Clicked on Submit Feedback button");
            		  }
            		  
            	  }//End of outer if
            	  
              }//End of for
              
           Thread.sleep(6000);
           
           String exp4="Submit Feedback for ticket with id "+ticketid;
           String act4=wd.findElement(By.xpath("/html/body/app-root/html/body/app-submit-feedbk/h1")).getText();
              
		   assertEquals(exp4,act4);
		   
		   //Write Feedback
		   wd.findElement(By.name("feedback")).sendKeys("Excellent");
		   
		   Thread.sleep(5000);
		   //Click on "Submit" button
		   wd.findElement(By.xpath("/html/body/app-root/html/body/app-submit-feedbk/form/button")).click();
		   
		   Thread.sleep(6000);
		 
		   String exp5="Feedback Submitted Successfully";
		   String act5=wd.findElement(By.xpath("/html/body/app-root/html/body/app-submit-feedbk/b")).getText();
		
		   assertEquals(exp5,act5);
		   
		   //Verifying from db feedback
		   Connection con2=DBUtil.getConnection();
			
			String sql4="select * from users where username='Cust1'";
			PreparedStatement ps4=con2.prepareStatement(sql4,ResultSet.TYPE_SCROLL_INSENSITIVE);
			
			ResultSet rs4=ps4.executeQuery();
			rs4.next();
			
			int custId2=rs4.getInt("userid");

		       String sql3="select * from ticket where cust_id=? ";
		       PreparedStatement ps3=con2.prepareStatement(sql3,ResultSet.TYPE_SCROLL_INSENSITIVE);
		       ps3.setInt(1, custId2);
		       ResultSet rs3=ps3.executeQuery();
		      // String status="";
		       String feedback="";
		       String ticketid2="";
		       
		       while(rs3.next()) {
		    	//rs.next();   
		    	   // status=rs.getString("status");
		    	   feedback=rs3.getString("feedback");
		    	   ticketid2=rs3.getString("ticket_id");
		       }
		       
	              System.out.println("TicketId:"+ ticketid2);
	              System.out.println("Feedback:"+ feedback);
		   
		   String exp6="Excellent";
		   String act6=feedback;
		   
		   assertEquals(exp6,act6);
            		   
		   
		   //Register Complaint
		   
		   //Click on "Go Back to Customer Dashboard
		   wd.findElement(By.xpath("/html/body/app-root/html/body/app-submit-feedbk/button")).click();
		   
		   Thread.sleep(6000);
		   
		   //Click on View Tickets button
		   wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div/button")).click();
		   
		   Thread.sleep(8000);
		   //Clicking on Register Complaint button
		   List<WebElement> results2=wd.findElements(By.className("one"));
		   System.out.println("Results2 size:"+ results2.size());
           
           for(int i=2;i<=results2.size()+1;i++) {
         	  
         	  
         	  if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div[2]/table/tr["+i+"]/td[2]")).getText().contains(ticketid)) {
         		 
         		  if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div[2]/table/tr["+i+"]/td[5]")).getText().contains("RESOLVED")) {
         			 
         			  wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div[2]/table/tr["+i+"]/button[2]")).click();
         			 //html/body/app-root/html/body/app-customerpage/div[2]/table/tr[48]/button[2]
         			  //html/body/app-root/html/body/app-customerpage/div[2]/table/tr[2]/button[2]
         			  System.out.println("Clicked on Register Complaint button");
         		  }
         		  
         	  }//End of outer if
         	  
         	  System.out.println("i:"+ i);
           }//End of for
           
        Thread.sleep(6000);
        
        String exp7="Register Complaint against ticket id "+ticketid;
        String act7=wd.findElement(By.xpath("/html/body/app-root/html/body/app-register-complaint/h1")).getText();
        
        assertEquals(exp7,act7);
        
        
        WebElement typeOfProblem=wd.findElement(By.name("typeOfProblem"));
		   
        Select tp=new Select(typeOfProblem);
        
        tp.selectByVisibleText("Can make calls, but cannot recieve calls");
        
        Thread.sleep(4000);
        
        wd.findElement(By.name("details")).sendKeys("jkl");
        //Click on Register Complaint button
        
        wd.findElement(By.xpath("/html/body/app-root/html/body/app-register-complaint/form/button")).click();
        
        Thread.sleep(6000);
        
        int ticketid3=Integer.parseInt(ticketid)+1;
        String exp8="Your ticket is generated successfully with id "+(ticketid3);
        String act8=wd.findElement(By.xpath("/html/body/app-root/html/body/app-register-complaint/b")).getText();
        
        assertEquals(exp8,act8);
        
        //Click on "Go to Customer Dashboard" button
        wd.findElement(By.xpath("/html/body/app-root/html/body/app-register-complaint/button")).click();
        
        Thread.sleep(6000);
        
        String exp9="View Tickets";
        String act9=wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div/h2")).getText();
        
        
        assertEquals(exp9,act9);
        
        
        //Click on "View Tickets" button
        wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div/button")).click();
        
        
        Thread.sleep(6000);
        
        List<WebElement> tics=wd.findElements(By.className("one"));
        
        String ticketid4=Integer.toString(ticketid3);
        
        System.out.println("Ticket4:"+ ticketid4);
        
        boolean pass=false;
        
        for(int i=2;i<=tics.size()+1;i++) {
        	
        if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div[2]/table/tr["+i+"]/td[2]")).getText().contains(ticketid4)) {
        	//html/body/app-root/html/body/app-customerpage/div[2]/table/tr[2]/td[2]
        	
        	System.out.println("Got ticket with id"+ ticketid4);
        	
        	  if(wd.findElement(By.xpath("/html/body/app-root/html/body/app-customerpage/div[2]/table/tr["+i+"]/td[3]")).getText().contains("can make calls ,but cannot recieve calls")) {
        		 //html/body/app-root/html/body/app-customerpage/div[2]/table/tr[27]/td[3]
        		  pass=true;
        		  System.out.println("Pass:"+ pass);
        		  System.out.println("i inside if:"+ i);
        		  
        	  }
        }//End of outer if
        
        System.out.println("i:"+ i);
      
        }//End of for
        
        assertEquals(true,pass);
        
        
		   
		   
    }//End of testE()

    
    
    
}
