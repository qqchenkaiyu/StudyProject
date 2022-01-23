package com.study.copyClassTest.pojo;


//import org.apache.commons.beanutils.BeanUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by Administrator on 2019/4/13.
 */
public class Boy implements Cloneable {
	Integer age;
	String name;
	String height;
	String weight;
	Girl girl;

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	//	ByteArrayOutputStream bout ;
//	ObjectOutputStream oos;
//
//	ByteArrayInputStream bin ;
//	ObjectInputStream ois;
//	 {
//
//		 try {
//			 bout = new ByteArrayOutputStream();
//			 oos = new ObjectOutputStream(bout);
//			 bin = new ByteArrayInputStream(bout.toByteArray());
//			 ois = new ObjectInputStream(bin);
//		 } catch (Exception e) {
//			 e.printStackTrace();
//		 }
//
//	}
	public void SettetBoy(Boy boy) {
		setAge(boy.getAge());
		setGirl(boy.getGirl());
		setHeight(boy.getHeight());
		setWeight(boy.getWeight());
		setName(boy.getName());
	}
	public void FuzhiBoy(Boy boy) {
		this.age=boy.age;
		this.girl=boy.girl;
		this.height=boy.height;
		this.weight=boy.weight;
		this.name=boy.name;
	}
//	public Boy StreamBoy(Boy boy) throws IOException {
//		oos.writeObject(boy);
// bin = new ByteArrayInputStream(bout.toByteArray());
//		ObjectInputStream ois = new ObjectInputStream(bin);
//return; ois.readObject();
//	}
public void SpringBeanBoy(Boy boy) {
	BeanUtils.copyProperties(boy,this);
}
	public void CommonBeanBoy(Boy boy) throws InvocationTargetException, IllegalAccessException {

		//BeanUtils.copyProperties(boy,this);
	}
	public void CglibBoy(Object boy,BeanCopier beanCopier) {
		//BeanCopier beanCopier = BeanCopier.create(boy.getClass(), this.getClass(), false);
		beanCopier.copy(boy,this,null);
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Girl getGirl() {
		return girl;
	}

	public void setGirl(Girl girl) {
		this.girl = girl;
	}
}
