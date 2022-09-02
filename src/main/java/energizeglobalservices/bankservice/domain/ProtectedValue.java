package energizeglobalservices.bankservice.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProtectedValue {

    private String value;

    @Override
    public String toString() {
        return "Secure Value";
    }
}
