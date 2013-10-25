package card;

import java.util.Date;

import model.component.ComponentIO;
import model.factory.MediatorFactory;
import model.factory.MediatorFactory.EMediator;
import model.mediator.Mediator;
import model.response.DataResponse;
import model.response.IResponse;
import model.strategies.IStrategy;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import simulator.Context;
import simulator.SimulatorFactory;
import simulator.exception.SimulatorException;
import ep.strategies.card.CardChipStrategy;
import ep.strategies.card.CardStrategy;

public class CardTest {

	private static ComponentIO card;
	private static ComponentIO chip;
	private static ComponentIO magstrippe;
	private static ComponentIO ept;
	private static MediatorFactory factory = MediatorFactory.getInstance();

	private static Mediator m_ept_card;
	private static Mediator m_card_chip;
	private static Mediator m_card_magstrippe;

	@Before
	public void init() throws Exception {
		Context.getInstance().autoRegistrationMode();
		card = new ComponentIO("cb");
		card.getProperties().put("pan", "1111111111111111111111111");
		card.getProperties().put("icvv", "000");
		card.getProperties().put("type", "M");
		card.getProperties().put("name", "Florent Moisson");
		card.getProperties().put("date expiration", "09/15");

		chip = new ComponentIO("chip");
		chip.getProperties().put("protocol", "ISO7816");
		chip.getProperties().put("pan", "1111111111111111111111111");
		chip.getProperties().put("bccs", "12421874");
		chip.getProperties().put("ceil", "400");
		chip.getProperties().put("approvalcode", "07B56=");
		chip.getProperties().put("state", "OFF");

		magstrippe = new ComponentIO("magstrippe");
		magstrippe.getProperties().put("iso2", "59859595985888648468454684");

		card.getComponents().add(magstrippe);
		card.getComponents().add(chip);

		ept = new ComponentIO("ept");

		card.setStrategy(new CardStrategy());
		chip.setStrategy(new CardChipStrategy());
		m_ept_card = MediatorFactory.getInstance().getMediator(card, ept, EMediator.HALFDUPLEX);
		m_card_chip = MediatorFactory.getInstance().getMediator(card, chip, EMediator.HALFDUPLEX);
		m_card_magstrippe = MediatorFactory.getInstance().getMediator(card, magstrippe, EMediator.HALFDUPLEX);

	}

	@After
	public void clean() throws Exception {
		Context.getInstance().reset();
	}

	@Test
	public void secureChanneltest() {
		final String tpe_sc = "01010060000000000POS ID0100000623598000PROTOCOL LIST022ISO7816 ISO8583 CB2A-T0000000PREFERRED007ISO78160000RET REF NUMB012320012000001000000000000STAN00600000100000000DATETIME0101008170100";
		ept.setStrategy(new IStrategy<ComponentIO>() {

			@Override
			public void processEvent(ComponentIO _this, String event) {
				DataResponse rp = (DataResponse) m_ept_card.send(_this, tpe_sc);
				Assert.assertTrue(rp.getData().contains(
						"01100050000000000POS ID010000062359800000000PROTOCOL007ISO7816000000000000STAN006000002"));
			}

			@Override
			public IResponse processMessage(ComponentIO _this, Mediator mediator, String data) {

				return DataResponse.build(null, null);
			}

			@Override
			public void init(Context ctx) {
				// TODO Auto-generated method stub

			}

		});
		// on insert la carte dans le tpe, le tpe envoie des donnees a la carte
		Context.getInstance().addStartPoint(new Date(), ept, "SMART_CARD_INSERTED");
		// execute simulation.
		try {
			SimulatorFactory.getSimulator().start();
		}
		catch (SimulatorException e) {
			Assert.assertFalse(true);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void cardHoldertest() {
		final String tpe_ch = "03010070000000000POS ID0100000623598000000000OP CODE002000000000000AMOUNT010000000800000000000PIN DATA00412340000RET REF NUMB012320012000001000000000000STAN00600000100000000DATETIME0101008170100";
		ept.setStrategy(new IStrategy<ComponentIO>() {

			@Override
			public void processEvent(ComponentIO _this, String event) {

				DataResponse rp = (DataResponse) m_ept_card.send(_this, tpe_ch);
				System.out.println(rp.getData());
				Assert.assertTrue(rp
						.getData()
						.contains(
								"03100070000000000POS ID0100000623598000000000OP CODE002000000000000AMOUNT0100000008000PIN VERIFICATION001100CARD AGREEMENT00110000000000000PAN016497671002564213000000000STAN006000002"));

			}

			@Override
			public IResponse processMessage(ComponentIO _this, Mediator mediator, String data) {
				return DataResponse.build(null, null);
			}

			@Override
			public void init(Context ctx) {
				// TODO Auto-generated method stub

			}

		});
		// on insert la carte dans le tpe, le tpe envoie des donnees a la carte
		Context.getInstance().addStartPoint(new Date(), ept, "SMART_CARD_INSERTED");
		// execute simulation.
		try {
			SimulatorFactory.getSimulator().start();
		}
		catch (SimulatorException e) {
			Assert.assertFalse(true);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void finalAgreementtest() {
		final String tpe_finalagree = "04110090000000000POS ID0100000623598000000000OP CODE002000000000000AMOUNT0100000008000000APPROVAL CODE00607B56=000RESPONSE CODE002000000000000000PAN016497671002564213000000000DATETIME01010081730260000RET REF NUMB012320012000001000000000000STAN006000001";
		ept.setStrategy(new IStrategy<ComponentIO>() {

			@Override
			public void processEvent(ComponentIO _this, String event) {
				DataResponse rp = (DataResponse) m_ept_card.send(_this, tpe_finalagree);
				System.out.println(rp.getData());
				Assert.assertTrue(rp
						.getData()
						.contains(
								"05000070000000000POS ID0100000623598000000000OP CODE002000000000000AMOUNT0100000008000000APPROVAL CODE00607B56=000RESPONSE CODE002000000000000000PAN016497671002564213000000000STAN006000002"));

			}

			@Override
			public IResponse processMessage(ComponentIO _this, Mediator mediator, String data) {
				return DataResponse.build(null, null);
			}

			@Override
			public void init(Context ctx) {
				// TODO Auto-generated method stub

			}

		});
		// on insert la carte dans le tpe, le tpe envoie des donnees a la carte
		Context.getInstance().addStartPoint(new Date(), ept, "SMART_CARD_INSERTED");
		// execute simulation.
		try {
			SimulatorFactory.getSimulator().start();
		}
		catch (SimulatorException e) {
			Assert.assertFalse(true);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
