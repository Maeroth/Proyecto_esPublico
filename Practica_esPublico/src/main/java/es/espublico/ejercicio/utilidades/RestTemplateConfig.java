package es.espublico.ejercicio.utilidades;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import es.espublico.ejercicio.modelo.Film;
import es.espublico.ejercicio.modelo.People;
import es.espublico.ejercicio.negocio.EntityManagerStarWars;

/**
 * Configuración del Bean RestTemplate: 
 * La configuración es simple: Si al leer un json, hay un campo int que viene informado
 * con la cadena "unknown", se validará como 0 en lugar de saltar una excepción
 * Este Bean se va a leer con el component-scan que tiene Spring-Boot
 * @author Manuel León
 * @since: 06/02/2023
 *
 */

@Configuration
public class RestTemplateConfig {

  @Bean(name="restTemplate")
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplate();

    ObjectMapper objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addDeserializer(int.class, new JsonDeserializer<Integer>() {
      @Override
      public Integer deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getValueAsString();
        if ("unknown".equals(value)) {
          // Devuelve un valor por defecto si la cadena es "unknown"
          return 0;
        }
        if ("n/a".equals(value)) {
            // Devuelve un valor por defecto si la cadena es "unknown"
            return 0;
          }
        // De lo contrario, intenta parsear la cadena como un int normalmente
        return Integer.parseInt(value);
      }
    });
    
    SimpleModule module2 = new SimpleModule();
    module2.addDeserializer(double.class, new JsonDeserializer<Double>() {
      @Override
      public Double deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String value = p.getValueAsString();
        if (value.contains(",")) {
          value = value.replace(',', '.'); //Si trae una coma, lo pasamos a punto
        }
        // De lo contrario, intenta parsear la cadena como un int normalmente
        return Double.parseDouble(value);
      }
    });
    
    objectMapper.registerModule(module);
    objectMapper.registerModule(module2);

    MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
    mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

    
//El problema que está sucediendo es que ya existe un mappingJaxkson2HttpMessageConverter y por eso no toma los cambios que he definido arriba
//con el objectMapper. Para que coja esta configuración, debo añadir este código de abajo para que borre la actual configuración del restTemplate
//Nota: Para que la práctica funcione sin hacer este paso, voy a poner los campos que den problema a String para que sea fiel a la información
//que está leyendo.
    
/*    List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
    for (HttpMessageConverter<?> messageConverter : messageConverters) {
        if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
        	 messageConverters.remove(messageConverter);
             break;
        }
    }*/
    
    restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);
    
    return restTemplate;
  }
}