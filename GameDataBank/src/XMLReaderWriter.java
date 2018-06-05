import java.io.File;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class XMLReaderWriter {
	
	public GameCollection read (final File file)
	{
		return JAXB.unmarshal(file, GameCollection.class);
	}
	
	public void write(final File file, final GameCollection game) throws JAXBException
	{
		final JAXBContext jc = JAXBContext.newInstance(GameCollection.class);
		final Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(game, file);
	}
	
}
