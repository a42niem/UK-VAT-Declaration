package uk.gov.hmrc.parser;

import static uk.gov.hmrc.parser.HMRCParser.prettyMapper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import uk.gov.hmrc.model.VATObligationResponse;
import uk.gov.hmrc.model.VATReturn;
import uk.gov.hmrc.model.VATReturnResponse;

public class VATParser {

    public static String toJson( VATReturn  vatReturn) throws JsonProcessingException {
        return prettyMapper.writeValueAsString(vatReturn);
    }

    public static VATReturn fromJson(String json) throws IOException {
        return prettyMapper.readValue(json,  VATReturn.class);
    }
    
    public static VATReturnResponse fromJsonResponse(String json) throws IOException {
        return prettyMapper.readValue(json,  VATReturnResponse.class);
    }

    public static VATObligationResponse fromJsonObligations(String json) throws IOException {
        return prettyMapper.readValue(json,  VATObligationResponse.class);
    }

//    public static VATLiabilitiesResponse fromJsonLiabilities(String json) throws IOException {
//        return prettyMapper.readValue(json,  VATLiabilitiesResponse.class);
//    }
//
//    public static VATPaymentsResponse fromJsonPayments(String json) throws IOException {
//        return prettyMapper.readValue(json,  VATPaymentsResponse.class);
//    }

}
