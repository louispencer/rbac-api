package com.github.rbac.model;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanEqualsFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanHashCodeFor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.Test;

public class RoleTest {
	
	private Role role;
	
	@Test
	public void serializeTest() {
		
		role = new Role();
		role.setId(System.currentTimeMillis());
		role.setActive(true);
		role.setDescription("ROLE_TEST");
		
		final byte[] serialazed = SerializationUtils.serialize(role);
		final Role deserialized = (Role) SerializationUtils.deserialize(serialazed);
		
		assertEquals(role, deserialized);
	}
	
	@Test
	public void vehicleTest() {
		assertThat(Role.class, allOf(
                hasValidBeanConstructor(),
                hasValidGettersAndSetters(),
                hasValidBeanHashCodeFor("id"),
                hasValidBeanEqualsFor("id")
        ));
	}
}
