package com.example.chatgptapi.restClient;

import org.springframework.web.client.RestClient;

public interface HttpInterfaceFactory {

    <S> S create(Class<S> clientClass, RestClient restClient);

}

