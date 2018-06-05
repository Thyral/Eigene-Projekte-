import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "myGames")
public class GameCollection {
	
	private List<Game> gameCollection;
	
	@XmlElement(name = "game")
	public List<Game> getGameCollection()
	{
		if (gameCollection == null)
		{
			gameCollection = new LinkedList<>();
		}
		
		return gameCollection;
	}

	public void setGameCollection(final List<Game> gameCollection)
	{
		this.gameCollection = gameCollection;
	}
}
