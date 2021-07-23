package com.example.hello.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/config")
@RequestScoped
public class ConfigTestController {

    @Inject
    @ConfigProperty(name = "injected.value")
    private String injectedValue;
    
    @Inject
    @ConfigProperty(name = "required", defaultValue = "required")
    private String required;

    @Path("/injected")
    @GET
    public String getInjectedConfigValue() {
        return "Config value as Injected by CDI " + injectedValue;
    }
    
    @Path("/lookup")
    @GET
    public String getLookupConfigValue() {
        Config config = ConfigProvider.getConfig();
        String value = config.getValue("value", String.class);
        return "Config value from ConfigProvider " + value;
    }

    @Path("/lookupoptional")
    @GET
    public String getLookupoptionalConfigValue() {
    	Config config = ConfigProvider.getConfig();
        Optional<String> optionalValue = config.getOptionalValue("optional",String.class);
        if(optionalValue.isPresent()){
        	return optionalValue.get();
        }
        return required;
    }
}
