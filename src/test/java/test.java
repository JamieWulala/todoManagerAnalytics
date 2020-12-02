import io.restassured.RestAssured;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class test {

    protected Process proc;

    @Before
    public void setup() {
        //Set base URI
        baseURI = "http://localhost:4567";
        //start the application
        try {
            //System.out.println("Starting application......");
            proc = Runtime.getRuntime().exec("java -jar runTodoManagerRestAPI-1.5.5.jar");
            //System.out.println("------Application started------");
            InputStream in = proc.getInputStream();
            InputStream err = proc.getErrorStream();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void testAddTodo() throws InterruptedException {
        String testCaseName = "addTodo";

        int size = 10000;
        long startTime;
        long endTime;
        long elapsed;
        Map<String, String> requestBody = new HashMap();
        requestBody.put("title", "categoryTitle");

        for (int i = 0; i < size ; i++){
            startTime = System.currentTimeMillis();
            given()
                    .body(requestBody)
                    .contentType("application/json").
            when().
                    post("/todos").
            then().
                    statusCode(201);
            endTime = System.currentTimeMillis();
            elapsed = endTime - startTime;
            System.out.println(i+" Elapsed time is: "+elapsed+"ms");
        }
        //Test code here


    }

    @After
    public void tearDown(){
        //proc.destroy();
    }
}
