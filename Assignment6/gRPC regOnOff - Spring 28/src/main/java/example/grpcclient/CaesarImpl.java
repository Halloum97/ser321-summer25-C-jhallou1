package example.grpcclient;

import io.grpc.stub.StreamObserver;
import service.CaesarGrpc;
import service.SaveReq;
import service.SaveRes;
import service.PasswordReq;
import service.PasswordRes;
import service.PasswordList;
import com.google.protobuf.Empty;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CaesarImpl extends CaesarGrpc.CaesarImplBase {
    private final Map<String, String> passwordStore = new HashMap<>();
    private final Map<String, Integer> keyStore = new HashMap<>();
    private final Random rand = new Random();

    private String encryptCaesar(String input, int key) {
        StringBuilder result = new StringBuilder();
        for (char ch : input.toCharArray()) {
            result.append((char)(ch + key));
        }
        return result.toString();
    }

    private String decryptCaesar(String input, int key) {
        StringBuilder result = new StringBuilder();
        for (char ch : input.toCharArray()) {
            result.append((char)(ch - key));
        }
        return result.toString();
    }

    @Override
    public void encrypt(SaveReq request, StreamObserver<SaveRes> responseObserver) {
        String name = request.getName();
        String password = request.getPassword();

        if (name.isEmpty() || password.isEmpty()) {
            SaveRes res = SaveRes.newBuilder().setOk(false).setError("Name and password must not be empty.").build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
            return;
        }

        int key = rand.nextInt(5) + 1; // Caesar key from 1 to 5
        String encrypted = encryptCaesar(password, key);

        passwordStore.put(name, encrypted);
        keyStore.put(name, key);

        SaveRes res = SaveRes.newBuilder().setOk(true).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void decrypt(PasswordReq request, StreamObserver<PasswordRes> responseObserver) {
        String name = request.getName();

        if (!passwordStore.containsKey(name)) {
            PasswordRes res = PasswordRes.newBuilder().setOk(false).setError("Password not found.").build();
            responseObserver.onNext(res);
            responseObserver.onCompleted();
            return;
        }

        String encrypted = passwordStore.get(name);
        int key = keyStore.get(name);
        String decrypted = decryptCaesar(encrypted, key);

        PasswordRes res = PasswordRes.newBuilder().setOk(true).setPassword(decrypted).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }

    @Override
    public void listPasswords(Empty request, StreamObserver<PasswordList> responseObserver) {
        PasswordList res = PasswordList.newBuilder().addAllPassList(passwordStore.keySet()).build();
        responseObserver.onNext(res);
        responseObserver.onCompleted();
    }
}
