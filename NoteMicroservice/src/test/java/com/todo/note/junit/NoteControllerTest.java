package com.todo.note.junit;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.note.NoteMicroserviceApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = NoteMicroserviceApplication.class)
@SpringBootTest
public class NoteControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	private ObjectMapper mapper = new ObjectMapper();

	private Resource casesFile;

	private Map<String, Json> cases;

	@Before
	public void setup() throws JsonParseException, JsonMappingException, IOException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		casesFile = new ClassPathResource("cases.json");

		cases = mapper.readValue(casesFile.getInputStream(), new TypeReference<Map<String, Json>>() {
		});
	}

	@Test
	public void createNote() throws Exception {
		Json json = cases.get("createnote");
		test(json);
	}
	@Test
	public void creteNoteValidation() throws Exception {
		Json json = cases.get("createnotevalid");
		test(json);
	}
	@Test
	public void createlabel() throws Exception {
		Json json = cases.get("createlabel");
		test(json);
	}
	@Test
	public void createlabelSad() throws Exception {
		Json json = cases.get("createlabelsad");
		test(json);
	}
	@Test
	public void remainder() throws Exception {
		Json json = cases.get("remainder");
		test(json);
	}
	

	private void test(Json json) throws Exception {
		ResultActions actions = mockMvc
				.perform(getMethod(json).headers(json.getRequest().getHeaders()).contentType(MediaType.APPLICATION_JSON)
						.content(getRequestBody(json)).accept(MediaType.APPLICATION_JSON));

		actions.andExpect(status().is(json.getResponse().getStatus().value()));

		MockHttpServletResponse response = actions.andReturn().getResponse();

		for (String key : json.getResponse().getHeaders().keySet()) {
			assertEquals(json.getResponse().getHeaders().get(key), response.getHeader(key));
		}
		assertEquals(getResponseBody(json), response.getContentAsString());
	}

	private MockHttpServletRequestBuilder getMethod(Json json) {
		return MockMvcRequestBuilders.request(HttpMethod.resolve(json.getRequest().getMethod()),
				json.getRequest().getUrl());
	}

	private String getRequestBody(Json json) throws JsonProcessingException {
		return mapper.writeValueAsString(json.getRequest().getBody());
	}

	private String getResponseBody(Json json) throws JsonProcessingException {
		return mapper.writeValueAsString(json.getResponse().getBody());
	}
}