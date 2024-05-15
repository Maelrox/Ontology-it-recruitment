package co.edu.iush.ontology.rdf.component;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class RDFLoader implements CommandLineRunner {

    @Value("${rdf.file.path}")
    private String rdfFilePath;

    private Model rdfModel;

    @Override
    public void run(String... args) throws Exception {
        rdfModel = loadRDFModelFromFile(rdfFilePath);
    }

    public Model getRdfModel() {
        return rdfModel;
    }

    private Model loadRDFModelFromFile(String filePath) {
        Model model = ModelFactory.createDefaultModel();
        try {
            // Load RDF data from a local file path
            InputStream in = new FileInputStream(filePath);
            model.read(in, null); // You can specify the RDF format if known
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return model;
    }
}