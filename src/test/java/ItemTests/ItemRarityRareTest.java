package ItemTests;

import models.Item;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ItemRarityRareTest {
	@Test
	public void itemRarityRareTest(){
		assertEquals(Item.Rarity.RARE, Item.getItemRarity(100, 1500));
	}

	@Test
	public void itemRarityRareTest2(){
		assertEquals(Item.Rarity.RARE, Item.getItemRarity(359, 24499));
	}
}
