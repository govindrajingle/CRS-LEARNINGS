import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
    <version>2.3.1</version>
</dependency>
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>2.3.3</version>
</dependency>


String xmldata = null;
		try {
			xmldata = serializeToXml(averageWeightModel);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
	private String serializeToXml(AverageWeightModel averageWeightModel) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(AverageWeightModel.class);
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter writer = new StringWriter();
		marshaller.marshal(averageWeightModel, writer);
		return writer.toString();
	}		
	
	
@XmlRootElement
public class AverageWeightModel {

    private double totalSamples;
    private double[] inputsForEachSamples; 
    private double averageWeight;

    private String xmlData;

}	



ALTER TABLE test_method_development_table
ALTER COLUMN xml_data TYPE TEXT;