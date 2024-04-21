package grpcholamundostream.servidor;

import com.proto.saludo.Holamundo.SaludoRequest;
import com.proto.saludo.Holamundo.SaludoResponse;
import com.proto.saludo.SaludoServiceGrpc;

import io.grpc.stub.StreamObserver;

import java.util.Scanner;


public class ServidorImpl extends SaludoServiceGrpc.SaludoServiceImplBase {
    @Override
    public void saludo(SaludoRequest request, StreamObserver<SaludoResponse> responseObserver) {
        SaludoResponse respuesta = SaludoResponse.newBuilder().setResultado("Hola " + request.getNombre()).build();

        responseObserver.onNext(respuesta);
        responseObserver.onCompleted();
    }

    @Override
    public void saludoStream(SaludoRequest request, StreamObserver<SaludoResponse> responseObserver) {
        for (int i = 0; i <= 10; i++) {
            SaludoResponse respuesta = SaludoResponse.newBuilder()
                        .setResultado("Hola " + request.getNombre() + " por " + i + " vez.").build();

            responseObserver.onNext(respuesta);
        }

        responseObserver.onCompleted();
    }

    @Override
    public void enviarArchivoStream(SaludoRequest request, StreamObserver<SaludoResponse> responseObserver) {
        try (Scanner scanner = new Scanner(Servidor.class.getResourceAsStream(request.getNombre()))) {

            while (scanner.hasNextLine()) {
                //System.out.println(scanner.nextLine());
                SaludoResponse respuesta = SaludoResponse.newBuilder()
                            .setResultado(scanner.nextLine()).build();

                responseObserver.onNext(respuesta);
            }

            responseObserver.onCompleted();
        }
        
    }
}
