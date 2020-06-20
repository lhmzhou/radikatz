package com.radikatz;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

import static org.assertj.core.api.Assertions.assertThat;
 
public class ApplicationTest {

   @LocalServerPort
   private int port;

   @Autowired
   private TestRestTemplate restTemplate;

   @Test
   public void greetingShouldReturnDefaultMessage() throws Exception {
      assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
            String.class)).contains("Hello, World");
   }

   @After
    public void cleanUpStreams() {
        System.setOut(null);
    }
}