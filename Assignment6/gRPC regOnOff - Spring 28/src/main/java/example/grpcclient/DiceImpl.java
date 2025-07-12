package example.grpcclient;

import io.grpc.stub.StreamObserver;
import service.DiceGrpc;
import service.DiceResponse;
import service.SingleRequest;
import service.TripleRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiceImpl extends DiceGrpc.DiceImplBase {
    private final Random random = new Random();

    @Override
    public void singleroll(SingleRequest req, StreamObserver<DiceResponse> responseObserver) {
        int type = req.getType();
        int num = req.getNum();

        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            results.add(random.nextInt(type) + 1); // simulate dice roll (1 to type)
        }

        DiceResponse response = DiceResponse.newBuilder().addAllDice(results).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void tripleroll(TripleRequest req, StreamObserver<DiceResponse> responseObserver) {
        int num = req.getNum();
        int[] types = {req.getType1(), req.getType2(), req.getType3()};

        List<Integer> results = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int type = types[i % 3]; // cycle through dice types
            results.add(random.nextInt(type) + 1);
        }

        DiceResponse response = DiceResponse.newBuilder().addAllDice(results).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
