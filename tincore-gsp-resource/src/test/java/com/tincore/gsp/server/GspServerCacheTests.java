package com.tincore.gsp.server;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { GspResourceApplication.class, TestConfiguration.class })
@ActiveProfiles("test")
public class GspServerCacheTests {

	@Autowired
	private CacheManager cacheManager;

	@Test
	public void testUserRepositoryCache() {
		Cache user = cacheManager.getCache("test");
		assertThat(user, notNullValue());
		// countries.clear(); // Simple test assuming the cache is empty
		// assertThat(countries.get("BE")).isNull();
		// Country be = this.countryRepository.findByCode("BE");
		// assertThat((Country) countries.get("BE").get()).isEqualTo(be);
	}

}