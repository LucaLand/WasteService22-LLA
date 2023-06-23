package it.unibo.WasteServiceStatusGui;

import it.unibo.coapobs.CoapObserverJava;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class WasteServiceStatusGuiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WSGuiController controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}


	/** Before starting this test run the CtxWasteService in order to make the Observer in the Controller fetch the standard data from the Actors
	 *	Also run CtxRaspberry (sprint3) and it.unibo.basicrobot (Sprint-1)
	 **/
	@Test
	public void test2() throws Exception {
		CoapObserverJava testObs = new CoapObserverJava("localhost:8072", "ctxwasteservice", "guiupdater");
		while(testObs.getCurrentState() == null){
			System.out.println("Waiting CtxWasteService....");
			Thread.sleep(1000);
		}

		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("athome")));
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("LedOff")));
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(model().attribute("pb", "0"));
	}

}
