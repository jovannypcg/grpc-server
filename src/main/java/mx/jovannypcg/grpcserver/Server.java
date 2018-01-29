package mx.jovannypcg.grpcserver;

import io.grpc.ServerBuilder;

import java.io.IOException;
import java.util.logging.Logger;

public class Server {
    private static final Logger LOGGER = Logger.getLogger(Server.class.getSimpleName());

    private static final int PORT = 5000;

    private io.grpc.Server server;

    private void start() throws IOException {
        server = ServerBuilder
                .forPort(PORT)
                .build()
                .start();

        LOGGER.info("gRPC server started. Listening on " + PORT);
    }
}
