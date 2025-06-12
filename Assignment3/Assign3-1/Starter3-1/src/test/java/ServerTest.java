import org.junit.Test;
import static org.junit.Assert.*;
import org.json.JSONObject;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ServerTest {

    Socket sock;
    OutputStream out;
    ObjectOutputStream os;

    DataInputStream in;


    // Establishing a connection to the server, make sure you start the server on localhost and 8888
    @org.junit.Before
    public void setUp() throws Exception {
        // Establish connection to server and create in/out streams
        sock = new Socket("localhost", 8888); // connect to host and socket

        // get output channel
        out = sock.getOutputStream();

        // create an object output writer (Java only)
        os = new ObjectOutputStream(out);

        // setup input stream
        in = new DataInputStream(sock.getInputStream());
    }

    @org.junit.After
    public void close() throws Exception {
        if (out != null)  out.close();
        if (sock != null) sock.close();
    }

    @Test
    public void addRequest() throws IOException {
        // create a correct req for server
        JSONObject req = new JSONObject();
        req.put("type", "add");
        req.put("num1", "1");
        req.put("num2", "2");

        // write the whole message
        os.writeObject(req.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();

        String i = (String) in.readUTF();
        // assuming I get correct JSON back
        JSONObject res = new JSONObject(i);

        // test response
        assertTrue(res.getBoolean("ok"));
        assertEquals("add", res.getString("type"));
        assertEquals(3, res.getInt("result"));

        // Wrong request to server num2 missing
        JSONObject req2 = new JSONObject();
        req2.put("type", "add");
        req2.put("num1", "1");
        // write the whole message
        os.writeObject(req2.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();

        i = (String) in.readUTF();
        // assuming I get correct JSON back
        res = new JSONObject(i);

        System.out.println(res);

        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("Field num2 does not exist in request", res.getString("message"));

        // Wrong request to server num1 missing
        JSONObject req3 = new JSONObject();
        req3.put("type", "add");
        req3.put("num2", "1");
        // write the whole message
        os.writeObject(req3.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();

        i = (String) in.readUTF();
        // assuming I get correct JSON back
        res = new JSONObject(i);

        System.out.println(res);

        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("Field num1 does not exist in request", res.getString("message"));

        // Wrong request to server num1 num2 missing
        JSONObject req4 = new JSONObject();
        req4.put("type", "add");
        // write the whole message
        os.writeObject(req4.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();

        i = (String) in.readUTF();
        // assuming I get correct JSON back
        res = new JSONObject(i);

        System.out.println(res);

        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("Field num1 does not exist in request", res.getString("message"));

        // Wrong request to server num2 missing
        JSONObject req5 = new JSONObject();
        req5.put("type", "add");
        req5.put("num1", "hello");
        req5.put("num2", "2");
        // write the whole message
        os.writeObject(req5.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();

        i = (String) in.readUTF();
        // assuming I get correct JSON back
        res = new JSONObject(i);

        System.out.println(res);

        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("Field num1/num2 needs to be of type: int", res.getString("message"));
    }

    @Test
    public void echoRequest() throws IOException {
        // valid request with data
        JSONObject req1 = new JSONObject();
        req1.put("type", "echo");
        req1.put("data", "gimme this back!");
        // write the whole message
        os.writeObject(req1.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();
        String i = (String) in.readUTF();
        // assuming I get correct JSON back
        JSONObject res = new JSONObject(i);
        // test response
        assertTrue(res.getBoolean("ok"));
        assertEquals("echo", res.getString("type"));
        assertEquals("Here is your echo: gimme this back!", res.getString("echo"));

        // Invalid request - no data sent
        JSONObject req2 = new JSONObject();
        req2.put("type", "echo");
        // write the whole message
        os.writeObject(req2.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();
        i = (String) in.readUTF();
        // assuming I get correct JSON back
        res = new JSONObject(i);
        System.out.println(res);
        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("Field data does not exist in request", res.getString("message"));
    }

    @Test
    public void addManyRequest() throws IOException {
        // create a correct req for server
        JSONObject req = new JSONObject();
        req.put("type", "addmany");
        List<String> myList = Arrays.asList(
                "12",
                "15",
                "111",
                "42"
        );
        req.put("nums", myList);
        // write the whole message
        os.writeObject(req.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();
        String i = (String) in.readUTF();
        // assuming I get correct JSON back
        JSONObject res = new JSONObject(i);
        // test response
        assertTrue(res.getBoolean("ok"));
        assertEquals("addmany", res.getString("type"));
        assertEquals(180, res.getInt("result"));

        // Invalid request to server
        JSONObject req2 = new JSONObject();
        req2.put("type", "addmany");
        myList = Arrays.asList(
                "two",
                "15",
                "111",
                "42"
        );
        req2.put("nums", myList);
        // write the whole message
        os.writeObject(req2.toString());
        // make sure it wrote and doesn't get cached in a buffer
        os.flush();
        i = (String) in.readUTF();
        // assuming I get correct JSON back
        res = new JSONObject(i);
        System.out.println(res);
        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("Values in array need to be ints", res.getString("message"));
    }

    @Test
    public void notJSON() throws IOException {
        // create an incorrect req for server
        os.writeObject("a");

        String i = (String) in.readUTF();
        // assuming I get correct JSON back
        JSONObject res = new JSONObject(i);

        // test response
        assertFalse(res.getBoolean("ok"));
        assertEquals("req not JSON", res.getString("message"));

        // calling the other test to make sure server continues to work and the "continue" does what it is supposed to do
        addRequest();
    }

    @Test
    public void tempConvertRequest() throws IOException {
      // 1) Valid conversion: 32°F → 0°C
      JSONObject req = new JSONObject();
      req.put("type", "tempconvert");
      req.put("value", 32.0);
      req.put("fromUnit", "F");
      req.put("toUnit", "C");

      os.writeObject(req.toString());
      os.flush();
      String resp = in.readUTF();
      JSONObject res = new JSONObject(resp);

      assertTrue(res.getBoolean("ok"));
      assertEquals("tempconvert", res.getString("type"));
      // allow tiny rounding error
      assertEquals(0.0, res.getDouble("result"), 1e-6);

      // 2) Missing field: no 'toUnit'
      JSONObject req2 = new JSONObject();
      req2.put("type", "tempconvert");
      req2.put("value", 100.0);
      req2.put("fromUnit", "C");
      // omit toUnit
      os.writeObject(req2.toString());
      os.flush();
      res = new JSONObject(in.readUTF());
      assertFalse(res.getBoolean("ok"));
      assertEquals("Field toUnit does not exist in request",
                   res.getString("message"));

      // 3) Bad type: value is a string
      JSONObject req3 = new JSONObject();
      req3.put("type", "tempconvert");
      req3.put("value", "hot");
      req3.put("fromUnit", "C");
      req3.put("toUnit", "F");
      os.writeObject(req3.toString());
      os.flush();
      res = new JSONObject(in.readUTF());
      assertFalse(res.getBoolean("ok"));
      assertEquals("Field value needs to be of type: double",
                   res.getString("message"));

      // 4) Out-of-bounds: below –20 °C (e.g. –100 °C)
      JSONObject req4 = new JSONObject();
      req4.put("type", "tempconvert");
      req4.put("value", -100.0);
      req4.put("fromUnit", "C");
      req4.put("toUnit", "F");
      os.writeObject(req4.toString());
      os.flush();
      res = new JSONObject(in.readUTF());
      assertFalse(res.getBoolean("ok"));
      assertEquals("value out of bounds",
                   res.getString("message"));
    }

    @Test
    public void dadJokeRequest() throws IOException {
    // 1) Add a joke
    JSONObject addReq = new JSONObject()
      .put("type", "dadjoke")
      .put("action", "add")
      .put("joke", "Why did the chicken cross the road?");
    os.writeObject(addReq.toString());
    os.flush();
    JSONObject addRes = new JSONObject(in.readUTF());
    assertTrue(addRes.getBoolean("ok"));
    assertEquals("dadjoke", addRes.getString("type"));
    String msg = addRes.getString("message");
    assertTrue(msg.startsWith("Joke added, this is joke number "));
    int id = Integer.parseInt(msg.replaceAll("\\D+",""));  // extract the assigned ID

    // 2) Get a random joke
    JSONObject getReq = new JSONObject()
      .put("type", "dadjoke")
      .put("action", "get");
    os.writeObject(getReq.toString());
    os.flush();
    JSONObject getRes = new JSONObject(in.readUTF());
    assertTrue(getRes.getBoolean("ok"));
    assertEquals("dadjoke", getRes.getString("type"));
    assertEquals(id, getRes.getInt("jokeID"));
    assertEquals("Why did the chicken cross the road?", getRes.getString("joke"));
    assertEquals(5.0, getRes.getDouble("rating"), 0.0001);

    // 3) Rate the joke
    JSONObject rateReq = new JSONObject()
      .put("type", "dadjoke")
      .put("action", "rate")
      .put("jokeID", id)
      .put("rating", "3");   // new rating
    os.writeObject(rateReq.toString());
    os.flush();
    JSONObject rateRes = new JSONObject(in.readUTF());
    assertTrue(rateRes.getBoolean("ok"));
    assertEquals("dadjoke", rateRes.getString("type"));
    assertEquals(id, rateRes.getInt("jokeID"));
    assertEquals("Why did the chicken cross the road?", rateRes.getString("joke"));
    // expected new average = (5.0 + 3.0)/2 = 4.0
    assertEquals(4.0, rateRes.getDouble("rating"), 0.0001);
}


}