import com.google.protobuf.Empty;
import example.grpcclient.Client;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.json.JSONObject;
import service.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;




public class ServerTest {

    ManagedChannel channel;
    private EchoGrpc.EchoBlockingStub blockingStub;
    private JokeGrpc.JokeBlockingStub blockingStub2;
    private DiceGrpc.DiceBlockingStub diceStub;
    private CaesarGrpc.CaesarBlockingStub passwordStub;



    @org.junit.Before
    public void setUp() throws Exception {
        // assuming default port and localhost for our testing, make sure Node runs on this port
        channel = ManagedChannelBuilder.forTarget("localhost:8000").usePlaintext().build();

        blockingStub = EchoGrpc.newBlockingStub(channel);
        blockingStub2 = JokeGrpc.newBlockingStub(channel);
        diceStub = DiceGrpc.newBlockingStub(channel);
        passwordStub = CaesarGrpc.newBlockingStub(channel);

    }

    @org.junit.After
    public void close() throws Exception {
        channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);

    }


    @Test
    public void parrot() {
        // success case
        ClientRequest request = ClientRequest.newBuilder().setMessage("test").build();
        ServerResponse response = blockingStub.parrot(request);
        assertTrue(response.getIsSuccess());
        assertEquals("test", response.getMessage());

        // error cases
        request = ClientRequest.newBuilder().build();
        response = blockingStub.parrot(request);
        assertFalse(response.getIsSuccess());
        assertEquals("No message provided", response.getError());

        request = ClientRequest.newBuilder().setMessage("").build();
        response = blockingStub.parrot(request);
        assertFalse(response.getIsSuccess());
        assertEquals("No message provided", response.getError());
    }

    // For this test the server needs to be started fresh AND the list of jokes needs to be the initial list
    @Test
    public void joke() {
        // getting first joke
        JokeReq request = JokeReq.newBuilder().setNumber(1).build();
        JokeRes response = blockingStub2.getJoke(request);
        assertEquals(1, response.getJokeCount());
        assertEquals("Did you hear the rumor about butter? Well, I'm not going to spread it!", response.getJoke(0));

        // getting next 2 jokes
        request = JokeReq.newBuilder().setNumber(2).build();
        response = blockingStub2.getJoke(request);
        assertEquals(2, response.getJokeCount());
        assertEquals("What do you call someone with no body and no nose? Nobody knows.", response.getJoke(0));
        assertEquals("I don't trust stairs. They're always up to something.", response.getJoke(1));

        // getting 2 more but only one more on server
        request = JokeReq.newBuilder().setNumber(2).build();
        response = blockingStub2.getJoke(request);
        assertEquals(2, response.getJokeCount());
        assertEquals("How do you get a squirrel to like you? Act like a nut.", response.getJoke(0));
        assertEquals("I am out of jokes...", response.getJoke(1));

        // trying to get more jokes but out of jokes
        request = JokeReq.newBuilder().setNumber(2).build();
        response = blockingStub2.getJoke(request);
        assertEquals(1, response.getJokeCount());
        assertEquals("I am out of jokes...", response.getJoke(0));

        // trying to add joke without joke field
        JokeSetReq req2 = JokeSetReq.newBuilder().build();
        JokeSetRes res2 = blockingStub2.setJoke(req2);
        assertFalse(res2.getOk());

        // trying to add empty joke
        req2 = JokeSetReq.newBuilder().setJoke("").build();
        res2 = blockingStub2.setJoke(req2);
        assertFalse(res2.getOk());

        // adding a new joke (well word)
        req2 = JokeSetReq.newBuilder().setJoke("whoop").build();
        res2 = blockingStub2.setJoke(req2);
        assertTrue(res2.getOk());

        // should have the new "joke" now and return it
        request = JokeReq.newBuilder().setNumber(1).build();
        response = blockingStub2.getJoke(request);
        assertEquals(1, response.getJokeCount());
        assertEquals("whoop", response.getJoke(0));
    }
    

    @Test
    public void testSingleRollDice() {
        SingleRequest req = SingleRequest.newBuilder().setType(6).setNum(4).build();
        DiceResponse res = diceStub.singleroll(req);
        assertEquals(4, res.getDiceCount());
        for (int roll : res.getDiceList()) {
            assertTrue(roll >= 1 && roll <= 6);
        }
    }

    @Test
    public void testTripleRollDice() {
        TripleRequest req = TripleRequest.newBuilder().setType1(4).setType2(6).setType3(8).setNum(6).build();
        DiceResponse res = diceStub.tripleroll(req);
        assertEquals(6, res.getDiceCount());
    }
    
    @Test
    public void testSaveAndRetrievePassword() {
        SaveReq save = SaveReq.newBuilder().setName("testUser").setPassword("abc123").build();
        SaveRes result = passwordStub.encrypt(save);
        assertTrue(result.getOk());

        PasswordReq req = PasswordReq.newBuilder().setName("testUser").build();
        PasswordRes res = passwordStub.decrypt(req);
        assertTrue(res.getOk());
        assertEquals("abc123", res.getPassword());
    }

    @Test
    public void testRetrieveMissingPassword() {
        PasswordReq req = PasswordReq.newBuilder().setName("notExist").build();
        PasswordRes res = passwordStub.decrypt(req);
        assertFalse(res.getOk());
        assertFalse(res.getError().isEmpty());
    }

    @Test
    public void testListPasswords() {
        SaveReq save = SaveReq.newBuilder().setName("listTest").setPassword("xyz").build();
        passwordStub.encrypt(save);

        PasswordList list = passwordStub.listPasswords(Empty.newBuilder().build());
        assertTrue(list.getPassListList().contains("listTest"));
    }


}