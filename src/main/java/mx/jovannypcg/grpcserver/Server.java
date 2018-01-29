package mx.jovannypcg.grpcserver;

import io.grpc.ServerBuilder;
import mx.jovannypcg.grpcserver.services.RepositoryEnrollerServiceGrpc;
import mx.jovannypcg.grpcserver.services.impl.RepositoryEnrollerServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getSimpleName());

    private static final int PORT = 5000;

    private io.grpc.Server server;

    private RepositoryEnrollerServiceImpl repositoryEnrollerService;

    public Server(RepositoryEnrollerServiceImpl repositoryEnrollerService) {
        this.repositoryEnrollerService = repositoryEnrollerService;
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public void start() throws IOException {
        server = ServerBuilder
                .forPort(PORT)
                .addService(repositoryEnrollerService)
                .build()
                .start();

        LOGGER.info("gRPC server started. Listening on " + PORT);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                Server.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
}
