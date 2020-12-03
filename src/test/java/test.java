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

public class test {

    protected Process proc;

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
        System.out.println("started");
    }

    public void tearDown() throws InterruptedException {
        proc.destroy();
        System.out.println("destroyed");
        Thread.sleep(400);
    }

    @Test
    public void testAddTodo2() throws InterruptedException, IOException {
        int size = 100;
        long T1startTime, uT1startTime;
        long T1endTime, uT1endTime;
        long T2startTime, uT2startTime;
        long T2endTime, uT2endTime;

        String testCaseName = "addTodos";

        File csv = new File("./output.csv");
        if (csv.exists()){
            csv.delete();
            csv = new File("./output.csv");
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true));

        Map<String, String> requestBody = new HashMap();
        requestBody.put("title", "categoryTitle");

        for (int i = 1; i < size; i *=10) { //control base element
            T1startTime = System.currentTimeMillis();
            uT1startTime = Instant.now().getEpochSecond();
            setup();
            for (int j = 0; j < i; j++) {
                given()
                        .body(requestBody)
                        .contentType("application/json").
                when().
                        post("/todos").
                then().
                        statusCode(201);
            }//set up base element
            T2startTime = System.currentTimeMillis();
            uT2startTime =Instant.now().getEpochSecond();
            given()
                    .body(requestBody)
                    .contentType("application/json").
            when().
                    post("/todos").
            then().
                    statusCode(201);

            T2endTime = System.currentTimeMillis();
            uT2endTime = Instant.now().getEpochSecond();
            tearDown();
            T1endTime = System.currentTimeMillis();
            uT1endTime =Instant.now().getEpochSecond();

            String input = testCaseName+ ", "+i+", "+T1startTime+ ", "+T1endTime+ ", "+uT1startTime+ ", "+uT1endTime+ ", "+T2startTime+ ", "+T2endTime+ ", "+uT2startTime+ ", "+uT2endTime;
            System.out.println(input);
            bw.write(input);
            bw.newLine();

        }
        bw.close();
    }

}
