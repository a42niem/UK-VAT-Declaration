package uk.gov.hmrc.parser;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class HMRCParser {
	public static final ObjectMapper prettyMapper;


	static {
		SimpleModule hmrcmodule = new SimpleModule("HMRCmodule", new Version(2, 0, 0, null,
				"uk.gov.hmrc", "HMRC-test"));

		prettyMapper = new ObjectMapper();
//		prettyMapper.registerModule(new JavaTimeModule());
		prettyMapper.findAndRegisterModules();
		prettyMapper.registerModule(hmrcmodule);
		prettyMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		prettyMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
		prettyMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		prettyMapper.configure(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
		prettyMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		prettyMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
	}

}
