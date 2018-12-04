package com.hdl.gzccocpcore.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeGenerator {

    ImageCode generate(ServletWebRequest servletWebRequest);

}
