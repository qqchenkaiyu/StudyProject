package com.study.Spring.domain;

import com.study.Spring.GPComponent;
import com.study.Spring.ioc.GPAutowired;
import lombok.Data;

@Data
@GPComponent
public class Car implements com.study.domain.CarInterface {
    @GPAutowired
    private PersonInterface person;
}
