package ItemTests;

import models.Item;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ItemRarityUncommonTest {
	@Test
	public void itemRarityUncommonTest(){
		assertEquals(Item.Rarity.UNCOMMON, Item.getItemRarity(100, 1001));
	}

	@Test
	public void itemRarityUncommonTest2(){
		assertEquals(Item.Rarity.UNCOMMON, Item.getItemRarity(144, 1001));
	}
	@Test
	public void itemRarityUncommonTest3(){
		assertEquals(Item.Rarity.UNCOMMON, Item.getItemRarity(107, 1499));
	}
	@Test
	public void itemRarityUncommonTest4(){
		assertEquals(Item.Rarity.UNCOMMON, Item.getItemRarity(155, 3001));
	}
	@Test
	public void itemRarityUncommonTest5(){
		assertEquals(Item.Rarity.UNCOMMON, Item.getItemRarity(155, 3749));
	}
	@Test
	public void itemRarityUncommonTest6(){
		assertEquals(Item.Rarity.UNCOMMON, Item.getItemRarity(155, 3666));
	}
	@Test
	public void itemRarityUncommonTest7(){
		assertEquals(Item.Rarity.UNCOMMON, Item.getItemRarity(100, 1111));
	}
}
