package com.sophos.poc.wsrestconsultarsaldo;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sophos.poc.wsrestconsultarsaldo.controller.AccountApiController;
import com.sophos.poc.wsrestconsultarsaldo.model.ConsultaSaldoReq;
import com.sophos.poc.wsrestconsultarsaldo.model.ConsultaSaldoRes;
import com.sophos.poc.wsrestconsultarsaldo.service.AccountService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WsRestConsultarSaldoApplicationTests {

	private MockMvc mockMvc;
	@InjectMocks
	private AccountApiController apiController;
	@Mock
	private AccountService mockService;
	@Mock
	private HttpServletRequest mockRequest;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(apiController).build();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void test_not_proccess_service() throws Exception {
		String sessionKey = "999999999999";
		
		ConsultaSaldoReq request = new ConsultaSaldoReq();
		request.setAccount_id("123567");
		request.setAccount_type("01");

		ConsultaSaldoRes response = new ConsultaSaldoRes();
		response.setAuth_code("00");

		when(mockRequest.getContentType()).thenReturn("application/json");
		when(mockRequest.getHeader("X-Sesion")).thenReturn(sessionKey);
		when(mockService.getBalance(request)).thenReturn(response);

		mockMvc.perform(
				MockMvcRequestBuilders.get("/accounts/balance")
				.contentType(MediaType.APPLICATION_JSON)
				.header("X-RqUID", "123456")
				.header("X-Channel", "POC")
				.header("X-IPAddr", "10.10.10.1")
				.header("X-Sesion", sessionKey)
				.content(asJsonString(request)))
				.andExpect(MockMvcResultMatchers.status().is5xxServerError());

		 verify(mockService, times(0)).getBalance(request);	  
		 verifyNoMoreInteractions(mockService);
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
