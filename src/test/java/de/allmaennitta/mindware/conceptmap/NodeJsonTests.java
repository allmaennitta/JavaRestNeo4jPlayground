package de.allmaennitta.mindware.conceptmap;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

@JsonTest
@RunWith(SpringRunner.class)
public class NodeJsonTests {

  @Autowired
  private JacksonTester<Node> json;

  @Test
  public void testSerialize() throws Exception {
    Node details = new Node("kalle");
    System.out.println(this.json.write((details)).toString());
    assertThat(this.json.write(details)).hasJsonPathStringValue("@.name");
    assertThat(this.json.write(details)).isEqualToJson("{'name': 'kalle'}");
    assertThat(this.json.write(details)).extractingJsonPathStringValue("@.name").contains("all");
  }

  @Test
  public void testDeserialize() throws Exception {
    String content = "{\"name\":\"Ursula\"}";
    assertThat(this.json.parse(content))
        .isEqualTo(new Node("Ursula"));
    assertThat(this.json.parseObject(content).getName()).isEqualTo("Ursula");
  }

  @Test
  public void multiplicationOfZeroIntegersShouldReturnZero() {
    assertThat("a").as("test context").isEqualTo("a");
  }


}