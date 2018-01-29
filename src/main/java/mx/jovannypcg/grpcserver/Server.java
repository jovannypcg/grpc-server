package mx.jovannypcg.grpcserver;

import io.grpc.ServerBuilder;
import mx.jovannypcg.grpcserver.services.impl.GreeterImpl;

import java.io.IOException;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getSimpleName());

    private static final int PORT = 5000;

    private io.grpc.Server server;

    public static void main(String[] args) throws IOException, InterruptedException {
        final Server server = new Server();
        server.start();
        server.blockUntilShutdown();
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    private void start() throws IOException {
        server = ServerBuilder
                .forPort(PORT)
                .addService(new GreeterImpl())
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

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }
}
