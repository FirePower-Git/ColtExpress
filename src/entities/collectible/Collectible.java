package entities.collectible;

/**
 * Class Collectible
 * Money bags or gems that can be collected by players
 */
public class Collectible {

	protected int value;

	public Collectible() {

	}

	/**
	 * constructor of Collectible class
	 * @param value; int: dollar value of the item
	 */
	public Collectible(int value) {
		this.value = value;
	}

	public void generateValue() {
		value = 100;
	}

	/**
	 * function getValue return the dollar value of the item
	 * @return int value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * function setValue set the dollar value of the item
	 * @param value; int: dollar value of the item
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * function getType return the type of the item (money bag or gem)
	 * @return CollectibleType
	 */
	public CollectibleType getType() {
		return CollectibleType.NONE;
	}

	/**
	 * enum CollectibleType the type of the item
	 */
	public enum CollectibleType {
		NONE, GEM, MONEY_BAG;
	}

	/**
	 * Get String of the object
	 * @return String
	 */
	@Override
	public String toString() {
		return "Collectible{" +
				"value=" + this.value +
				'}';
	}
}
