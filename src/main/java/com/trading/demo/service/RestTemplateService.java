package com.trading.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestTemplateService {
    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(RestTemplateService.class);

    public RestTemplateService (@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        for (HttpMessageConverter converter : restTemplate.getMessageConverters()) {
            if ( converter instanceof MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter)converter).getObjectMapper().configure(
                        SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            }
        }
    }

    public <T> List<T> getRestForList(String url, HttpEntity<?> requestEntity, Class<T> clazz) {
        List<T> result = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                    ParameterizedTypeReference.forType(type));

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                result = (List<T>) response.getBody();
            }
        } catch (Exception ex) {
            logger.error("Call Rest failed: " + ex);
        }

        return result;
    }

    public <T> Object getRestForObject(String url, HttpEntity<?> requestEntity, Class<T> clazz) {
        Object result = null;

        try {
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                    ParameterizedTypeReference.forType(clazz));

            if (response.getStatusCode().equals(HttpStatus.OK)) {
                result = response.getBody();
            }
        } catch (Exception ex) {
            logger.error("Call Rest failed: " + ex);
        }

        return result;
    }


}
