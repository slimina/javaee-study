package cn.slimsmart.java.demo.valid;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class ValidTest {

	public static void main(String[] args) {

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();

		User user = new User();
		user.setAge(12);
		user.setMail("abcdddd");
		user.setName("测试测试测试");
		user.setSex("A");
		user.setCourse(new Course());
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		for (ConstraintViolation<User> constraintViolation : constraintViolations) {
			System.err.println(constraintViolation.getPropertyPath()+":"+constraintViolation.getMessage());
		}
	}

}
