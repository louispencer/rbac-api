package com.github.filipe.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEqualsFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCodeFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

public class ProfileTest {
	
	private Profile profile;
	
	@Test
	public void serializeTest() {
		
		profile = new Profile();
		profile.setId(System.currentTimeMillis());
		profile.setActive(true);
		profile.setDescription("TEST");
		profile.setRoles(new HashSet<>());
		
		final byte[] serialazed = SerializationUtils.serialize(profile);
		final Profile deserialized = (Profile) SerializationUtils.deserialize(serialazed);
		
		assertEquals(profile, deserialized);
	}
	
	@Test
	public void vehicleTest() {
		assertThat(Profile.class, allOf(
                hasValidBeanConstructor(),
                hasValidGettersAndSetters(),
                hasValidBeanHashCodeFor("id"),
                hasValidBeanEqualsFor("id")
        ));
	}


}
