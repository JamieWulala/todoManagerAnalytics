import io.restassured.RestAssured;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class testTodos {

    protected Process proc;
    private int SIZE = 196830;

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
        //System.out.println("started");
    }

    public void tearDown() throws InterruptedException {
        proc.destroy();
        //System.out.println("destroyed");
        Thread.sleep(400);
    }

    @Test
    public void testAddTodo() throws InterruptedException, IOException {

        String testCaseName = "addTodos";
        String fileName = "output_"+testCaseName;
        int size = SIZE;

        long T1startTime, uT1startTime;
        long T1endTime, uT1endTime;
        long T2startTime, uT2startTime;
        long T2endTime, uT2endTime;

        File csv = new File(fileName);
        if (csv.exists()){
            csv.delete();
            csv = new File(fileName);
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));

        Map<String, String> requestBody = new HashMap();
        requestBody.put("title", "categoryTitle");

        for (int i = 10; i <= size; i *=3) { //control base element
            T1startTime = System.currentTimeMillis();
            uT1startTime = Instant.now().getEpochSecond(); //T1 -> set up time
            setup();
            for (int j = 0; j < i; j++) { //set up base element to I
                given()
                        .body(requestBody)
                        .contentType("application/json").
                when().
                        post("/todos").
                then().
                        statusCode(201);
                Thread.sleep(1);
            }//set up base element

            T2startTime = System.currentTimeMillis(); //T2 -> one operation response
            //request code
            uT2startTime =Instant.now().getEpochSecond();
            int statusCode =
                given()
                        .body(requestBody)
                        .contentType("application/json").
                when().
                        post("/todos").
                then().
                        extract().
                        statusCode();
            //end request
            T2endTime = System.currentTimeMillis();
            uT2endTime = Instant.now().getEpochSecond(); //T2end -> request time
            tearDown();
            T1endTime = System.currentTimeMillis();//T1end -> after tear down
            uT1endTime =Instant.now().getEpochSecond();
            assert (statusCode == 201);

            String input = testCaseName+ ", "+i+", "+T1startTime+ ", "+T1endTime+ ", "+uT1startTime+ ", "+uT1endTime+ ", "+T2startTime+ ", "+T2endTime+ ", "+uT2startTime+ ", "+uT2endTime;
            System.out.println(input);
            bw.write(input);
            bw.newLine();

        }
        bw.close();
    }

    @Test
    public void testUpdateTodo() throws InterruptedException, IOException {

        String testCaseName = "updateTodos";
        String fileName = "output_"+testCaseName;
        int size = SIZE;
        int expectCode = 200;

        long T1startTime, uT1startTime;
        long T1endTime, uT1endTime;
        long T2startTime, uT2startTime;
        long T2endTime, uT2endTime;

        File csv = new File(fileName);
        if (csv.exists()){
            csv.delete();
            csv = new File(fileName);
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));

        Map<String, String> requestBody = new HashMap();
        requestBody.put("title", "categoryTitle");

        for (int i = 10; i <= size; i *=3) { //control base element
            T1startTime = System.currentTimeMillis();
            uT1startTime = Instant.now().getEpochSecond(); //T1 -> set up time
            setup();
            for (int j = 0; j < i; j++) { //set up base element to I
                given()
                        .body(requestBody)
                        .contentType("application/json").
                        when().
                        post("/todos").
                        then().
                        statusCode(201);
                Thread.sleep(1);
            }//set up base element

            requestBody.put("description", "some description");

            T2startTime = System.currentTimeMillis(); //T2 -> one operation response
            //request code
            uT2startTime =Instant.now().getEpochSecond();
            int statusCode =
                    given()
                            .body(requestBody)
                            .contentType("application/json").
                            when().
                    post("/todos/2").
                            then().
                            extract().
                            statusCode();
            //end request
            T2endTime = System.currentTimeMillis();
            uT2endTime = Instant.now().getEpochSecond(); //T2end -> request time
            tearDown();
            T1endTime = System.currentTimeMillis();//T1end -> after tear down
            uT1endTime =Instant.now().getEpochSecond();
            assert (expectCode == statusCode);

            String input = testCaseName+ ", "+i+", "+T1startTime+ ", "+T1endTime+ ", "+uT1startTime+ ", "+uT1endTime+ ", "+T2startTime+ ", "+T2endTime+ ", "+uT2startTime+ ", "+uT2endTime;
            System.out.println(input);
            bw.write(input);
            bw.newLine();
        }
        bw.close();
    }

    @Test
    public void testDeleteTodo() throws InterruptedException, IOException {

        String testCaseName = "deleteTodos";
        String fileName = "output_"+testCaseName;
        int size = SIZE;
        int expectCode = 200;

        long T1startTime, uT1startTime;
        long T1endTime, uT1endTime;
        long T2startTime, uT2startTime;
        long T2endTime, uT2endTime;

        File csv = new File(fileName);
        if (csv.exists()){
            csv.delete();
            csv = new File(fileName);
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));

        Map<String, String> requestBody = new HashMap();
        requestBody.put("title", "categoryTitle");

        for (int i = 10; i <= size; i *=3) { //control base element
            T1startTime = System.currentTimeMillis();
            uT1startTime = Instant.now().getEpochSecond(); //T1 -> set up time
            setup();
            for (int j = 0; j < i; j++) { //set up base element to I
                given()
                        .body(requestBody)
                        .contentType("application/json").
                        when().
                post("/todos").
                        then().
                        statusCode(201);
                Thread.sleep(1);
            }//set up base element

            requestBody.put("description", "some description");

            T2startTime = System.currentTimeMillis(); //T2 -> one operation response
            //request code
            uT2startTime =Instant.now().getEpochSecond();
            int statusCode =
                    given().
                            when().
                    delete("/todos/2").
                            then().
                    extract().
                            statusCode();
            //end request
            T2endTime = System.currentTimeMillis();
            uT2endTime = Instant.now().getEpochSecond(); //T2end -> request time
            tearDown();
            T1endTime = System.currentTimeMillis();//T1end -> after tear down
            uT1endTime =Instant.now().getEpochSecond();
            assert (expectCode == statusCode);

            String input = testCaseName+ ", "+i+", "+T1startTime+ ", "+T1endTime+ ", "+uT1startTime+ ", "+uT1endTime+ ", "+T2startTime+ ", "+T2endTime+ ", "+uT2startTime+ ", "+uT2endTime;
            System.out.println(input);
            bw.write(input);
            bw.newLine();
        }
        bw.close();
    }
}
