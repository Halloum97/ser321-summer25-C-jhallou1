package service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.49.1)",
    comments = "Source: services/zoo.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ZooGrpc {

  private ZooGrpc() {}

  public static final String SERVICE_NAME = "services.Zoo";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<service.AddAnimalRequest,
      service.AddAnimalResponse> getAddMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "add",
      requestType = service.AddAnimalRequest.class,
      responseType = service.AddAnimalResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.AddAnimalRequest,
      service.AddAnimalResponse> getAddMethod() {
    io.grpc.MethodDescriptor<service.AddAnimalRequest, service.AddAnimalResponse> getAddMethod;
    if ((getAddMethod = ZooGrpc.getAddMethod) == null) {
      synchronized (ZooGrpc.class) {
        if ((getAddMethod = ZooGrpc.getAddMethod) == null) {
          ZooGrpc.getAddMethod = getAddMethod =
              io.grpc.MethodDescriptor.<service.AddAnimalRequest, service.AddAnimalResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "add"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.AddAnimalRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.AddAnimalResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ZooMethodDescriptorSupplier("add"))
              .build();
        }
      }
    }
    return getAddMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.SpeakRequest,
      service.SpeakResponse> getSpeakMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "speak",
      requestType = service.SpeakRequest.class,
      responseType = service.SpeakResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.SpeakRequest,
      service.SpeakResponse> getSpeakMethod() {
    io.grpc.MethodDescriptor<service.SpeakRequest, service.SpeakResponse> getSpeakMethod;
    if ((getSpeakMethod = ZooGrpc.getSpeakMethod) == null) {
      synchronized (ZooGrpc.class) {
        if ((getSpeakMethod = ZooGrpc.getSpeakMethod) == null) {
          ZooGrpc.getSpeakMethod = getSpeakMethod =
              io.grpc.MethodDescriptor.<service.SpeakRequest, service.SpeakResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "speak"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.SpeakRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.SpeakResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ZooMethodDescriptorSupplier("speak"))
              .build();
        }
      }
    }
    return getSpeakMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      service.SpeakResponse> getSpeakAllMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "speakAll",
      requestType = com.google.protobuf.Empty.class,
      responseType = service.SpeakResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.google.protobuf.Empty,
      service.SpeakResponse> getSpeakAllMethod() {
    io.grpc.MethodDescriptor<com.google.protobuf.Empty, service.SpeakResponse> getSpeakAllMethod;
    if ((getSpeakAllMethod = ZooGrpc.getSpeakAllMethod) == null) {
      synchronized (ZooGrpc.class) {
        if ((getSpeakAllMethod = ZooGrpc.getSpeakAllMethod) == null) {
          ZooGrpc.getSpeakAllMethod = getSpeakAllMethod =
              io.grpc.MethodDescriptor.<com.google.protobuf.Empty, service.SpeakResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "speakAll"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.SpeakResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ZooMethodDescriptorSupplier("speakAll"))
              .build();
        }
      }
    }
    return getSpeakAllMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ZooStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZooStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZooStub>() {
        @java.lang.Override
        public ZooStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZooStub(channel, callOptions);
        }
      };
    return ZooStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ZooBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZooBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZooBlockingStub>() {
        @java.lang.Override
        public ZooBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZooBlockingStub(channel, callOptions);
        }
      };
    return ZooBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ZooFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ZooFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ZooFutureStub>() {
        @java.lang.Override
        public ZooFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ZooFutureStub(channel, callOptions);
        }
      };
    return ZooFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class ZooImplBase implements io.grpc.BindableService {

    /**
     */
    public void add(service.AddAnimalRequest request,
        io.grpc.stub.StreamObserver<service.AddAnimalResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAddMethod(), responseObserver);
    }

    /**
     */
    public void speak(service.SpeakRequest request,
        io.grpc.stub.StreamObserver<service.SpeakResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSpeakMethod(), responseObserver);
    }

    /**
     */
    public void speakAll(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<service.SpeakResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSpeakAllMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getAddMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.AddAnimalRequest,
                service.AddAnimalResponse>(
                  this, METHODID_ADD)))
          .addMethod(
            getSpeakMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.SpeakRequest,
                service.SpeakResponse>(
                  this, METHODID_SPEAK)))
          .addMethod(
            getSpeakAllMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                com.google.protobuf.Empty,
                service.SpeakResponse>(
                  this, METHODID_SPEAK_ALL)))
          .build();
    }
  }

  /**
   */
  public static final class ZooStub extends io.grpc.stub.AbstractAsyncStub<ZooStub> {
    private ZooStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ZooStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZooStub(channel, callOptions);
    }

    /**
     */
    public void add(service.AddAnimalRequest request,
        io.grpc.stub.StreamObserver<service.AddAnimalResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAddMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void speak(service.SpeakRequest request,
        io.grpc.stub.StreamObserver<service.SpeakResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSpeakMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void speakAll(com.google.protobuf.Empty request,
        io.grpc.stub.StreamObserver<service.SpeakResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSpeakAllMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class ZooBlockingStub extends io.grpc.stub.AbstractBlockingStub<ZooBlockingStub> {
    private ZooBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ZooBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZooBlockingStub(channel, callOptions);
    }

    /**
     */
    public service.AddAnimalResponse add(service.AddAnimalRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAddMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.SpeakResponse speak(service.SpeakRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSpeakMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.SpeakResponse speakAll(com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSpeakAllMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class ZooFutureStub extends io.grpc.stub.AbstractFutureStub<ZooFutureStub> {
    private ZooFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ZooFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ZooFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.AddAnimalResponse> add(
        service.AddAnimalRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAddMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.SpeakResponse> speak(
        service.SpeakRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSpeakMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.SpeakResponse> speakAll(
        com.google.protobuf.Empty request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSpeakAllMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_ADD = 0;
  private static final int METHODID_SPEAK = 1;
  private static final int METHODID_SPEAK_ALL = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ZooImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ZooImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ADD:
          serviceImpl.add((service.AddAnimalRequest) request,
              (io.grpc.stub.StreamObserver<service.AddAnimalResponse>) responseObserver);
          break;
        case METHODID_SPEAK:
          serviceImpl.speak((service.SpeakRequest) request,
              (io.grpc.stub.StreamObserver<service.SpeakResponse>) responseObserver);
          break;
        case METHODID_SPEAK_ALL:
          serviceImpl.speakAll((com.google.protobuf.Empty) request,
              (io.grpc.stub.StreamObserver<service.SpeakResponse>) responseObserver);
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

  private static abstract class ZooBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ZooBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return service.ZooProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Zoo");
    }
  }

  private static final class ZooFileDescriptorSupplier
      extends ZooBaseDescriptorSupplier {
    ZooFileDescriptorSupplier() {}
  }

  private static final class ZooMethodDescriptorSupplier
      extends ZooBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ZooMethodDescriptorSupplier(String methodName) {
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
      synchronized (ZooGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ZooFileDescriptorSupplier())
              .addMethod(getAddMethod())
              .addMethod(getSpeakMethod())
              .addMethod(getSpeakAllMethod())
              .build();
        }
      }
    }
    return result;
  }
}
