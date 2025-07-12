package example.grpcclient;

import io.grpc.stub.StreamObserver;
import service.TodoGrpc;
import service.TaskRequest;
import service.TaskResponse;
import service.TaskList;
import service.RemoveRequest;
import com.google.protobuf.Empty;

import java.util.ArrayList;
import java.util.List;

public class TodoImpl extends TodoGrpc.TodoImplBase {

    private final List<String> tasks = new ArrayList<>();

    @Override
    public void addTask(TaskRequest request, StreamObserver<TaskResponse> responseObserver) {
        String desc = request.getDescription().trim();
        if (desc.isEmpty()) {
            responseObserver.onNext(TaskResponse.newBuilder()
                .setOk(false)
                .setError("Task description cannot be empty")
                .build());
        } else {
            tasks.add(desc);
            responseObserver.onNext(TaskResponse.newBuilder().setOk(true).build());
        }
        responseObserver.onCompleted();
    }

    @Override
    public void listTasks(Empty request, StreamObserver<TaskList> responseObserver) {
        TaskList list = TaskList.newBuilder().addAllTasks(tasks).build();
        responseObserver.onNext(list);
        responseObserver.onCompleted();
    }

    @Override
    public void removeTask(RemoveRequest request, StreamObserver<TaskResponse> responseObserver) {
        String desc = request.getDescription().trim();
        if (tasks.remove(desc)) {
            responseObserver.onNext(TaskResponse.newBuilder().setOk(true).build());
        } else {
            responseObserver.onNext(TaskResponse.newBuilder()
                .setOk(false)
                .setError("Task not found")
                .build());
        }
        responseObserver.onCompleted();
    }
}
