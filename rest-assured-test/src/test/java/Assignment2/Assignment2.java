package Assignment2;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.*;
import static org.hamcrest.Matchers.*;



public class Assignment2 {	

	@BeforeMethod
	public void setup() {
		RestAssured.baseURI="https://restful-booker.herokuapp.com";
	}

	@Test(priority=0)
	public void getBookingDetails() {
		String response =
				given().
				contentType(ContentType.JSON).
				when().
				get("booking").
				then().
				assertThat().
				statusCode(200).
				extract().
				response().asString();
	}

	@Test(priority=1)
	public void getBookingDetailsById() {
		String bookingid= "354";
		
		Response rs = given().
				contentType(ContentType.JSON).
				when().
				get("booking/"+bookingid);

		JsonPath jp = rs.jsonPath();
		String fname= jp.getString("firstname");
		System.out.println("First Name:"+fname);

		String lname= jp.getString("lastname");
		System.out.println("Last Name:"+lname);

		String totalprice= jp.getString("totalprice");
		System.out.println("Total Price:"+totalprice);

		String depositpaid= jp.getString("depositpaid");
		System.out.println("Deposit Paid:"+depositpaid);

		String additionalneeds= jp.getString("additionalneeds");
		System.out.println("Additional Needs:"+additionalneeds);

		String checkin= jp.getString("bookingdates.'checkin'");
		System.out.println("Checkin Date:"+checkin);

		String checkout= jp.getString("bookingdates.'checkout'");
		System.out.println("Checkout Date:"+checkout);
		
		String response =
				given().
				contentType(ContentType.JSON).
				when().
				log().
				all().
				get("booking/"+bookingid).
				then().
				log().
				body().
				assertThat().
				statusCode(200).
				assertThat().
				body("firstname",equalTo("6c257ff9-9e9b-4f61-8b61-50d04869e5c1")).
				assertThat().
				body("lastname",equalTo("23fbdd6d-11c1-4821-ba8c-4ac6b2873bc6")).
				assertThat().
				body("totalprice",equalTo(150)).
				assertThat().
				body("depositpaid",equalTo(true)).
				assertThat().
				body("additionalneeds",equalTo("breakfast")).
				assertThat().
				body("bookingdates.'checkin'",equalTo("2022-10-28")).
				assertThat().
				body("bookingdates.'checkout'",equalTo("2022-11-02")).
				extract().asString();
	}
}
