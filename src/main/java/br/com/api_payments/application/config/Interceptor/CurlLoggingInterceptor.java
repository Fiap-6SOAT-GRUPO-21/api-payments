package br.com.api_payments.application.config.Interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class CurlLoggingInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println(generateCurlCommand(requestTemplate));
    }

    private String generateCurlCommand(RequestTemplate requestTemplate) {
        StringBuilder curlCommand = new StringBuilder();
        curlCommand.append("curl -X ").append(requestTemplate.method()).append(" \\\n");

        // Adiciona os cabeçalhos
        requestTemplate.headers().forEach((name, values) ->
                values.forEach(value -> curlCommand.append("    -H '").append(name).append(": ").append(value).append("' \\\n")));

        // Adiciona o corpo da requisição, se houver
        if (requestTemplate.body() != null) {
            curlCommand.append("    -d '").append(new String(requestTemplate.body())).append("' \\\n");
        }

        curlCommand.append("    '").append(requestTemplate.feignTarget().url()).append(requestTemplate.url()).append("'");

        return curlCommand.toString();
    }
}
