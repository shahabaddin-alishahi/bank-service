package energizeglobalservices.bankservice.config.feign;

import energizeglobalservices.bankservice.config.exception.InternalServerError;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.BufferedReader;
import java.io.IOException;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            BufferedReader reader = new BufferedReader(response.body().asReader());
            StringBuilder responseBuilder = new StringBuilder();
            reader.lines().forEach(responseBuilder::append);
            return new FeignException(response.status(), responseBuilder.toString());
        } catch (IOException e) {
            return new InternalServerError();

        }
    }
}
