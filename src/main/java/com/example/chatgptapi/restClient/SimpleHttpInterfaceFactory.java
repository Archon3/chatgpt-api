package com.example.chatgptapi.restClient;

import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

public class SimpleHttpInterfaceFactory implements HttpInterfaceFactory {

    // SpringBoot 3.2 이상
    public <S> S create(Class<S> clientClass, RestClient restClient) {
        return HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build()
                .createClient(clientClass);
    }
}

