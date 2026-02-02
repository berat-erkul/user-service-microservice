package com.cydeo.util;

import com.cydeo.dto.RoleDTO;
import com.cydeo.service.RoleService;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class RoleDTODeserializer extends JsonDeserializer<RoleDTO> implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RoleDTODeserializer.applicationContext = applicationContext;
    }

    @Override
    public RoleDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String roleDescription = node.asText();

        RoleService roleService = applicationContext.getBean(RoleService.class);
        return roleService.readByDescription(roleDescription);

    }

}
