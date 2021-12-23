package com.study.domain;

import com.study.Spring.GPComponent;
import com.study.Spring.ioc.GPAutowired;
import lombok.Data;

@Data
@GPComponent
public class Car implements CarInterface{
    @GPAutowired
    private PersonInterface person;
}
