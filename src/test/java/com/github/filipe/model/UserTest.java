package com.github.filipe.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEqualsFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCodeFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.code.beanmatchers.BeanMatchers;
import com.google.code.beanmatchers.ValueGenerator;

public class UserTest {
	
	private User user;
	
	@Before
	public void setup() {
		
		BeanMatchers.registerValueGenerator(new ValueGenerator<LocalDate>() {
			public LocalDate generate() {
				long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
			    long maxDay = LocalDate.of(2019, 12, 31).toEpochDay();
			    long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
			    LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
				return randomDate;
	        }
	    }, LocalDate.class);
		
	}
	
	@Test
	public void serializeTest() {
		
		user = new User();
		user.setId(System.currentTimeMillis());
		user.setActive(true);
		user.setEmail("test@test.com");
		user.setName("Test User");
		user.setPassword("password");
		user.setProfiles(new HashSet<>());
		user.setRegisteredIn(new Date());
		
		final byte[] serialazed = SerializationUtils.serialize(user);
		final User deserialized = (User) SerializationUtils.deserialize(serialazed);
		
		assertEquals(user, deserialized);
	}
	
	@Test
	public void vehicleTest() {
		assertThat(User.class, allOf(
                hasValidBeanConstructor(),
                hasValidGettersAndSetters(),
                hasValidBeanHashCodeFor("id"),
                hasValidBeanEqualsFor("id")
        ));
	}



}
