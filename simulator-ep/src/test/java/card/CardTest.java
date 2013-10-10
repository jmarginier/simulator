package card;

import static org.junit.Assert.*;

import java.util.HashMap;

import model.component.ComponentIO;
import model.factory.MediatorFactory;
import model.factory.MediatorFactory.EMediator;
import model.mediator.Mediator;
import model.strategies.NullStrategy;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ep.strategies.card.CardStrategy;
import utils.ISO7816Exception;
import utils.ISO7816Tools;

public class CardTest {

	private static ComponentIO tpe;
	private static ComponentIO card;
	private static ComponentIO chip;
	private static ComponentIO magstrippe;
	
	private static Mediator m_tpe_card;
	private static Mediator m_card_chip;
	private static Mediator m_card_magstrippe;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//fake tpe
		tpe = new ComponentIO("tpe");
		
		card = new ComponentIO("cb");
		card.getProperties().put("cipher", "RSA2048");
		card.getProperties().put("protocol", "ISO7816");
		card.getProperties().put("pan", "1111111111111111111111111");
		card.getProperties().put("icvv", "000");
		card.getProperties().put("genre", "M");
		card.getProperties().put("nom porteur", "Florent Moisson");
		card.getProperties().put("date expiration", "09/15");
		
		chip = new ComponentIO("puce");
		chip.getProperties().put("pan", "1111111111111111111111111");
		chip.getProperties().put("bccs", "12421874");
		
		magstrippe = new ComponentIO("piste magnetique");
		magstrippe.getProperties().put("piste iso2", "59859595985888648468454684");
		
		card.getComponents().add(magstrippe);
		card.getComponents().add(chip);
		
		card.setStrategy(new CardStrategy(card));
		tpe.setStrategy(new NullStrategy());
		
		m_tpe_card = MediatorFactory.getInstance().getMediator(card, tpe, EMediator.HALFDUPLEX);
		m_card_chip = MediatorFactory.getInstance().getMediator(card, chip, EMediator.HALFDUPLEX);
		m_card_magstrippe = MediatorFactory.getInstance().getMediator(card, magstrippe, EMediator.HALFDUPLEX);
		
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void SecureChanneltest() {
		//TPE SEND info for init sc
		String data = "01010040000000000POS ID0100000623598000PROTOCOL LIST022ISO7816 ISO8583 CB2A-T0000000PREFERRED007ISO781600000000DATETIME0101008170100";
		//m_tpe_card.send(tpe, "01010030000000000POS ID0100000623598000PROTOCOL LIST022ISO7816 ISO8583 CB2A-T0000000PREFERRED007ISO781600000000DATETIME0101008170100");
	
		m_tpe_card.send(tpe, data);
		/*HashMap<String, String> structdata;
		try {
			structdata = ISO7816Tools.data2Hash(data);
		} catch (ISO7816Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
	}

}