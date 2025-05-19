package edu.austral.dissis.chess.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ExampleTest {

  // Dummy test because kover includes constructor in coverage
  @Test
  void testInstance() {
    JavaMain main = new JavaMain();
    assertEquals(main.getClass().getSimpleName(), "JavaMain");
  }

  @Test
  void testGetMessage() {
    assertEquals("Hello      World!", JavaMain.getMessage());
  }
}
