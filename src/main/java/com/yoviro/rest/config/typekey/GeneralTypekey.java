package com.yoviro.rest.config.typekey;

import javax.annotation.PostConstruct;
import java.util.List;

public abstract class GeneralTypekey {
    private List<OptionTypekey> options;

    public List<OptionTypekey> getOptions() {
        return options;
    }

    public void setOptions(List<OptionTypekey> options) {
        this.options = options;
    }

    protected OptionTypekey getOptionTypekeyByCode(String code){
        for(OptionTypekey option : getOptions()){
            if(option.getCode().compareToIgnoreCase(code) == 0) return option;
        }
        return null;
    }

    /***
     * Author: Andrés V.
     * Desc : Es recomendable popular valores estaticos en la clase implementada, de forma que se asegure
     * reutilizar el codedecode en todo el aplicativo
     */
    @PostConstruct
    public abstract void afterConstruct();
}
