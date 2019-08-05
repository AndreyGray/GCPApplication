package candreyskakunenko.myapplication.model;

import java.util.HashMap;
import java.util.Map;

public class Menu {

    private String name;
    private String function;
    private String param;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public Menu(String name, String function, String param) {
        this.name = name;
        this.function = function;
        this.param = param;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
