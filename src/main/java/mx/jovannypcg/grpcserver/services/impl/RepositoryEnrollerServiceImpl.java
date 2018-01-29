package mx.jovannypcg.grpcserver.services.impl;

import io.grpc.stub.StreamObserver;
import mx.jovannypcg.grpcserver.amqp.Publisher;
import mx.jovannypcg.grpcserver.messages.EnrollmentResponse;
import mx.jovannypcg.grpcserver.messages.Repository;
import mx.jovannypcg.grpcserver.services.RepositoryEnrollerServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RepositoryEnrollerServiceImpl extends RepositoryEnrollerServiceGrpc.RepositoryEnrollerServiceImplBase {
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryEnrollerServiceImpl.class);

    private Publisher repositoryPublisher;

    public RepositoryEnrollerServiceImpl(Publisher repositoryPublisher) {
        this.repositoryPublisher = repositoryPublisher;
    }

    @Override
    public void enroll(Repository request, StreamObserver<EnrollmentResponse> responseObserver) {
        LOGGER.info("Receiving enrollment request from: \n{}", request);

        repositoryPublisher.publish(request);

        EnrollmentResponse response = EnrollmentResponse
                .newBuilder()
                .setAck(true)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
