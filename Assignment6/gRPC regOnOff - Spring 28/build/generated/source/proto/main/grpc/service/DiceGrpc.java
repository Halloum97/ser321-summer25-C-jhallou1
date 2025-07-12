package service;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.49.1)",
    comments = "Source: services/dice.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class DiceGrpc {

  private DiceGrpc() {}

  public static final String SERVICE_NAME = "services.Dice";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<service.SingleRequest,
      service.DiceResponse> getSinglerollMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "singleroll",
      requestType = service.SingleRequest.class,
      responseType = service.DiceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.SingleRequest,
      service.DiceResponse> getSinglerollMethod() {
    io.grpc.MethodDescriptor<service.SingleRequest, service.DiceResponse> getSinglerollMethod;
    if ((getSinglerollMethod = DiceGrpc.getSinglerollMethod) == null) {
      synchronized (DiceGrpc.class) {
        if ((getSinglerollMethod = DiceGrpc.getSinglerollMethod) == null) {
          DiceGrpc.getSinglerollMethod = getSinglerollMethod =
              io.grpc.MethodDescriptor.<service.SingleRequest, service.DiceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "singleroll"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.SingleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.DiceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DiceMethodDescriptorSupplier("singleroll"))
              .build();
        }
      }
    }
    return getSinglerollMethod;
  }

  private static volatile io.grpc.MethodDescriptor<service.TripleRequest,
      service.DiceResponse> getTriplerollMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "tripleroll",
      requestType = service.TripleRequest.class,
      responseType = service.DiceResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<service.TripleRequest,
      service.DiceResponse> getTriplerollMethod() {
    io.grpc.MethodDescriptor<service.TripleRequest, service.DiceResponse> getTriplerollMethod;
    if ((getTriplerollMethod = DiceGrpc.getTriplerollMethod) == null) {
      synchronized (DiceGrpc.class) {
        if ((getTriplerollMethod = DiceGrpc.getTriplerollMethod) == null) {
          DiceGrpc.getTriplerollMethod = getTriplerollMethod =
              io.grpc.MethodDescriptor.<service.TripleRequest, service.DiceResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "tripleroll"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.TripleRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  service.DiceResponse.getDefaultInstance()))
              .setSchemaDescriptor(new DiceMethodDescriptorSupplier("tripleroll"))
              .build();
        }
      }
    }
    return getTriplerollMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DiceStub>() {
        @java.lang.Override
        public DiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DiceStub(channel, callOptions);
        }
      };
    return DiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DiceBlockingStub>() {
        @java.lang.Override
        public DiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DiceBlockingStub(channel, callOptions);
        }
      };
    return DiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<DiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<DiceFutureStub>() {
        @java.lang.Override
        public DiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new DiceFutureStub(channel, callOptions);
        }
      };
    return DiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class DiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void singleroll(service.SingleRequest request,
        io.grpc.stub.StreamObserver<service.DiceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSinglerollMethod(), responseObserver);
    }

    /**
     */
    public void tripleroll(service.TripleRequest request,
        io.grpc.stub.StreamObserver<service.DiceResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getTriplerollMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getSinglerollMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.SingleRequest,
                service.DiceResponse>(
                  this, METHODID_SINGLEROLL)))
          .addMethod(
            getTriplerollMethod(),
            io.grpc.stub.ServerCalls.asyncUnaryCall(
              new MethodHandlers<
                service.TripleRequest,
                service.DiceResponse>(
                  this, METHODID_TRIPLEROLL)))
          .build();
    }
  }

  /**
   */
  public static final class DiceStub extends io.grpc.stub.AbstractAsyncStub<DiceStub> {
    private DiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DiceStub(channel, callOptions);
    }

    /**
     */
    public void singleroll(service.SingleRequest request,
        io.grpc.stub.StreamObserver<service.DiceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSinglerollMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void tripleroll(service.TripleRequest request,
        io.grpc.stub.StreamObserver<service.DiceResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getTriplerollMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DiceBlockingStub extends io.grpc.stub.AbstractBlockingStub<DiceBlockingStub> {
    private DiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public service.DiceResponse singleroll(service.SingleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSinglerollMethod(), getCallOptions(), request);
    }

    /**
     */
    public service.DiceResponse tripleroll(service.TripleRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getTriplerollMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DiceFutureStub extends io.grpc.stub.AbstractFutureStub<DiceFutureStub> {
    private DiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new DiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.DiceResponse> singleroll(
        service.SingleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSinglerollMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<service.DiceResponse> tripleroll(
        service.TripleRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getTriplerollMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SINGLEROLL = 0;
  private static final int METHODID_TRIPLEROLL = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_SINGLEROLL:
          serviceImpl.singleroll((service.SingleRequest) request,
              (io.grpc.stub.StreamObserver<service.DiceResponse>) responseObserver);
          break;
        case METHODID_TRIPLEROLL:
          serviceImpl.tripleroll((service.TripleRequest) request,
              (io.grpc.stub.StreamObserver<service.DiceResponse>) responseObserver);
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

  private static abstract class DiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return service.DiceProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Dice");
    }
  }

  private static final class DiceFileDescriptorSupplier
      extends DiceBaseDescriptorSupplier {
    DiceFileDescriptorSupplier() {}
  }

  private static final class DiceMethodDescriptorSupplier
      extends DiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (DiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DiceFileDescriptorSupplier())
              .addMethod(getSinglerollMethod())
              .addMethod(getTriplerollMethod())
              .build();
        }
      }
    }
    return result;
  }
}
