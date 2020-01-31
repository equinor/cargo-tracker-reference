
package com.equinor.cargotrackerreference.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.equinor.cargotrackerreference.MasterdataSetup;
import com.equinor.cargotrackerreference.builder.GradeResourceBuilder;
import com.equinor.cargotrackerreference.controller.resources.GradeResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles(profiles = { "h2" })
@WithUserDetails("user@equinor.com")
public class GradeControllerAccessTest extends MasterdataSetup {

	@Autowired 
	private ObjectMapper objectMapper;

	@Autowired 
	private MockMvc mockMvc;
	
	@Test
	@WithUserDetails("user@equinor.com")
	public void test_regularUserAccess() throws Exception {
		
		//Create a grade for the region
		GradeResource grade = GradeResourceBuilder.aGrade()
				.withName("DALIA")
				.withApi(new BigDecimal(20.56))
				.withSulphur(new BigDecimal(3.44))
				.withSource("OCD")
				.withTradingAreaId(wafTradingAreaResource.getId())
				.withValidfrom(LocalDate.of(2000, 1, 1))
				.buildGrade();
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/ctref/config/grade")					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isForbidden());					
		
		mockMvc.perform(MockMvcRequestBuilders
				.patch("/ctref/config/grade/" + asiaGrade1.id)					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isForbidden());
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/ctref/config/grade/" + asiaGrade1.id)					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isForbidden());
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/ctref/config/grade/" + asiaGrade1.id)					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isForbidden());
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/ctref/config/grade/" + asiaGrade1.id)
				.accept(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isOk());
		
	}	
	
	@Test
	@WithUserDetails("readonlyuser@equinor.com")
	public void test_readOnlyUserAccess() throws Exception {
		
		//Create a grade for the region
		GradeResource grade = GradeResourceBuilder.aGrade()
				.withName("DALIA")
				.withApi(new BigDecimal(20.56))
				.withSulphur(new BigDecimal(3.44))
				.withSource("OCD")
				.withTradingAreaId(wafTradingAreaResource.getId())
				.withValidfrom(LocalDate.of(2000, 1, 1))
				.buildGrade();
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/ctref/config/grade")					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isForbidden());					
		
		mockMvc.perform(MockMvcRequestBuilders
				.patch("/ctref/config/grade/" + asiaGrade1.id)					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isForbidden());
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/ctref/config/grade/" + asiaGrade1.id)					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isForbidden());
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/ctref/config/grade/" + asiaGrade1.id)					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isForbidden());
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/ctref/config/grade/" + asiaGrade1.id)
				.accept(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isOk());
		
	}	
		
	@Test
	@WithUserDetails("superuser@equinor.com")
	public void test_superUserAccess() throws Exception {
		
		//Create a grade for the region
		GradeResource grade = GradeResourceBuilder.aGrade()
				.withName("DALIA-TEMP")
				.withApi(new BigDecimal(20.56))
				.withSulphur(new BigDecimal(3.44))
				.withSource("OCD")
				.withTradingAreaId(wafTradingAreaResource.getId())
				.withValidfrom(LocalDate.of(2000, 1, 1))
				.buildGrade();
		
		MvcResult result = 
			mockMvc.perform(MockMvcRequestBuilders
					.post("/ctref/config/grade")					
					.content(objectMapper.writeValueAsString(grade))
					.contentType(MediaType.APPLICATION_JSON_UTF8)
				).andExpect(status().isOk()).andReturn();
		
		String id = JsonPath.parse(result.getResponse().getContentAsString()).read("$.id");
		
		mockMvc.perform(MockMvcRequestBuilders
				.patch("/ctref/config/grade/" + asiaGrade1.id)					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isOk());
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/ctref/config/grade/" + asiaGrade1.id)					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isOk());
					
		mockMvc.perform(MockMvcRequestBuilders
				.get("/ctref/config/grade/" + asiaGrade1.id)
				.accept(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isOk());
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/ctref/config/grade/" + id)					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isOk());		
	}	
	
	@Test
	@WithUserDetails("noaccess@equinor.com")
	public void test_noAccessUserAccess() throws Exception {
		
		//Create a grade for the region
		GradeResource grade = GradeResourceBuilder.aGrade()
				.withName("DALIA")
				.withApi(new BigDecimal(20.56))
				.withSulphur(new BigDecimal(3.44))
				.withSource("OCD")
				.withTradingAreaId(wafTradingAreaResource.getId())
				.withValidfrom(LocalDate.of(2000, 1, 1))
				.buildGrade();
		
		mockMvc.perform(MockMvcRequestBuilders
				.post("/ctref/config/grade")					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isForbidden());					
		
		mockMvc.perform(MockMvcRequestBuilders
				.patch("/ctref/config/grade/" + asiaGrade1.id)					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isForbidden());
		
		mockMvc.perform(MockMvcRequestBuilders
				.put("/ctref/config/grade/" + asiaGrade1.id)					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isForbidden());
		
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/ctref/config/grade/" + asiaGrade1.id)					
				.content(objectMapper.writeValueAsString(grade))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isForbidden());
		
		mockMvc.perform(MockMvcRequestBuilders
				.get("/ctref/config/grade/" + asiaGrade1.id)
				.accept(MediaType.APPLICATION_JSON_UTF8)
			).andExpect(status().isForbidden());
		
	}	
}
