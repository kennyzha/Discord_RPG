package ItemTests;

import models.Item;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ItemRarityEpicTest {
	@Test
	public void itemRarityTest(){
		assertEquals(Item.Rarity.EPIC, Item.getItemRarity(100, 2000));
	}

	@Test
	public void itemRarityTest2(){
		assertEquals(Item.Rarity.EPIC, Item.getItemRarity(100, 2800));
	}

	@Test
	public void itemRarityTest3(){
		assertEquals(Item.Rarity.EPIC, Item.getItemRarity(359, 24555));
	}

	@Test
	public void itemRarityTest4(){
		assertEquals(Item.Rarity.EPIC, Item.getItemRarity(359, 24500));
	}
}
