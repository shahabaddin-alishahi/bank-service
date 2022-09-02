package energizeglobalservices.bankservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
@Builder
public class GeneralStatus implements Serializable{



    public static enum Status {
        SUCCESS,
        FAILURE;

        private Status() {
        }
    }

        Status status;
        String responseId;
        String message;
        String clazz;

        public GeneralStatus() {
            this.responseId = UUID.randomUUID().toString();
        }

        public GeneralStatus(Status status, String responseId) {
            this.status = status;
            this.responseId = responseId;
        }

        public GeneralStatus(Status status, String responseId, String message) {
            this.status = status;
            this.responseId = responseId;
            this.message = message;
        }
}
