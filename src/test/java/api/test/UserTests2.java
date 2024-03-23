package api.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.UserEndpoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	
	Faker faker;
	User userPayload;
	public Logger logger;
	
	@BeforeClass
	public void setUp()
	{
		faker=new Faker();
		userPayload=new User();
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//Log
		logger=LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 1)
	public void testPostUser()
	{
		logger.info("************CreateUser*************");
		Response response=UserEndpoints2.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 2)
	public void testgetUserByName()
	{
		logger.info("*************GetUser**********************");
		Response response=UserEndpoints2.getUser(this.userPayload.getUsername());
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(), 200);
	}
	
	@Test(priority = 3)
	public void testUpdateUserByName()
	{
		logger.info("********************UpdateUser*****************");
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response=UserEndpoints2.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().body();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		
		//After updating data
		
		Response afterUpdatingData=UserEndpoints2.getUser(this.userPayload.getUsername());
		Assert.assertEquals(afterUpdatingData.getStatusCode(), 200);
	}
	
	@Test(priority = 4)
	public void testDeleteUserByName()
	{
		logger.info("**************DeleteUser*******************");
		Response response=UserEndpoints2.deleteUser(this.userPayload.getUsername());
		Assert.assertEquals(response.getStatusCode(), 200);
	}

}
