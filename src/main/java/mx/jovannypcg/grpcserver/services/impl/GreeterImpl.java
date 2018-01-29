package mx.jovannypcg.grpcserver.services.impl;

import io.grpc.stub.StreamObserver;
import mx.jovannypcg.grpcserver.messages.HelloReply;
import mx.jovannypcg.grpcserver.messages.HelloRequest;
import mx.jovannypcg.grpcserver.services.GreeterGrpc;

public class GreeterImpl extends GreeterGrpc.GreeterImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply
                .newBuilder()
                .setMessage("Hello " + request.getName())
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
