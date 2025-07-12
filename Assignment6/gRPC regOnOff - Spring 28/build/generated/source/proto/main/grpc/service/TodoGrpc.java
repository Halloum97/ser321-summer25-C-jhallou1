package service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.49.1)",
    comments = "Source: services/todo.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TodoGrpc {

  private TodoGrpc() {}

  public static final String SERVICE_NAME = "services.Todo";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<service.TaskRequest,
      service.TaskResponse> getAddTaskMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AddTask",
      requestType = service.TaskRequest.class,
      responseType = service.TaskResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.TaskRequest,
      service.TaskResponse> getAddTaskMethod() {
    io.grpc.MethodDescriptor<service.TaskRequest, service.TaskResponse> getAddTaskMethod;
    if ((getAddTaskMethod = TodoGrpc.getAddTaskMethod) == null) {
      synchronized (TodoGrpc.class) {
        if ((getAddTaskMethod = TodoGrpc.getAddTaskMethod) == null) {
          TodoGrpc.getAddTaskMethod = getAddTaskMethod =
              io.grpc.MethodDescriptor.<service.TaskRequest, service.TaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AddTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.TaskRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.TaskResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TodoMethodDescriptorSupplier("AddTask"))
              .build();
        }
      }
    }
    return getAddTaskMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      service.TaskList> getListTasksMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListTasks",
      requestType = com.google.protobuf.Empty.class,
      responseType = service.TaskList.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      service.TaskList> getListTasksMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, service.TaskList> getListTasksMethod;
    if ((getListTasksMethod = TodoGrpc.getListTasksMethod) == null) {
      synchronized (TodoGrpc.class) {
        if ((getListTasksMethod = TodoGrpc.getListTasksMethod) == null) {
          TodoGrpc.getListTasksMethod = getListTasksMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, service.TaskList>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListTasks"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.TaskList.getDefaultInstance()))
              .setSchemaDescriptor(new TodoMethodDescriptorSupplier("ListTasks"))
              .build();
        }
      }
    }
    return getListTasksMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.RemoveRequest,
      service.TaskResponse> getRemoveTaskMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "RemoveTask",
      requestType = service.RemoveRequest.class,
      responseType = service.TaskResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.RemoveRequest,
      service.TaskResponse> getRemoveTaskMethod() {
    io.grpc.MethodDescriptor<service.RemoveRequest, service.TaskResponse> getRemoveTaskMethod;
    if ((getRemoveTaskMethod = TodoGrpc.getRemoveTaskMethod) == null) {
      synchronized (TodoGrpc.class) {
        if ((getRemoveTaskMethod = TodoGrpc.getRemoveTaskMethod) == null) {
          TodoGrpc.getRemoveTaskMethod = getRemoveTaskMethod =
              io.grpc.MethodDescriptor.<service.RemoveRequest, service.TaskResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "RemoveTask"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.RemoveRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.TaskResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TodoMethodDescriptorSupplier("RemoveTask"))
              .build();
        }
      }
    }
    return getRemoveTaskMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TodoStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TodoStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TodoStub>() {
        @java.lang.Override
        public TodoStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TodoStub(channel, callOptions);
        }
      };
    return TodoStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TodoBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TodoBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TodoBlockingStub>() {
        @java.lang.Override
        public TodoBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TodoBlockingStub(channel, callOptions);
        }
      };
    return TodoBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TodoFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TodoFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TodoFutureStub>() {
        @java.lang.Override
        public TodoFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TodoFutureStub(channel, callOptions);
        }
      };
    return TodoFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class TodoImplBase implements io.grpc.BindableService {

    /**
     */
    public void addTask(service.TaskRequest request,
        io.grpc.stub.StreamObserver<service.TaskResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddTaskMethod(), responseObserver);
    }

    /**
     */
    public void listTasks(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<service.TaskList> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListTasksMethod(), responseObserver);
    }

    /**
     */
    public void removeTask(service.RemoveRequest request,
        io.grpc.stub.StreamObserver<service.TaskResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRemoveTaskMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddTaskMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.TaskRequest,
                service.TaskResponse>(
                  this, METHODID_ADD_TASK)))
          .addMethod(
            getListTasksMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                service.TaskList>(
                  this, METHODID_LIST_TASKS)))
          .addMethod(
            getRemoveTaskMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.RemoveRequest,
                service.TaskResponse>(
                  this, METHODID_REMOVE_TASK)))
          .build();
    }
  }

  /**
   */
  public static final class TodoStub extends io.grpc.stub.AbstractAsyncStub<TodoStub> {
    private TodoStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TodoStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TodoStub(channel, callOptions);
    }

    /**
     */
    public void addTask(service.TaskRequest request,
        io.grpc.stub.StreamObserver<service.TaskResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddTaskMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listTasks(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<service.TaskList> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListTasksMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void removeTask(service.RemoveRequest request,
        io.grpc.stub.StreamObserver<service.TaskResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRemoveTaskMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class TodoBlockingStub extends io.grpc.stub.AbstractBlockingStub<TodoBlockingStub> {
    private TodoBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TodoBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TodoBlockingStub(channel, callOptions);
    }

    /**
     */
    public service.TaskResponse addTask(service.TaskRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddTaskMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.TaskList listTasks(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListTasksMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.TaskResponse removeTask(service.RemoveRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRemoveTaskMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class TodoFutureStub extends io.grpc.stub.AbstractFutureStub<TodoFutureStub> {
    private TodoFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TodoFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TodoFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.TaskResponse> addTask(
        service.TaskRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddTaskMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.TaskList> listTasks(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListTasksMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.TaskResponse> removeTask(
        service.RemoveRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRemoveTaskMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD_TASK = 0;
  private static final int METHODID_LIST_TASKS = 1;
  private static final int METHODID_REMOVE_TASK = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final TodoImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(TodoImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD_TASK:
          serviceImpl.addTask((service.TaskRequest) request,
              (io.grpc.stub.StreamObserver<service.TaskResponse>) responseObserver);
          break;
        case METHODID_LIST_TASKS:
          serviceImpl.listTasks((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<service.TaskList>) responseObserver);
          break;
        case METHODID_REMOVE_TASK:
          serviceImpl.removeTask((service.RemoveRequest) request,
              (io.grpc.stub.StreamObserver<service.TaskResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class TodoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TodoBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return service.TodoProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Todo");
    }
  }

  private static final class TodoFileDescriptorSupplier
      extends TodoBaseDescriptorSupplier {
    TodoFileDescriptorSupplier() {}
  }

  private static final class TodoMethodDescriptorSupplier
      extends TodoBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    TodoMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (TodoGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TodoFileDescriptorSupplier())
              .addMethod(getAddTaskMethod())
              .addMethod(getListTasksMethod())
              .addMethod(getRemoveTaskMethod())
              .build();
        }
      }
    }
    return result;
  }
}
