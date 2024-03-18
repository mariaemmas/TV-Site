import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Input;
import implement.LoadData;

import java.io.File;
import java.io.IOException;

public class Main {
    /**
     * Method main is called from Test
     * @param args given from Test
     * @throws IOException  for reading / writing
     */
    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(args[0]), Input.class);
        ArrayNode output = objectMapper.createArrayNode();

        LoadData load = new LoadData(inputData, output);
        load.load();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

        objectWriter.writeValue(new File("results.out"), output);
    }
}
