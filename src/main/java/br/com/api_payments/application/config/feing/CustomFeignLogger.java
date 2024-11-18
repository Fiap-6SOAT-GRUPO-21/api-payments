package br.com.api_payments.application.config.feing;

import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Setter
@Component
public class CustomFeignLogger extends Logger {

        @Override
        protected void logRequest(String configKey, Level logLevel, Request request) {
        StringBuilder curlCommand = new StringBuilder("curl -X ")
                .append(request.httpMethod().name())
                .append(" '")
                .append(request.url())
                .append("'");

        for (Map.Entry<String, Collection<String>> headerEntry : request.headers().entrySet()) {
            for (String headerValue : headerEntry.getValue()) {
                curlCommand.append(" -H '")
                        .append(headerEntry.getKey())
                        .append(": ")
                        .append(headerValue)
                        .append("'");
            }
        }

        if (request.body() != null) {
            curlCommand.append(" -d '").append(new String(request.body())).append("'");
        }

        log.info("HTTP request: {}", curlCommand);
    }

        @Override
        protected void logRetry(String configKey, Level logLevel) {
        // Implement retry logging if needed
    }

        @Override
        protected void log(String configKey, String format, Object... args) {
        log.info(String.format(methodTag(configKey) + format, args));
    }

        @Override
        protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) throws IOException {
        String status = String.valueOf(response.status());
        String responseBody = " - ";

        if (response.body() != null) {
            responseBody = Util.toString(response.body().asReader(StandardCharsets.UTF_8));
        }

        String msgLog = "HTTP Response: URL " + response.request().url() + " | STATUS: " + status + " | TIME: " + elapsedTime + "ms";

        msgLog += " | RESPONSE BODY: " + responseBody;

        log.info(msgLog);


        return response.toBuilder().body(responseBody.getBytes(StandardCharsets.UTF_8)).build();
    }
}
